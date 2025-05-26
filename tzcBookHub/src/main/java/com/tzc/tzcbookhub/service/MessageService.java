package com.tzc.tzcbookhub.service;

import com.tzc.tzcbookhub.mapper.ChatMapper;
import com.tzc.tzcbookhub.mapper.MessageMapper;
import com.tzc.tzcbookhub.model.Message;
import com.tzc.tzcbookhub.model.dto.MessageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageMapper messageMapper;
    private final ChatMapper chatMapper;

    public MessageService(MessageMapper messageMapper, ChatMapper chatMapper) {
        this.messageMapper = messageMapper;
        this.chatMapper = chatMapper;
    }

    public void saveMessage(MessageRequest messageRequest) {
        int chatId = chatMapper.selectChat(Integer.parseInt(messageRequest.getSenderId()), Integer.parseInt(messageRequest.getReceiverId())).getId();
        messageMapper.addMessage(Integer.parseInt(messageRequest.getSenderId()), Integer.parseInt(messageRequest.getReceiverId()), messageRequest.getContent(), chatId);
    }

    public List<Message> getMessageByChatId(int senderId, int receiverId) {
        int chatId = chatMapper.selectChat(senderId, receiverId).getId();
        return messageMapper.selectByChatId(chatId);
    }
}
