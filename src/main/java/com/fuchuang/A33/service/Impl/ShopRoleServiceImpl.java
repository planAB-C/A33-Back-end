package com.fuchuang.A33.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchuang.A33.entity.Employee;
import com.fuchuang.A33.entity.EmployeeRole;
import com.fuchuang.A33.entity.Shop;
import com.fuchuang.A33.entity.ShopRole;
import com.fuchuang.A33.mapper.EmployeeMapper;
import com.fuchuang.A33.mapper.ShopMapper;
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

    @Autowired
    private EmployeeMapper employeeMapper ;

    @Autowired
    private ShopMapper shopMapper ;

    @Override
    public Result addShopRoleService(String shopID, String shopRoleType , String shopRoleValue) {
        ShopRole shopRole = new ShopRole();
        shopRole = new ShopRole() ;
        shopRole.setShopID(shopID);
        shopRole.setShopRoleValue(shopRoleValue);
        shopRole.setShopRoleType(shopRoleType);
        int rows = shopRoleMapper.insert(shopRole) ;
        if (rows==0) return Result.fail(500,"操作不成功，请确认后重新操作") ;
        return Result.success(200);
    }

    @Override
    public Result UpdateShopRoleService(String shopID, String shopRoleType, String shopRoleValue) {
        int rows =0 ;
        ShopRole shopRole = new ShopRole();
        shopRole.setShopRoleType(shopRoleType);
        shopRole.setShopRoleValue(shopRoleValue);
        rows = shopRoleMapper.update(shopRole,new QueryWrapper<ShopRole>()
                .eq("shopID",shopID)
                .eq("shop_role_type",shopRoleType).eq("shop_role_value",shopRoleValue)) ;
        if (rows==0) return Result.fail(500,"操作不成功，请确认后重新操作") ;
        return null;
    }

    @Override
    public Result removeShopRoleService(String shopID , String shopRoleType) {
        int rows = shopRoleMapper.delete(new QueryWrapper<ShopRole>().eq("shop_ID",shopID).eq("shop_Role_Type",shopRoleType)) ;
        if (rows==0){
            return Result.fail(500,"操作不成功，请重新操作") ;
        }
        return Result.success(200);
    }

    @Override
    public Result addShop(String name, String address, double size) {
        Long count = shopMapper.selectCount(new QueryWrapper<Shop>());
        String ID = null ;
        if (count<=8) ID = "0" + (count + 1) + "" ;
        else ID = count + 1 + "" ;
        shopMapper.insert(new Shop(ID,name,address,size)) ;
        return Result.success(200);
    }
}
