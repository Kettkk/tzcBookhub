package com.tzc.tzcbookhub.service;

import com.tzc.tzcbookhub.mapper.AdminMapper;
import com.tzc.tzcbookhub.model.Admin;
import com.tzc.tzcbookhub.model.LoginRequest;
import com.tzc.tzcbookhub.model.User;
import com.tzc.tzcbookhub.model.dto.GoodVO;
import com.tzc.tzcbookhub.model.dto.OrderVO;
import com.tzc.tzcbookhub.model.dto.RegisterRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminMapper adminMapper;

    public AdminService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    public boolean login(LoginRequest loginRequest) {
        Admin admin = adminMapper.selectByName(loginRequest.getUsername());
        if (admin != null && admin.getPassword().equals(loginRequest.getPassword())) {
            return true;
        } else {
            return false;
        }
    }

    public boolean register(RegisterRequest registerRequest) {
        Admin admin = adminMapper.selectByName(registerRequest.getUsername());
        if (admin == null) {
            int result = adminMapper.insert(registerRequest.getUsername(), registerRequest.getPassword());
            return true;
        } else {
            return false;
        }
    }

    public List<GoodVO> getAllGoodVO() {
        return adminMapper.selectAllGoodVO();
    }

    public List<OrderVO> getAllOrderVO() {
        return adminMapper.selectAllOrderVO();
    }

    public List<User> getAllUser(){
        return adminMapper.selectAllUser();
    }

    public boolean deleteUserById(int userId) {
        int rows = adminMapper.deleteUserById(userId);
        return rows > 0;
    }

    public boolean updateUser(User user){
        System.out.println(user);
        int rows = adminMapper.updateUser(user);
        System.out.println(rows);
        return rows > 0;
    }
}
