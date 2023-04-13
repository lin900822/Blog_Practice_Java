package com.lin.blog.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService
{
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public Integer saveArticle(ArticleVO articleVO, String username) throws ServiceException
    {
        //User user = userMapper.findUserByUsername(username);

        Article article = new Article()
                .setId(articleVO.getId())
                .setTitle(articleVO.getTitle())
                .setSummary(articleVO.getSummary())
                .setContent(articleVO.getContent())
                .setThumbnail(articleVO.getThumbnail())
                .setCategoryId(0)
                .setUserId(0)
                .setStatus(articleVO.getStatus())
                .setUpdatedAt(LocalDateTime.now());

        int num = 0;

        if(article.getId() == null){
            article.setCreatedAt(LocalDateTime.now());
            num = articleMapper.insert(article);
        }
        else{
            num = articleMapper.updateById(article);
        }

        if(num != 1) throw new ServiceException("資料庫錯誤!");

        return article.getId();
    }

    @Override
    public PageInfo<Article> getAllArticles(Integer pageNum, Integer pageSize)
    {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articles = articleMapper.selectList(null);
        return new PageInfo<>(articles);
    }
}
