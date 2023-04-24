package com.lin.blog.portal.controller;

import com.lin.blog.portal.exception.ServiceException;
import com.lin.blog.portal.model.Category;
import com.lin.blog.portal.service.ICategoryService;
import com.lin.blog.portal.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController
{
    @Resource
    private ICategoryService categoryService;

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addCategory(String name, Integer ancestorId)
    {
        log.debug("接收到新增分類資料:name={},ancestorId={}", name, ancestorId);
        categoryService.addCategory(name, ancestorId);
    }

    @GetMapping("/getAllCategoriesTree")
    public ResponseEntity<List<Category>> getAllCategoriesTree()
    {
        return ResponseEntity.ok(categoryService.getAllCategoriesTree());
    }

    @PostMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity deleteCategory(Integer id)
    {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity updateCategory(Integer id, String name)
    {
        if (name.isEmpty())
        {
            throw new ServiceException("名稱不得為空!");
        }

        categoryService.updateCategory(id, name);

        return ResponseEntity.ok(null);
    }
}
