package com.fuchuang.A33.controller;

import com.fuchuang.A33.entity.ShopDiyRole;
import com.fuchuang.A33.service.Impl.ShopRoleServiceImpl;
import com.fuchuang.A33.utils.UsualMethodUtils;
import com.fuchuang.A33.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopRole")
@Api(tags = "商铺及商铺规则")
public class ShopRoleController {
    @Autowired
    private ShopRoleServiceImpl shopRoleService ;

    @PreAuthorize("hasAnyAuthority('boss')")
    @PostMapping("/addRole")
    @ApiOperation(value = "添加商铺规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopID", value = "商铺ID" ,dataType= "String"),
            @ApiImplicitParam(name = "shopRoleType", value = "商铺规则类型" ,dataType= "String"),
            @ApiImplicitParam(name = "shopRoleValue", value = "商铺规则值" ,dataType= "String")
    })
    public Result addShopRoleService(String shopID , String shopRoleType , String shopRoleValue){
        return shopRoleService.addShopRoleService(UsualMethodUtils.parseID(shopID), shopRoleType, shopRoleValue) ;
    }

    @PreAuthorize("hasAnyAuthority('boss')")
    @PostMapping("/updateRole")
    @ApiOperation(value = "修改商铺规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopID", value = "商铺ID" ,dataType= "String"),
            @ApiImplicitParam(name = "shopRoleType", value = "商铺规则类型" ,dataType= "String"),
            @ApiImplicitParam(name = "shopRoleValue", value = "商铺规则值" ,dataType= "String")
    })
    public Result updateShopRoleService(String shopID , String shopRoleType , String shopRoleValue){
        return shopRoleService.UpdateShopRoleService(UsualMethodUtils.parseID(shopID), shopRoleType, shopRoleValue) ;
    }

    @PreAuthorize("hasAnyAuthority('boss')")
    @DeleteMapping("/remove")
    @ApiOperation(value = "移除商铺规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopID", value = "商铺ID" ,dataType= "String"),
            @ApiImplicitParam(name = "shopRoleType", value = "商铺规则类型" ,dataType= "String")
    })
    public Result removeEmployeeRole(String shopID ,String shopRoleType){
        return shopRoleService.removeShopRoleService(UsualMethodUtils.parseID(shopID), shopRoleType) ;
    }

    @PreAuthorize("hasAnyAuthority('boss')")
    @DeleteMapping("/removeDiy")
    @ApiOperation(value = "移除商铺规则")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shopID", value = "商铺ID" ,dataType= "String"),
            @ApiImplicitParam(name = "shopRoleType", value = "商铺规则类型" ,dataType= "String")
    })
    public Result removeShopDiyRole(@RequestBody ShopDiyRole shopDiyRole){
        return shopRoleService.removeShopDiyRoleService(shopDiyRole) ;
    }
}
