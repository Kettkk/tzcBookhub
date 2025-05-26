package com.tzc.tzcbookhub.controller;


import com.tzc.tzcbookhub.mapper.GoodMapper;
import com.tzc.tzcbookhub.mapper.UserMapper;
import com.tzc.tzcbookhub.model.Good;
import com.tzc.tzcbookhub.model.User;
import com.tzc.tzcbookhub.model.dto.GoodWithSellerDTO;
import com.tzc.tzcbookhub.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.tzc.tzcbookhub.utils.JwtUtil.validateToken;

@RestController
@RequestMapping("/api")
public class GoodController {

    @Resource
    private GoodMapper goodMapper;

    @Resource
    private UserMapper userMapper;

    @GetMapping("getAllGood")
    public List<Good> getAllGood() {
        return goodMapper.selectAll();
    }

    @GetMapping("getGoodById")
    public GoodWithSellerDTO getGoodById(@RequestParam("id") Integer id) {
        Good good = goodMapper.selectById(id);
        User seller = userMapper.selectById(good.getSellerID());

        GoodWithSellerDTO dto = new GoodWithSellerDTO();
        dto.setGood(good);
        dto.setSeller(seller);

        return dto;
    }

    @GetMapping("getGreatGood")
    public List<Good> getGreatGood() {
        return goodMapper.selectOrderBySellerStar();
    }

    @GetMapping("getNewGood")
    public List<Good> getNewGood() {
        return goodMapper.selectOrderNewBook();
    }

    @GetMapping("getGoodsBySellerId")
    public List<Good> getGoodBySellerId(@RequestParam("sellerId") Integer sellerId) {
        return goodMapper.selectByUserId(sellerId);
    }

    @PostMapping("PublishBook")
    @Transactional
    public ResponseEntity<String> publishBook(
            @RequestParam("goodName") String goodName,
            @RequestParam("price") double price,
            @RequestParam("description") String description,
            @RequestParam("img") MultipartFile img,
            @RequestHeader("Authorization") String authHeader // "Bearer 4"
    ) {
        // 从 Authorization: Bearer 4 中提取 ID
        String token = authHeader.replace("Bearer ", "");
        Integer sellerID;
        try {
            sellerID = Integer.parseInt(token); // 直接转换为 ID
            System.out.println(sellerID);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("无效的 token");
        }

        // 保存文件
        String fileName = UUID.randomUUID() + "_" + img.getOriginalFilename();
        // 更换主机只需要修改这一部分
        String savePath = "D:/DevProjects/tzcBookhub/bookhubclient/public/assets/books/" + fileName;
        try {
            Path path = Paths.get(savePath);
            Files.createDirectories(path.getParent());
            img.transferTo(path.toFile());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("图片保存失败");
        }

        // 插入数据库
        Good good = new Good();
        good.setGoodID(goodMapper.selectFinalId() + 1);
        good.setSellerID(sellerID);
        good.setGoodName(goodName);
        good.setGoodInfo(description);
        good.setGoodValue(price);
        good.setGoodImg("/assets/books/" + fileName);
        good.setCreateTime(LocalDateTime.now());
        good.setLastUpdateTime(LocalDateTime.now());
        System.out.println(good);
        int result = goodMapper.saveProduct(good);
        if (result > 0) {
            return ResponseEntity.ok("发布成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("保存商品失败");
        }
    }


}
