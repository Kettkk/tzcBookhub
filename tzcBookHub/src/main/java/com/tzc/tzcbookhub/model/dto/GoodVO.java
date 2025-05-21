package com.tzc.tzcbookhub.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GoodVO {
    private int goodID;
    private String sellerName;
    private String goodName;
    private String goodInfo;
    private double goodValue;
    private String goodImg;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
}
