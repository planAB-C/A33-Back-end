package com.fuchuang.A33;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchuang.A33.entity.Menu;
import com.fuchuang.A33.entity.User;
import com.fuchuang.A33.mapper.MenuMapper;
import com.fuchuang.A33.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
class SpringSecurityApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private UserMapper userMapper ;

    @Autowired
    private MenuMapper menuMapper ;

    @Test
    public void test1(){
        List<User> users = userMapper.selectList(new QueryWrapper<>());
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void test2(){
        String encode = passwordEncoder.encode("1234");
        System.out.println(encode);
    }

    @Test
    public void test3(){
        List<Menu> allMenus = menuMapper.getAllMenus(1);
        for (Menu allMenu : allMenus) {
            System.out.println(allMenu);
        }
    }
}
