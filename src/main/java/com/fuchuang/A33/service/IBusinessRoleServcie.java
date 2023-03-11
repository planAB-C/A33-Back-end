package com.fuchuang.A33.service;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fuchuang.A33.utils.Result;
import org.springframework.stereotype.Repository;

@Repository
public interface IBusinessRoleServcie {
    Result addOrUpdateShopRoleService(    String shopID  ,String type , String businessType , String businessValue ,
                                  String timeType , String restType , String restValue ) ;
    Result removeShopRoleService(String shopID  ,String type , String businessType , String businessValue ,
                                 String timeType , String restType , String restValue) ;


}
