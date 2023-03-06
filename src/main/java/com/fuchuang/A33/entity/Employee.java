package com.fuchuang.A33.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_employee")
public class Employee  {
    private String ID ;
    private String name ;
    private String email ;
    private String position ;
    @TableField("shop_ID")
    private String  shopID ;
    @TableField(exist = false)
    private List<String> permissions ;

    public Employee(String ID, String name, String email, String position, String shopID ){
        this.ID = ID ;
        this.name = name ;
        this.email = email ;
        this.position = position ;
        this.shopID = shopID ;
        permissions = new ArrayList<String>() ;
    }
}
