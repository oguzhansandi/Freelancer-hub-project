package com.freelance.service.impl;

import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.service.IFileService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.UUID;

@Service
public class FileServiceImpl implements IFileService {

    private final String uploadDir = System.getProperty("user.home") + "/Desktop/FreelanceUploads";

    @Override
    public String saveFile(MultipartFile file) {
        try {
            String cleanedFileName = file.getOriginalFilename().replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
            String fileName = UUID.randomUUID() + "_" + cleanedFileName;
            Path dir = Paths.get(uploadDir);
            Files.createDirectories(dir);

            Path filePath = dir.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException e) {
            throw new BaseException(new ErrorMessage(MessageType.FILE_NOT_SAVED, e.getMessage()));
        }
    }

    @Override
    public Resource getFile(String fileName, Principal principal) {
        try {
            Path filePath = Paths.get(System.getProperty("user.home"), "Desktop/FreelanceUploads", fileName);

            if (!Files.exists(filePath) || !Files.isRegularFile(filePath)){
                throw new BaseException(new ErrorMessage(MessageType.DATA_NOT_FOUND,"Dosya bulunamadı"));
            }
            System.out.println(filePath.toUri());
            return new UrlResource(filePath.toUri());
        }catch (MalformedURLException e){
            throw new RuntimeException("Dosya URL hatası : " + fileName, e);
        }
    }
}
