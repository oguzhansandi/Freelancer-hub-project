package com.freelance.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageRequest {

    private Long chatId;
    private Long senderId;
    private String content;
    private Date createdDate;
}
