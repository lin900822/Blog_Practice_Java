package com.lin.blog.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.blog.portal.mapper.BasicMapper;
import com.lin.blog.portal.model.Basic;
import com.lin.blog.portal.model.Category;
import com.lin.blog.portal.service.IBasicService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Service
public class BasicServiceImpl extends ServiceImpl<BasicMapper, Basic> implements IBasicService
{
    @Resource
    private BasicMapper basicMapper;


    @Override
    public void saveBasic(Basic basic)
    {
        basic.setId(1);
        basicMapper.updateById(basic);

        resetBasicRedis();
    }

    @Resource
    private RedisTemplate<String, Basic> redisTemplate;

    @Override
    public Basic getBasic()
    {
        Basic basic = redisTemplate.opsForValue().get("basic");

        if (basic == null)
        {
            basic = basicMapper.selectById(1);
            redisTemplate.opsForValue().set("basic", basic);
        }

        return basic;
    }

    private void resetBasicRedis()
    {
        if (redisTemplate.hasKey("basic"))
        {
            redisTemplate.delete("basic");
        }
    }
}
