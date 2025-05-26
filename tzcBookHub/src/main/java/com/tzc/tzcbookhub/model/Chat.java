package com.tzc.tzcbookhub.model;

import lombok.Data;

@Data
public class Chat {
    private int id;
    private int senderId;
    private int receiverId;
}
