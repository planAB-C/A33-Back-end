package com.fuchuang.A33.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_user")
public class User {
    private Integer id ;
    private String username ;
    private String phone ;
    private String password ;
    private Integer isDelete ;
    @TableField(exist = false)
    private List<String> permissions ;
}
