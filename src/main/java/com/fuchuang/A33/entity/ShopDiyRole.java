package com.fuchuang.A33.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
@TableName("t_diy_role")
public class ShopDiyRole {
    private String ID;
    private String type;
    private String value;
    private String comment;
    @TableField("shop_ID")
    private String shopID;

}
