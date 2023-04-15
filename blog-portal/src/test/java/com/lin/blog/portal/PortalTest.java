package com.lin.blog.portal;

import com.lin.blog.portal.mapper.CategoryMapper;
import com.lin.blog.portal.mapper.CategoryPathMapper;
import com.lin.blog.portal.mapper.UserMapper;
import com.lin.blog.portal.model.Category;
import com.lin.blog.portal.model.CategoryPath;
import com.lin.blog.portal.model.User;
import com.lin.blog.portal.service.impl.CategoryServiceImpl;
import com.lin.blog.portal.service.impl.JWTService;
import com.lin.blog.portal.vo.AuthenticationVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class PortalTest
{
    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void encodePassword()
    {
        String str = "123456";
        String pwd = encoder.encode(str);
        System.out.println(pwd);
    }

    @Resource
    UserMapper userMapper;

    @Test
    public void testUserMapper()
    {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }

    @Resource
    private JWTService jwtService;

    @Test
    public void testGenerateJWT()
    {
        String token = jwtService.generateToken(new AuthenticationVO().setUsername("linwilson").setPassword("123456"));
        System.out.println(token);
    }

    @Test
    public void testParseJWT()
    {
        Object obj = jwtService.parseToken("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6ImxpbndpbHNvbiIsImV4cCI6MTY3OTcxNDMzMiwiaXNzIjoiTXkgQmxvZyJ9.Cn8coaKmodfZDeU7z9JohVs07-GFH3uXDE0ANrIh21o");
        System.out.println(obj);
    }

    @Resource
    CategoryPathMapper categoryPathMapper;

    @Test
    public void testCategoryPathMapper()
    {
        List<CategoryPath> list = new ArrayList<>();
        list.add(new CategoryPath().setAncestor(1).setDescendant(1));
        list.add(new CategoryPath().setAncestor(1).setDescendant(2));
        list.add(new CategoryPath().setAncestor(1).setDescendant(3));
        System.out.println(categoryPathMapper.batchInsert(list));
    }

    @Resource
    private CategoryServiceImpl categoryService;

    @Test
    public void testCategoryServiceImpl()
    {
        categoryService.addCategory("Nacos", 35);
    }

    @Resource
    private CategoryMapper categoryMapper;

    @Test
    public void testCategorySelectDescendantById(){
        List<Category> list = categoryService.getSelfAndDescendantCategories(27);
        for (Category c : list){
            System.out.println(c);
        }
    }

    @Test
    public void testGetAllCategoryOrderByLevel(){
        List<Category> list = categoryService.getAllCategoriesTree();
        for (Category c : list){
            for (int i = 0; i < c.getLevel(); i++)
            {
                System.out.print("---");
            }
            System.out.println(c.getName());
        }
    }
}
