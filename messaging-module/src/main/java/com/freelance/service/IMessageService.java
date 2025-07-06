package com.freelance.service;

import com.freelance.dto.MessageRequest;
import com.freelance.dto.MessageResponse;
import com.freelance.model.message.Message;
import org.springframework.web.multipart.MultipartFile;

public interface IMessageService {

    MessageResponse sendMessage(MessageRequest request);
}
