package com.wjt.ieps.service;

import com.wjt.ieps.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wjt
 * @since 2022-04-25
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> selectAllMenu(String s);
}
