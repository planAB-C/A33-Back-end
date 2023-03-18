package com.fuchuang.A33.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetailsInformationDTO {
    private String ID ;
    private String name ;
    private String email ;
    private String phone ;
    private String position ;
    private String shopName ;
    private String groupName ;
    private final String hobbyType1 = "喜欢的工作日" ;
    private String hobbyValue1 = "无" ;
    private final String hobbyType2 = "喜欢的工作时间" ;
    private String hobbyValue2 = "无" ;
    private final String hobbyType3 = "一次性工作的最长时间" ;
    private String hobbyValue3 = "无" ;
}
