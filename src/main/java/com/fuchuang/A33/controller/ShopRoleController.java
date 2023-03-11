package com.fuchuang.A33.controller;

import com.fuchuang.A33.service.Impl.EmployeeRoleServiceImpl;
import com.fuchuang.A33.service.Impl.ShopRoleServiceImpl;
import com.fuchuang.A33.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/shopRole")
@Api(tags = "商铺规则")
public class ShopRoleController {
    @Autowired
    private ShopRoleServiceImpl shopRoleService ;

    @PreAuthorize("hasAnyAuthority('root','manage','group','view')")
    @PostMapping("/add")
    @ApiOperation(value = "添加或修改商铺规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopID", value = "商铺ID" ,dataType= "String"),
            @ApiImplicitParam(name = "shopRoleType", value = "商铺规则类型" ,dataType= "String"),
            @ApiImplicitParam(name = "shopRoleValue", value = "商铺规则值" ,dataType= "String")
    })
    public Result addOrUpdateShopRoleService(String shopID , String shopRoleType , String shopRoleValue){
        return shopRoleService.addOrUpdateShopRoleService(shopID, shopRoleType, shopRoleValue) ;
    }

    @PreAuthorize("hasAnyAuthority('root','manage','group','view')")
    @DeleteMapping("/remove")
    @ApiOperation(value = "移除商铺规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopID", value = "商铺ID" ,dataType= "String"),
            @ApiImplicitParam(name = "shopRoleType", value = "商铺规则类型" ,dataType= "String")
    })
    public Result removeEmployeeRole(String shopID ,String shopRoleType){
        return shopRoleService.removeShopRoleService(shopID, shopRoleType) ;
    }
}
