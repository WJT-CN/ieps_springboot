package com.wjt.ieps.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wjt.ieps.entity.Permission;
import com.wjt.ieps.entity.User;
import com.wjt.ieps.result.Result;
import com.wjt.ieps.service.PermissionService;
import com.wjt.ieps.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wjt
 * @since 2022-04-25
 */
@RestController
@RequestMapping("/ieps/permission")
@CrossOrigin  //解决跨域
public class PermissionController {

    @Autowired
    PermissionService permissionService;
    @Autowired
    UserService userService;

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/toAssign")
    public Result toAssign() {
        Subject subject = SecurityUtils.getSubject();
        List<Permission> list = new ArrayList<>();
        if(subject.hasRole("校级管理员")){
            list = permissionService.selectAllMenu("202201");
        }else if(subject.hasRole("院级管理员")){
            list = permissionService.selectAllMenu("202202");
        }else if(subject.hasRole("评委")){
            list = permissionService.selectAllMenu("202203");
        }else if(subject.hasRole("教师")){
            list = permissionService.selectAllMenu("202204");
        }else{
            list = permissionService.selectAllMenu("202205");
        }
        return Result.ok().data("permissionList", list);
    }

}
