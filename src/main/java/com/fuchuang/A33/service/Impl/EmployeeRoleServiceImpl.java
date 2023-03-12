package com.fuchuang.A33.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fuchuang.A33.entity.EmployeeRole;
import com.fuchuang.A33.mapper.EmployeeRoleMapper;
import com.fuchuang.A33.service.IEmployeeRoleService;
import com.fuchuang.A33.utils.Constants;
import com.fuchuang.A33.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EmployeeRoleServiceImpl implements IEmployeeRoleService {

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper ;

    @Override
    public Result UpdateEmployeeRole(String employeeID, String hobbyType1, String hobbyValue1 ,String hobbyType2, String hobbyValue2 ,
    String hobbyType3, String hobbyValue3) {
        String hobbyType = null ;
        String hobbyValue = null ;
        for(int i = 0 ;i < Constants.EMPLOYEEROLE_NUMBER ; i++){
            switch (i){
                case 0 : {
                    hobbyType = hobbyType1 ;
                    hobbyValue = hobbyValue1 ;
                    break ;
                }
                case 1 : {
                    hobbyType = hobbyType2 ;
                    hobbyValue = hobbyValue2 ;
                    break;
                }
                case 2 : {
                    hobbyType = hobbyType3 ;
                    hobbyValue = hobbyValue3 ;
                    break;
                }
            }
            QueryWrapper<EmployeeRole> wrapper = new QueryWrapper<EmployeeRole>().eq("employee_ID", employeeID).eq("hobby_type", hobbyType);
            EmployeeRole employeeRole = employeeRoleMapper.selectOne(wrapper);
            if(Objects.isNull(employeeRole)){
                employeeRole = new EmployeeRole() ;
                employeeRole.setEmployeeID(employeeID); //由于在前面我们判断了employeeRole为空，为了防止出现空指针，这里我们需要重新创建一个对象
                employeeRole.setHobbyType(hobbyType);
                employeeRole.setHobbyValue(hobbyValue);
               employeeRoleMapper.insert(employeeRole) ;
            }else {
                employeeRole.setEmployeeID(employeeID);
                employeeRole.setHobbyType(hobbyType);
                employeeRole.setHobbyValue(hobbyValue);
                employeeRoleMapper.update(employeeRole,new QueryWrapper<EmployeeRole>().eq("employee_ID",employeeID)) ;
            }
        }
        return Result.success(200);
    }

}
