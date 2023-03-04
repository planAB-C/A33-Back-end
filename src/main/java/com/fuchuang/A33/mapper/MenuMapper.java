package com.fuchuang.A33.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fuchuang.A33.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> getAllMenus(Integer id) ;
}
