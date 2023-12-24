package com.projectmd5.service;

import com.google.cloud.storage.Storage;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
   Storage getStorage();
   String getExtension(String originalFileName);
   String generateFileName(String originalFileName);
   String uploadFile(MultipartFile file);
}
