package com.fuchuang.A33.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsInfomationDTO {
    private String ID ;
    private String name ;
    private String email ;
    private String position ;
    private String shopName ;
    private String groupName ;
    private HashMap<String,String> employeeRole ;
}
