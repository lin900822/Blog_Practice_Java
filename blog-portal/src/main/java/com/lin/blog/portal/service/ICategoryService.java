package com.lin.blog.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.blog.portal.model.Category;

import java.util.List;

public interface ICategoryService extends IService<Category>
{
    void addCategory(String name, Integer ancestorId);

    List<Category> getAllCategoriesTree();

    void deleteCategory(Integer id);

    void updateCategory(Integer id, String name);
}
