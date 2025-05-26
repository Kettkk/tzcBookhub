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

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }

    @GetMapping("/getGeldUser")
    public List<User> getStarredUsers() {
        return userMapper.selectUserByStar();
    }

    @PostMapping("/userinfo")
    public User getUserInfo(@RequestBody Integer userId) {
        return userMapper.selectById(userId);
    }

    @PostMapping("/register")
    public String register(@RequestBody User userFromFront) {
        if (userMapper.findByUsername(userFromFront.getUsername()) != null) {
            return "500"; // 用户名已存在
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
    public Integer login(@RequestBody LoginRequest loginRequest) {
        User user = userMapper.findByUsername(loginRequest.getUsername());
        if (user == null || !user.getPassword().equals(loginRequest.getPassword())) {
            return -1; // 登录失败返回 -1 或其他错误标识
        }
        return user.getUserID(); // 登录成功返回 user_id
    }
}