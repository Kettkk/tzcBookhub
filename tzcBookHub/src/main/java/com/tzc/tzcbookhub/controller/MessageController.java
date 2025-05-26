package com.tzc.tzcbookhub.controller;

import com.tzc.tzcbookhub.model.Message;
import com.tzc.tzcbookhub.service.MessageService;
import jakarta.servlet.ServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/message")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("getMessageByChatId")
    public List<Message> getMessageByChatId(@RequestParam("senderId") int senderId, @RequestParam("receiverId") int receiverId, ServletRequest servletRequest){
        return messageService.getMessageByChatId(senderId,receiverId);
    }
}
