package com.tzc.tzcbookhub.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class Chat {
    private int id;
    private int senderId;
    private int receiverId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }
}
