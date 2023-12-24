package com.projectmd5.service.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.projectmd5.service.FilesStorageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FilesStorageServiceImpl implements FilesStorageService {
   private final String bucketName = "demoupload-512c4.appspot.com";
   @Override
   public Storage getStorage() {
      ClassPathResource serviceAccount = new ClassPathResource("firebase-upload.json");
      // Authenticate with Google Cloud using service account credentials
      // InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("firebase-config.json");
      GoogleCredentials credentials = null;
      try {
         credentials = GoogleCredentials.fromStream(serviceAccount.getInputStream());
      } catch (IOException e) {
         throw new RuntimeException(e.getMessage());
      }
      return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
   }

   @Override
   public String getExtension(String originalFileName) {
      return StringUtils.getFilenameExtension(originalFileName);
   }

   @Override
   public String generateFileName(String originalFileName) {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
      return dtf.format(LocalDateTime.now())+ originalFileName +"."+ getExtension(originalFileName);
   }

   @Override
   public String uploadFile(MultipartFile file) {
      String fileName = generateFileName(file.getOriginalFilename()); // lấy ra tên file upload

      BlobId blobId = BlobId.of(bucketName, fileName); // tạo file trên storage bằng tên và bucket name chỉ đinh

      BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

      // Thiết lập quyền truy cập công cộng
      List<Acl> acls = new ArrayList<>();
      acls.add(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
      blobInfo = blobInfo.toBuilder().setAcl(acls).build();
      try {
         Blob blob = getStorage().create(blobInfo, file.getBytes());
         return blob.getMediaLink(); // trả về đường dẫn ảnh online
      } catch (IOException e) {
         throw new RuntimeException(e.getMessage());
      }
   }
}
