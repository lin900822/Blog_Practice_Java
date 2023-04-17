package com.lin.blog.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lin.blog.portal.mapper.ResourceMapper;
import com.lin.blog.portal.model.Resource;
import com.lin.blog.portal.service.IResourceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource> implements IResourceService
{
    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    public String saveFileToDisk(MultipartFile multipartFile) throws IOException
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
        return resourceUrl;
    }

    @Override
    public void addResource(Resource resource)
    {
        resourceMapper.insert(resource);
    }

    @Override
    public PageInfo<Resource> getAllResources(Integer pageNum, Integer pageSize)
    {
        if(pageSize == null || pageSize == 0) pageSize = 1;
        if(pageNum == null || pageNum == 0) pageNum = 1;

        QueryWrapper<Resource> query = new QueryWrapper<>();
        query.orderByDesc("created_at");

        PageHelper.startPage(pageNum, pageSize);
        List<Resource> list = resourceMapper.selectList(query);
        return new PageInfo<>(list);
    }

    @Value("${blog.resource.path}")
    private File resourcePath;
    @Value("${blog.resource.host}")
    private String resourceHost;

    @Override
    public void deleteResource(Integer id)
    {
        Resource resource = resourceMapper.selectById(id);
        String filePath = resource.getUrl();
        filePath = filePath.substring(resourceHost.length());
        filePath = resourcePath + filePath;

        File file = new File(filePath);
        file.delete();

        resourceMapper.deleteById(id);
    }
}
