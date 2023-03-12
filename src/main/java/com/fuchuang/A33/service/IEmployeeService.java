package com.fuchuang.A33.service;

import com.fuchuang.A33.utils.Result;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeService {
    Result login(String email);
    Result regist(String name, String email, String position, String shopId ,String Belong);
    Result changeGroup(String groupEmail) ;
    Result updateEmployeeInformation(String email, String position);
    Result showOtherImformation();
    Result showEmployeeByRoot(String shopID);
    Result updateOtherImformation(String ID, String email, String position, String belong);
    Result showAllShop();
}
