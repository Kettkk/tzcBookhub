package com.tzc.tzcbookhub.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Good {
    private int goodID;
    private String goodName;
    private int sellerID;
    private String goodInfo;
    private double goodValue;
    private String goodImg;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;

}
