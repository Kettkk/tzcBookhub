package com.tzc.tzcbookhub.controller;


import com.tzc.tzcbookhub.mapper.GoodMapper;
import com.tzc.tzcbookhub.mapper.UserMapper;
import com.tzc.tzcbookhub.model.Good;
import com.tzc.tzcbookhub.model.User;
import com.tzc.tzcbookhub.model.dto.GoodWithSellerDTO;
import com.tzc.tzcbookhub.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Good> getNewGood(){
        return goodMapper.selectOrderNewBook();
    }

    @GetMapping("getGoodsBySellerId")
    public List<Good> getGoodBySellerId(@RequestParam("sellerId") Integer sellerId) {
        return goodMapper.selectByUserId(sellerId);
    }




}
