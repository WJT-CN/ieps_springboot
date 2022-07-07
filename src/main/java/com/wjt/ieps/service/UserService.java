package com.wjt.ieps.service;

import com.wjt.ieps.entity.Permission;
import com.wjt.ieps.entity.User;
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
public interface UserService extends IService<User> {

    User findRolesByUsername(String primaryPrincipal);

    List<Permission> findPermsByRoleId(Integer roleId);
}
