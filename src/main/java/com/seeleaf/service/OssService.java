package com.seeleaf.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    public String uploadFile(MultipartFile file);
}
