package com.tzc.tzcbookhub.controller;


import com.tzc.tzcbookhub.mapper.GoodMapper;
import com.tzc.tzcbookhub.model.Good;
import com.tzc.tzcbookhub.model.User;
import com.tzc.tzcbookhub.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GoodController {

    @Resource
    private GoodMapper goodMapper;

    @GetMapping("getAllGood")
    public List<Good> getAllGood() {
        return goodMapper.selectAll();
    }

    @GetMapping("getGoodById")
    public Good getGoodById(@RequestParam("id") Integer id) {
        return goodMapper.selectById(id);
    }
    @GetMapping("getGreatGood")
    public List<Good> getGreatGood() {
        return goodMapper.selectOrderBySellerStar();
    }

    @GetMapping("getNewGood")
    public List<Good> getNewGood(){
        return goodMapper.selectOrderNewBook();
    }




}
