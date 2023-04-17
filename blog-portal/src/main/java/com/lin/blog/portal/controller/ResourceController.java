package com.lin.blog.portal.controller;

import com.github.pagehelper.PageInfo;
import com.lin.blog.portal.service.IResourceService;
import com.lin.blog.portal.service.impl.ResourceServiceImpl;
import com.lin.blog.portal.utils.ByteConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/resource")
public class ResourceController
{
    @Resource
    private IResourceService resourceService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(MultipartFile multipartFile) throws IOException
    {
        String resourceUrl = resourceService.saveFileToDisk(multipartFile);

        com.lin.blog.portal.model.Resource resource = new com.lin.blog.portal.model.Resource()
                .setName(multipartFile.getOriginalFilename())
                .setUrl(resourceUrl)
                .setFileSize(ByteConverter.convertBytes(multipartFile.getSize()))
                .setCreatedAt(LocalDateTime.now());

        resourceService.addResource(resource);

        return ResponseEntity.ok(resourceUrl);
    }

    @GetMapping("/allResources")
    public ResponseEntity<PageInfo<com.lin.blog.portal.model.Resource>> getAllFiles(Integer pageNum, Integer pageSize)
    {
        PageInfo<com.lin.blog.portal.model.Resource> list = resourceService.getAllResources(pageNum, pageSize);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteFile(@PathVariable Integer id)
    {
        resourceService.deleteResource(id);

        return ResponseEntity.ok(null);
    }
}
