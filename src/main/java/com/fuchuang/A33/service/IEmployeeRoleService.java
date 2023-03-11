package com.fuchuang.A33.service;

import com.fuchuang.A33.utils.Result;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface IEmployeeRoleService {
    Result addOrUpdateEmployeeRole(String shopID , String shopRoleType , String shopRoleValue) ;
    Result removeEmployeeRole(String shopID , String shopRoleType) ;
}
