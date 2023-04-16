package com.lin.blog.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lin.blog.portal.model.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IResourceService extends IService<Resource>
{
    String saveFileToDisk(MultipartFile multipartFile) throws IOException;

    void addResource(Resource resource);

    PageInfo<Resource> getAllResources(Integer pageNum, Integer pageSize);

    void deleteResource(Integer id);
}
