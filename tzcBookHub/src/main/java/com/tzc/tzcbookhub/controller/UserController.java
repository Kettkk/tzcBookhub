package com.tzc.tzcbookhub.controller;

import com.tzc.tzcbookhub.mapper.UserMapper;
import com.tzc.tzcbookhub.model.LoginRequest;
import com.tzc.tzcbookhub.model.User;
import com.tzc.tzcbookhub.utils.JwtUtil;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("getAllUser")
    public List<User> getAllUser() {
        return userMapper.selectAll();
    }

    @GetMapping("getGeldUser")
    public List<User> getGeldUser() {
        return userMapper.selectUserByStar();
    }


    @PostMapping("/Register")
    public String register(@RequestBody User userFromFront) {
        List<User> existingUsers = userMapper.selectAll();
        for (User u : existingUsers) {
            if (u.getUsername().equals(userFromFront.getUsername())) {
                return "500"; // 用户名重复
            }
        }

        Integer maxUserId = userMapper.getMaxUserId();
        int nextUserId = (maxUserId == null) ? 1 : maxUserId + 1;

        userFromFront.setUserID(nextUserId);
        userFromFront.setMoney(0.0);
        userFromFront.setStar(0.0);
        userFromFront.setAvatar(null);
        LocalDateTime now = LocalDateTime.now();
        userFromFront.setCreateTime(now);
        userFromFront.setLastUpdateTime(now);

        int result = userMapper.insertUser(userFromFront);

        return result > 0 ? "200" : "500";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        User user = userMapper.findByUsername(loginRequest.getUsername());
        if (user == null) {
            return "500"; // 用户不存在
        }
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            return "500"; // 密码错误
        }

        // 登录成功，生成 JWT
        String token = JwtUtil.generateToken(user.getUserID(), user.getUsername(), user.getPassword());
        return token;
    }


}