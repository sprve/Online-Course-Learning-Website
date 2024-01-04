package com.sprve.oss.service;
import org.springframework.web.multipart.MultipartFile;
public interface OssService {
    String uploadFileAvator(MultipartFile file);
    void deleteFileAvator(String fileUrl);
}