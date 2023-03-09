package com.fuchuang.A33.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_flow")
public class Flow {
    @TableField("ID")
    private String ID ;
    @TableField("permit_flow")
    private Integer permitFlow ;
    @TableField("real_flow")
    private Integer realFlow  ;
    @TableField("location_ID")
    private String locationID ;
}
