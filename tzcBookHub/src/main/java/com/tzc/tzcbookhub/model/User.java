package com.tzc.tzcbookhub.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class User {
    private String username;
    private int userID;
    private String password;
    private String email;
    private double money;
    private double star;
    private String avatar;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
}
