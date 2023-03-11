package com.fuchuang.A33.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_business_role")
public class BusinessRole {
    @TableField("shop_ID")
    private String shopID ;
    @TableField("type")
    private String type ;
    @TableField("business_type")
    private String businessType ;
    @TableField("business_value")
    private String businessValue ;
    @TableField("time_type")
    private String timeType ;
    @TableField("rest_type")
    private String restType ;
    @TableField("rest_value")
    private String restValue ;
}
