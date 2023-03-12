package com.fuchuang.A33.service;

import com.fuchuang.A33.utils.Result;
import org.springframework.stereotype.Repository;

@Repository
public interface IShopRoleService {
    Result addShopRoleService(String shopID , String shopRoleType , String shopRoleValue) ;
    Result UpdateShopRoleService(String shopID , String shopRoleType , String shopRoleValue) ;
    Result removeShopRoleService(String shopID , String shopRoleType) ;
    Result addShop(String name, String address, double size);
}
