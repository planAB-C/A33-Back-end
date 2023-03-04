package com.fuchuang.A33.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fuchuang.A33.entity.LoginUser;
import com.fuchuang.A33.entity.Menu;
import com.fuchuang.A33.entity.User;
import com.fuchuang.A33.mapper.MenuMapper;
import com.fuchuang.A33.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
//实现UserDetailsService接口，重写其中的方法
public class UserDetailsImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper ;

    @Autowired
    private MenuMapper menuMapper ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    //通过用户名进行查找，实现验证
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUsername, username);
        User user = userMapper.selectOne(lambdaQueryWrapper);
        if (Objects.isNull(user)){
            throw new RuntimeException("this user is null now") ;
        }

        //记得注入User对象
        ArrayList<String> authentication = new ArrayList<>();
        List<Menu> allMenus = menuMapper.getAllMenus(user.getId());
        for (Menu menu : allMenus) {
            authentication.add(menu.getMenu()) ;
        }
        return new LoginUser(user,authentication);
    }
}
