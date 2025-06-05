package com.tzc.tzcbookhub.service;


import com.tzc.tzcbookhub.mapper.ChatMapper;
import com.tzc.tzcbookhub.model.Chat;
import com.tzc.tzcbookhub.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatMapper chatMapper;

    public ChatService(ChatMapper chatMapper) {
        this.chatMapper = chatMapper;
    }

    public int saveChat(int senderId, int receiverId) {
        Chat chat = chatMapper.selectChat(senderId, receiverId);
        if (chat == null) {
            return chatMapper.saveChat(senderId, receiverId);
        } else {
            return 0;
        }
    }

    public List<User> getChatListByUserId(int userId) {
        return chatMapper.selectChatByUserId(userId);
    }

}
