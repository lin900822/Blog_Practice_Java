package com.lin.blog.portal.controller;

import com.lin.blog.portal.model.Category;
import com.lin.blog.portal.service.ICategoryService;
import com.lin.blog.portal.service.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable Integer id)
    {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(null);
    }
}
