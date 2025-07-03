package com.freelance.service.impl;

import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.message.Chat;
import com.freelance.model.user.User;
import com.freelance.repository.ChatRepository;
import com.freelance.repository.UserRepository;
import com.freelance.service.IChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChatServiceImpl implements IChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Chat getOrCreateChat(Long userAId, Long userBId) {
        if (userAId.equals(userBId)){
            throw new BaseException(new ErrorMessage(MessageType.MESSAGE_ERROR, "Kullanıcı kendisiyle mesajlaşamaz"));
        }

        User userA = userRepository.findById(userAId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "user : " + userAId)
                ));

        User userB = userRepository.findById(userBId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "user : " + userAId)
                ));

        return  chatRepository.findByParticipant1AndParticipant2(userA, userB)
                    .or(() -> chatRepository.findByParticipant2AndParticipant1(userA, userB))
                    .orElseGet(() -> {
                        Chat chat = new Chat();
                        chat.setParticipant1(userA);
                        chat.setParticipant2(userB);
                        chat.setCreateDate(new Date());
                        return chatRepository.save(chat);
                    });
        }

    @Override
    public List<Chat> getChatsForUser(Long userId) {
        return chatRepository.findAllByUserId(userId);
    }

    @Override
    public Chat getChatById(Long chatId) {
        return chatRepository.findById(chatId)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.DATA_NOT_FOUND,"Sohbet bulunamadı : " + chatId)
                ));
    }

    public boolean userIsParticipant(Long userId, Chat chat){
        return chat.getParticipant1().getId().equals(userId) ||chat.getParticipant2().getId().equals(userId);
    }

}
