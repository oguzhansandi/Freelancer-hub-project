package com.freelance.service.impl;

import com.freelance.exception.BaseException;
import com.freelance.exception.ErrorMessage;
import com.freelance.exception.MessageType;
import com.freelance.model.Portfolio;
import com.freelance.model.PortfolioRequest;
import com.freelance.model.PortfolioResponse;
import com.freelance.model.User;
import com.freelance.repository.PortfolioRepository;
import com.freelance.repository.UserRepository;
import com.freelance.service.IPortfolioService;
import com.freelance.services.CommonService;
import io.minio.*;
import io.minio.http.Method;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PortfolioServiceImpl implements IPortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommonService commonService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(path + "/" + fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(path + "/" + fileName)
                            .expiry(7, TimeUnit.DAYS)
                            .build()
            );

        } catch (Exception e) {
            throw new IOException("Dosya MinIO'ya yüklenemedi: " + e.getMessage());
        }
    }
    @Override
    public PortfolioResponse createPortfolio(PortfolioRequest request, MultipartFile file) {
        String username = commonService.getCurrentUsername();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, "username : " + username)
                ));

        Portfolio portfolio = new Portfolio();

        modelMapper.map(request, portfolio);
        portfolio.setUser(user);
        portfolio.setLikeCount(0);
        portfolio.setViewCount(0);
        try {
            portfolio.setMediaURL(uploadFile("portfolio",file));
        }catch (IOException e){
            throw new RuntimeException("Dosya yükelenemedi : " + e.getMessage());
        }
        portfolio.setCreateDate(new Date());

        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
        return  modelMapper.map(savedPortfolio, PortfolioResponse.class);
    }
}
