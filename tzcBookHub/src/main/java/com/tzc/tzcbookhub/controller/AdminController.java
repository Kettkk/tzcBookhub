package com.tzc.tzcbookhub.controller;

import com.tzc.tzcbookhub.model.LoginRequest;
import com.tzc.tzcbookhub.model.User;
import com.tzc.tzcbookhub.model.dto.GoodVO;
import com.tzc.tzcbookhub.model.dto.OrderVO;
import com.tzc.tzcbookhub.model.dto.RegisterRequest;
import com.tzc.tzcbookhub.service.AdminService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        boolean success = adminService.login(loginRequest);
        if (success) {
            return ResponseEntity.ok("login successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("wrong password or admin does not exist");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        boolean success = adminService.register(registerRequest);

        if (success) {
            return ResponseEntity.ok("register successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("user has existed");
        }
    }

    @GetMapping("/goods")
    public ResponseEntity<List<GoodVO>> getAllGoods() {
        return ResponseEntity.ok(adminService.getAllGoodVO());
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderVO>> getAllOrders() {
        return ResponseEntity.ok(adminService.getAllOrderVO());
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUser());
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        boolean success = adminService.deleteUserById(id);
        if (success) {
            return ResponseEntity.ok("用户删除成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("用户删除失败");
        }
    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        boolean success = adminService.updateUser(user);
        if (success) {
            return ResponseEntity.ok("用户更新成功");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("用户更新失败");
        }
    }


}

