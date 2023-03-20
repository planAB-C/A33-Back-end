package com.fuchuang.A33.DTO;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopDiyRoleDTO {
    private String ID;
    private String type;
    private String valueB;
    private String valueA;
    private String comment;
    private String option;
    @TableField("shop_ID")
    private String shopID;
}
