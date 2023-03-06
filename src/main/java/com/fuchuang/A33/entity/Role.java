package com.fuchuang.A33.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_role")
public class Role {
    @TableField("role_type")
    private String roleType ;
    @TableField("shop_ID")
    private String shopID ;
    @TableField("role_value")
    private String roleValue ;
}
