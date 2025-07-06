package com.freelance.controller.impl;

import com.freelance.controller.IMessageController;
import com.freelance.dto.ChatMessage;
import com.freelance.dto.MessageRequest;
import com.freelance.dto.MessageResponse;
import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.message.Chat;
import com.freelance.model.user.User;
import com.freelance.repository.UserRepository;
import com.freelance.service.IChatService;
import com.freelance.service.IMessageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class MessageControllerImpl implements IMessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final IMessageService messageService;
    private final IChatService chatService;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        User sender = userRepository.findByUsername(chatMessage.getSender())
                .orElseThrow();
        User receiver = userRepository.findByUsername(chatMessage.getReceiver())
                .orElseThrow();

        Chat chat = chatService.getOrCreateChat(sender.getId(), receiver.getId());

        MessageRequest request = MessageRequest.builder()
                .chatId(chat.getId())
                .senderId(sender.getId())
                .content(chatMessage.getContent())
                .attachmentType(chatMessage.getAttachmentType())
                .attachmentUrl(chatMessage.getAttachmentUrl())
                .build();

        messageService.sendMessage(request);

        chatMessage.setChatId(chat.getId());
        messagingTemplate.convertAndSend("/topic/chat." + chat.getId(), chatMessage);
    }

    @GetMapping("/api/chats/id")
    public ResponseEntity<Long> getChatId(@RequestParam("user1") String user1, @RequestParam("user2") String user2) {
        User u1 = userRepository.findByUsername(user1).orElseThrow(
                ()->
                new BaseException(new ErrorMessage(MessageType.USER_NOT_FOUND,"Kullanıcı bulunamadı: " + user1)));
        User u2 = userRepository.findByUsername(user2).orElseThrow( ()->
                new BaseException(new ErrorMessage(MessageType.USER_NOT_FOUND,"Kullanıcı bulunamadı: " + user2)));
        Chat chat = chatService.getOrCreateChat(u1.getId(), u2.getId());
        return ResponseEntity.ok(chat.getId());
    }
}
