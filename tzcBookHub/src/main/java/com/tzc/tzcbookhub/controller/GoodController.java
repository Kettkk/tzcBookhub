package com.tzc.tzcbookhub.controller;


import com.tzc.tzcbookhub.mapper.GoodMapper;
import com.tzc.tzcbookhub.model.Good;
import com.tzc.tzcbookhub.model.User;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
