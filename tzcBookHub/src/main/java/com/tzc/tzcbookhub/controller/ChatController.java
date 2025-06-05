package com.tzc.tzcbookhub.controller;

import com.tzc.tzcbookhub.model.User;
import com.tzc.tzcbookhub.model.dto.ChatRequest;
import com.tzc.tzcbookhub.service.ChatService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/save")
    public int saveChat(@CookieValue("jwt1") int userId, @RequestBody ChatRequest chatRequest) {
        if (userId == chatRequest.getReceiverId()) {
            return -1;
        }
        return chatService.saveChat(userId, chatRequest.getReceiverId());
    }

    @GetMapping("/getChat")
    public List<User> getChatListByUserId(@CookieValue("jwt1") int userId) {
        return chatService.getChatListByUserId(userId);
    }
}
