package com.fuchuang.A33.service.Impl;

import com.fuchuang.A33.service.IBusinessRoleServcie;
import com.fuchuang.A33.utils.Result;
import org.springframework.stereotype.Service;

@Service
public class BusinessRoleServiceImpl implements IBusinessRoleServcie {
    @Override
    public Result addOrUpdateShopRoleService(String shopID, String type, String businessType,
                                             String businessValue, String timeType, String restType, String restValue) {
        return null;
    }

    @Override
    public Result removeShopRoleService(String shopID, String type, String businessType,
                                        String businessValue, String timeType, String restType, String restValue) {
        return null;
    }
}
