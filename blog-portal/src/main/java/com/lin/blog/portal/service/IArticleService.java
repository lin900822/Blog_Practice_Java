package com.lin.blog.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.blog.portal.model.Article;
import com.lin.blog.portal.vo.ArticleVO;

public interface IArticleService extends IService<Article>
{
    void createArticle(ArticleVO articleVO, String username);
}
