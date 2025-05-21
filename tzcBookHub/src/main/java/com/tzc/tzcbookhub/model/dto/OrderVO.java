package com.tzc.tzcbookhub.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderVO {
    private int orderID;
    private String sellerName;
    private String consumerName;
    private String goodName;
    private String goodImg;
    private String orderStatu;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
}
