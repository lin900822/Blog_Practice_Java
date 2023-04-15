package com.lin.blog.portal.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.blog.portal.model.CategoryPath;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryPathMapper extends BaseMapper<CategoryPath>
{
    int batchInsert(List<CategoryPath> categoryPathList);

    @Select("select ancestor from categorypaths where descendant=#{id}")
    List<Integer> selectAncestorIdsById(Integer id);

    @Delete("DELETE FROM categorypaths \n" +
            "WHERE descendant IN(SELECT descendant FROM (SELECT * FROM categorypaths) AS tmp WHERE ancestor = #{id});")
    void deleteCategoryPath(Integer id);
}
