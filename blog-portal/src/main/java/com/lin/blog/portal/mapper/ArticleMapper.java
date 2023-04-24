package com.lin.blog.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.blog.portal.model.Article;
import com.lin.blog.portal.model.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper extends BaseMapper<Article>
{
    @Update("UPDATE articles SET category=#{newName} WHERE category=#{oldName};")
    int updateArticlesCategoryName(@Param("oldName") String oldName, @Param("newName") String newName);

    List<Article> selectByCategories(List<Category> categories);
}
