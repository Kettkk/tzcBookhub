package com.tzc.tzcbookhub.controller;

import com.tzc.tzcbookhub.mapper.UserMapper;
import com.tzc.tzcbookhub.model.Good;
import com.tzc.tzcbookhub.model.LoginRequest;
import com.tzc.tzcbookhub.model.User;
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
            return 500; // 登录失败返回 -1 或其他错误标识
        }
        return user.getUserID(); // 登录成功返回 user_id
    }


    @PostMapping("updateUserInfo")
    @Transactional
    public ResponseEntity<String> updateUserInfo(
            @RequestParam("editedName") String editedName,
            @RequestParam("editedEmail") String editedEmail,
            @RequestParam(value = "img", required = false) MultipartFile img,
            @RequestHeader("Authorization") String authHeader // "Bearer 4"
    ) {
        // 从 Authorization: Bearer 4 中提取 ID
        String token = authHeader.replace("Bearer ", "");
        Integer userID;
        try {
            userID = Integer.parseInt(token); // 直接转换为 ID
            System.out.println(userID);
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("无效的 token");
        }

        if (!editedName.equals("")) {
            userMapper.updateUsername(editedName, userID);
        }

        if (!editedEmail.equals("")) {
            userMapper.updateEmail(editedEmail, userID);
        }

        if (img != null) {
            // 保存文件
            String fileName = UUID.randomUUID() + "_" + img.getOriginalFilename();
            // 更换主机只需要修改这一部分
            String savePath = "D:/DevProjects/tzcBookhub/bookhubclient/public/assets/userAvatars/" + fileName;
            try {
                Path path = Paths.get(savePath);
                Files.createDirectories(path.getParent());
                img.transferTo(path.toFile());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("图片保存失败");
            }

            userMapper.updateAvatar("/assets/userAvatars/" + fileName, userID);

        }

        return ResponseEntity.ok("更新成功");

    }

}