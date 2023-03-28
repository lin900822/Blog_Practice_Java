package com.lin.blog.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ArticleVO implements Serializable
{
    @NotBlank
    private String title;

    private String summary;

    private String content;

    private String thumbnail;

    private String category;

    private Integer status;

    private String createdAt;

    private String updatedAt;
}
