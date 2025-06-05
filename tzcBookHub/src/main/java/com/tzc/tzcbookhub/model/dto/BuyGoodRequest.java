package com.tzc.tzcbookhub.model.dto;

import java.time.LocalDateTime;
public class BuyGoodRequest {
    private Integer userId;
    private Integer sellerID;
    private Integer goodId;
    private LocalDateTime createTime;
    private LocalDateTime lastUpdateTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSellerID() {
        return sellerID;
    }

    public void setSellerID(Integer sellerId) {
        this.sellerID = sellerId;
    }

    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "BuyGoodRequest{" +
                "userId=" + userId +
                ", sellerId=" + sellerID +
                ", goodId=" + goodId +
                ", createTime=" + createTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
