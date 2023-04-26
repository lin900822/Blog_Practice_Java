package com.lin.blog.portal.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("basic")
public class Basic implements Serializable
{
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("website_name")
    private String websiteName;

    @TableField("website_thumbnail")
    private String websiteThumbnail;
}
