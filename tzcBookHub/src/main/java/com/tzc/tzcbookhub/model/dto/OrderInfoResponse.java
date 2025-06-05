package com.tzc.tzcbookhub.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderInfoResponse {
    private int orderID;
    private String sellerName;
    private String goodName;
    private String orderStatu;
    private LocalDateTime createTime;
}
