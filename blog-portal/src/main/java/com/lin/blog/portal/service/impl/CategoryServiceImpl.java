package com.lin.blog.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.blog.portal.exception.ServiceException;
import com.lin.blog.portal.mapper.ArticleMapper;
import com.lin.blog.portal.mapper.CategoryMapper;
import com.lin.blog.portal.mapper.CategoryPathMapper;
import com.lin.blog.portal.model.Category;
import com.lin.blog.portal.model.CategoryPath;
import com.lin.blog.portal.service.ICategoryService;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService
{
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private CategoryPathMapper categoryPathMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Override
    @Transactional
    public void addCategory(String name, Integer ancestorId)
    {
        Category category = categoryMapper.selectByName(name);

        if (category != null)
            throw new ServiceException("該分類已存在!");

        if (ancestorId != null)
        {
            List<CategoryPath> list = new ArrayList<>();
            List<Integer> ancestorIds = categoryPathMapper.selectAncestorIdsById(ancestorId);

            category = new Category().setName(name).setLevel(ancestorIds.size());
            categoryMapper.insert(category);

            for (Integer i : ancestorIds)
            {
                list.add(new CategoryPath(i, category.getId()));
            }
            list.add(new CategoryPath(category.getId(), category.getId()));

            categoryPathMapper.batchInsert(list);
        }
        else
        {
            category = new Category().setName(name).setLevel(0);
            categoryMapper.insert(category);

            categoryPathMapper.insert(new CategoryPath(category.getId(), category.getId()));
        }
    }

    @Override
    public List<Category> getSelfAndDescendantCategories(Integer id)
    {
        return categoryMapper.findSelfAndDescendantById(id);
    }

    @Override
    public List<Category> getAllCategoriesTree()
    {
        return categoryMapper.findAllCategoriesOrderByLevel();
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id)
    {
        List<Category> list = categoryMapper.findSelfAndDescendantById(id);

        categoryPathMapper.deleteCategoryPath(id);

        for(Category c : list)
        {
            articleMapper.updateCategoryName(c.getName(), "");
            categoryMapper.deleteById(c.getId());
        }
    }
}
