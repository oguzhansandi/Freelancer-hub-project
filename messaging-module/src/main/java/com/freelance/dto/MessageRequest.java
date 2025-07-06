package com.freelance.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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
    private String attachmentOriginalName;
    private String attachmentUrl;
    private String attachmentType;
}
