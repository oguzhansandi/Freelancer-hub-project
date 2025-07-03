package com.freelance.dto;

import com.freelance.model.common.BaseEntity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private Long chatId;
    private Date createdDate;
    private String content;
    private String sender;
    private String receiver;

}
