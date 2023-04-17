package com.lin.blog.portal.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.blog.portal.mapper.BasicMapper;
import com.lin.blog.portal.model.Basic;
import com.lin.blog.portal.service.IBasicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

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
    }
}
