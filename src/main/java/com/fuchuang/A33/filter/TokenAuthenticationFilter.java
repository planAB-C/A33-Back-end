package com.fuchuang.A33.filter;

import cn.hutool.core.bean.BeanUtil;
import com.fuchuang.A33.entity.LoginUser;
import com.fuchuang.A33.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private StringRedisTemplate redisTemplate ;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = request.getHeader("token");
        //检查token是否为空，为空，放行，因为有可能是登录页面，哪怕不是登陆页面，后面的过滤器仍然会进行过滤
        if (!StringUtils.hasText(token)){
            //放行
            filterChain.doFilter(request,response);
            return;
        }
        //从redis中获取数据
        User user = BeanUtil.fillBeanWithMap(redisTemplate.opsForHash().entries(token), new User(), false);
        if (BeanUtil.isEmpty(user)){
            throw new RuntimeException("there is a wrong now") ;
        }
        //存入SecurityContextHoledr，这个容器会存储用户信息，后面的过滤器会去获得SecurityContextHolder的对象并进行检查是否认证成功
        //含有三个参数的UsernamePasswordAuthenticationToken方法，它内部授予了认证
        LoginUser loginUser = new LoginUser(user, user.getPermissions());
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request,response);
    }
}
