package com.lin.blog.portal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lin.blog.portal.mapper")
public class BlogPortalApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(BlogPortalApplication.class, args);
    }
}
