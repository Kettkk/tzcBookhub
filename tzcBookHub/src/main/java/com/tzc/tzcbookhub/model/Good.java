package com.tzc.tzcbookhub.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Good {
    private int goodID;
    private String goodName;
    private Integer sellerID;
    private String goodInfo;
    private double goodValue;
    private String goodImg;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;
}
