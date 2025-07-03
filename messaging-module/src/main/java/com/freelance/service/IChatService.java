package com.freelance.service;

import com.freelance.model.message.Chat;

import java.util.List;

public interface IChatService {
    Chat getOrCreateChat(Long userAId, Long userBId);

    List<Chat> getChatsForUser(Long userId);

    Chat getChatById(Long chatId);


}
