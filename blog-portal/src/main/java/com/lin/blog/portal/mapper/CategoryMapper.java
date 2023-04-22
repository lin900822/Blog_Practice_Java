package com.lin.blog.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.blog.portal.model.Category;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryMapper extends BaseMapper<Category>
{
    @Select("select * from categories where name=#{name}")
    Category selectByName(String name);

    @Select("SELECT c.* FROM categories c \n" +
            "INNER JOIN categorypaths p on c.id = p.descendant\n" +
            "WHERE p.ancestor = #{id};")
    List<Category> findSelfAndDescendantById(Integer id);

    @Select("with recursive cte as (\n" +
            "  select id, name, level, cast(name as char(200)) as path\n" +
            "  from categories\n" +
            "  where level = 0\n" +
            "  union all\n" +
            "  select c.id, c.name, c.level, concat(cte.path, ' > ', c.name)\n" +
            "  from categories c\n" +
            "  join categorypaths cp on c.id = cp.descendant\n" +
            "  join cte on cp.ancestor = cte.id\n" +
            "  where c.level = cte.level + 1\n" +
            ")\n" +
            "select id,name,level from cte order by path;")
    List<Category> findAllCategoriesOrderByLevel();
}
