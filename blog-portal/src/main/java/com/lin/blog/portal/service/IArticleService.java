package com.lin.blog.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lin.blog.portal.model.Article;
import com.lin.blog.portal.vo.ArticleVO;

import java.util.List;

public interface IArticleService extends IService<Article>
{
    Integer saveArticle(ArticleVO articleVO, String username);

    PageInfo<Article> getAllArticles(Integer pageNum, Integer pageSize);
}
