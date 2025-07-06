package com.freelance.controller.impl;

import com.freelance.controller.IFileController;
import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.security.Principal;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileControllerImpl implements IFileController {

    @Autowired
    private IFileService fileService;

    @Override
    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public ResponseEntity<String> uploadAttachment(@RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("Yüklemek için geçerli bir dosya seçilmedi.");
        }

        String fileUrl = fileService.saveFile(file);
        return ResponseEntity.ok(fileUrl);
    }

    @Override
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("fileName") String fileName, Principal principal) {
        Resource resource = fileService.getFile(fileName, principal);
        MediaType contentType = MediaType.APPLICATION_OCTET_STREAM;
        try {
            String mimeType = Files.probeContentType(resource.getFile().toPath());
            if (mimeType != null) {
                contentType = MediaType.parseMediaType(mimeType);
            }
        }catch (Exception e){
            throw new BaseException(new ErrorMessage(MessageType.DATA_NOT_FOUND, e.getMessage()));
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(contentType)
                .body(resource);
    }
}
