package com.lin.blog.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.blog.portal.exception.ServiceException;
import com.lin.blog.portal.mapper.ArticleMapper;
import com.lin.blog.portal.mapper.UserMapper;
import com.lin.blog.portal.model.Article;
import com.lin.blog.portal.model.User;
import com.lin.blog.portal.service.IArticleService;
import com.lin.blog.portal.vo.ArticleVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService
{
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public void createArticle(ArticleVO articleVO, String username) throws ServiceException
    {
        //User user = userMapper.findUserByUsername(username);

        Article article = new Article()
                .setTitle(articleVO.getTitle())
                .setSummary(articleVO.getSummary())
                .setContent(articleVO.getContent())
                .setThumbnail(articleVO.getThumbnail())
                .setCategoryId(articleVO.getCategoryId())
                .setUserId(0)
                .setStatus(articleVO.getStatus())
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());

        int num = articleMapper.insert(article);
        if(num != 1) throw new ServiceException("資料庫錯誤!");
    }
}
