package com.freelance.controller;

import com.freelance.dto.ChatMessage;
import com.freelance.dto.MessageRequest;
import com.freelance.dto.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IMessageController {
    public void sendMessage(ChatMessage chatMessage);
}