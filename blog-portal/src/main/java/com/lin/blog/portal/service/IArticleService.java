package com.lin.blog.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lin.blog.portal.model.Article;
import com.lin.blog.portal.vo.ArticleVO;

public interface IArticleService extends IService<Article>
{
    Integer saveArticle(ArticleVO articleVO, String username);

    PageInfo<Article> getAllArticles(Integer pageNum, Integer pageSize);

    PageInfo<Article> getAllPublicArticles(Integer pageNum, Integer pageSize);

    PageInfo<Article> getPublicArticlesByCategory(String categoryName, Integer pageNum, Integer pageSize);
}
