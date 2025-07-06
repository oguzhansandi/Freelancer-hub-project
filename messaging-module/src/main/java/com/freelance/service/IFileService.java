package com.freelance.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface IFileService {

    String saveFile(MultipartFile file);
    public Resource getFile(String fileName, Principal principal);
}
