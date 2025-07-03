package com.freelance.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private Long messageId;
    private Long chatId;
    private Long senderId;
    private String senderName;
    private String content;
    private Date createDate;
//    private MessageType type;
}
