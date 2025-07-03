package com.freelance.service.impl;

import com.freelance.dto.MessageRequest;
import com.freelance.dto.MessageResponse;
import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.message.Chat;
import com.freelance.model.message.Message;
import com.freelance.model.user.User;
import com.freelance.repository.ChatRepository;
import com.freelance.repository.MessageRepository;
import com.freelance.repository.UserRepository;
import com.freelance.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    public Chat getOrCreateChat(User user1, User user2){

        return chatRepository.findByParticipants(user1, user2)
                .orElseGet(() -> {
                    Chat newChat = new Chat();
                    newChat.setParticipant1(user1);
                    newChat.setParticipant2(user2);
                    newChat.setCreateDate(new Date());
                    return chatRepository.save(newChat);
                });
    }

    @Override
    public MessageResponse sendMessage(MessageRequest request) {

        Chat chat = chatRepository.findById(request.getChatId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.MESSAGE_ERROR, "Sohbet bulunamadı")
                ));

        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "Gönderen bulunamadı : " + request.getSenderId())
                ));

        if (!chat.getParticipant1().getId().equals(sender.getId()) &&
                !chat.getParticipant2().getId().equals(sender.getId())) {
            throw new BaseException(new ErrorMessage(MessageType.MESSAGE_ERROR, "Bu sohbetin katılımcısı değilsiniz"));
        }

        Message message = new Message();
        message.setSender(sender);
        message.setChat(chat);
        message.setContent(request.getContent());
        message.setCreateDate(new Date());
        message.setRead(false);

        Message saved = messageRepository.save(message);

        return new MessageResponse(
                saved.getId(),
                chat.getId(),
                sender.getId(),
                sender.getUsername(),
                saved.getContent(),
                saved.getCreateDate()
        );
    }

}
