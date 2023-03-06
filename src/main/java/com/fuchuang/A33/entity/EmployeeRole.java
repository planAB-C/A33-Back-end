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
@TableName("t_employ_role")
public class EmployeeRole {
    @TableField("hobby")
    private String hobby ;
    @TableField("employee_id")
    private String employId ;
    @TableField("hobby_value")
    private String hobbyValue ;
}
