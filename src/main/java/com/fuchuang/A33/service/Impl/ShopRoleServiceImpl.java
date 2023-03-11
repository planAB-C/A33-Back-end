package com.fuchuang.A33.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchuang.A33.entity.EmployeeRole;
import com.fuchuang.A33.entity.ShopRole;
import com.fuchuang.A33.mapper.ShopRoleMapper;
import com.fuchuang.A33.service.IShopRoleService;
import com.fuchuang.A33.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ShopRoleServiceImpl implements IShopRoleService {
    @Autowired
    private ShopRoleMapper shopRoleMapper ;

    @Override
    public Result addOrUpdateShopRoleService(String shopID, String shopRoleType , String shopRoleValue) {
        QueryWrapper<ShopRole> wrapper = new QueryWrapper<ShopRole>().eq("shop_ID", shopID).eq("shop_role_type", shopRoleType);
        ShopRole shopRole = shopRoleMapper.selectOne(wrapper);
        int rows = 0 ;
        if(Objects.isNull(shopRole)){
            shopRole = new ShopRole() ;
            shopRole.setShopID(shopID);
            shopRole.setShopRoleValue(shopRoleValue);
            shopRole.setShopRoleType(shopRoleType);
            rows = shopRoleMapper.insert(shopRole) ;

            //没有就插入
        }else {
            //含有就更新
            shopRole.setShopRoleType(shopRoleType);
            shopRole.setShopRoleValue(shopRoleValue);
            rows = shopRoleMapper.update(shopRole,new QueryWrapper<ShopRole>()
                    .eq("shopID",shopID)
                    .eq("shop_role_type",shopRoleType).eq("shop_role_value",shopRoleValue)) ;
        }
        if (rows==0) return Result.fail(500,"操作不成功，请确认后重新操作") ;
        return Result.success(200);
    }

    @Override
    public Result removeShopRoleService(String shopID , String shopRoleType) {
        int rows = shopRoleMapper.delete(new QueryWrapper<ShopRole>().eq("shop_ID",shopID).eq("shop_Role_Type",shopRoleType)) ;
        if (rows==0){
            return Result.fail(500,"操作不成功，请重新操作") ;
        }
        return Result.success(200);
    }
}
