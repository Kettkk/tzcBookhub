package com.tzc.tzcbookhub.model.dto;

import lombok.Data;

@Data
public class ChatRequest {
    private int receiverId;

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
}
