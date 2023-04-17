package com.lin.blog.portal.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.blog.portal.model.Basic;

public interface IBasicService extends IService<Basic>
{
    void saveBasic(Basic basic);
}
