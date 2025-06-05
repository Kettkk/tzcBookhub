package com.tzc.tzcbookhub.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {
    private int orderID;
    private int sellerID;
    private int consumerID;
    private int goodID;
    private String orderStatu;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
}
