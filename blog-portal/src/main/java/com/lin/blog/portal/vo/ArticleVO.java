package com.lin.blog.portal.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ArticleVO implements Serializable
{
    private Integer id;

    @NotBlank(message = "文章標題不得為空")
    private String title;

    private String summary;

    private String content;

    private String thumbnail;

    private String category;

    private Integer status;

    private String createdAt;

    private String updatedAt;
}
