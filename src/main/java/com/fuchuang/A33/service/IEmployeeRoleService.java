package com.fuchuang.A33.service;

import com.fuchuang.A33.utils.Result;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRoleService {
    Result UpdateEmployeeRole(String employeeID, String hobbyType1, String hobbyValue1 ,String hobbyType2, String hobbyValue2 ,
                                   String hobbyType3, String hobbyValue3) ;
}
