package com.tzc.tzcbookhub.model;

import lombok.Data;

@Data
public class Message {
    private int id;
    private String senderId;
    private String receiverId;
    private String content;
    private String chatId;
}