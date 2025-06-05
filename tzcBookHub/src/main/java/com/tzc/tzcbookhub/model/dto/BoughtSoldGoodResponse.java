package com.tzc.tzcbookhub.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoughtSoldGoodResponse {
    private String goodName;
    private String goodInfo;
    private double goodValue;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
    private String goodImg;
}
