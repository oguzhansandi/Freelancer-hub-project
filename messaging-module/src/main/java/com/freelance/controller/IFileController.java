package com.freelance.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface IFileController {

    ResponseEntity<String> uploadAttachment(MultipartFile file);

    ResponseEntity<Resource> getFile(String fileName, Principal principal);
}
