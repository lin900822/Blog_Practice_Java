package com.lin.blog.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.blog.portal.mapper.CategoryPathMapper;
import com.lin.blog.portal.model.CategoryPath;
import com.lin.blog.portal.service.ICategoryPathService;
import org.springframework.stereotype.Service;

@Service
public class CategoryPathServiceImpl extends ServiceImpl<CategoryPathMapper, CategoryPath> implements ICategoryPathService
{
}
