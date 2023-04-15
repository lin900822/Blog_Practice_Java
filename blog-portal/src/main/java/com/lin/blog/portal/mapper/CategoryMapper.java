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

    @Select("WITH RECURSIVE cte AS (\n" +
            "  SELECT id, NAME, level, CAST(NAME AS CHAR(200)) AS path\n" +
            "  FROM categories\n" +
            "  WHERE level = 0\n" +
            "  UNION ALL\n" +
            "  SELECT c.id, c.NAME, c.level, CONCAT(cte.path, ' > ', c.NAME)\n" +
            "  FROM categories c\n" +
            "  JOIN CategoryPaths cp ON c.id = cp.Descendant\n" +
            "  JOIN cte ON cp.Ancestor = cte.id\n" +
            "  WHERE c.level = cte.level + 1\n" +
            ")\n" +
            "SELECT id,NAME,level FROM cte ORDER BY PATH;")
    List<Category> findAllCategoriesOrderByLevel();
}
