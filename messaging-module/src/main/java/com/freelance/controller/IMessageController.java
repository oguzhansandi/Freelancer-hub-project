package com.freelance.controller;

import com.freelance.dto.ChatMessage;
import com.freelance.dto.MessageRequest;
import com.freelance.dto.MessageResponse;

public interface IMessageController {
    public void sendMessage(ChatMessage chatMessage);
}
