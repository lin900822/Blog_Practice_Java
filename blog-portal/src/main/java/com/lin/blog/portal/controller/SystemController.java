package com.lin.blog.portal.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@Slf4j
public class SystemController
{
    @Value("${blog.resource.path}")
    private File resourcePath;
    @Value("${blog.resource.host}")
    private String resourceHost;

    @PostMapping("/upload/file")
    public ResponseEntity<String> upload(MultipartFile multipartFile) throws IOException
    {
        String path = DateTimeFormatter.ofPattern("yyyy/MM/dd").format(LocalDateTime.now());
        File resourceFolder = new File(resourcePath, path);
        resourceFolder.mkdirs();

        String fileName = multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + ext;

        File uploadedFile = new File(resourceFolder, newFileName);
        multipartFile.transferTo(uploadedFile);

        String resourceUrl = resourceHost + "/" + path + "/" + newFileName;
        return ResponseEntity.ok(resourceUrl);
    }
}
