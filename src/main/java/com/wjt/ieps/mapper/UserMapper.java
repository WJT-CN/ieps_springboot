package com.wjt.ieps.mapper;

import com.wjt.ieps.entity.Permission;
import com.wjt.ieps.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wjt
 * @since 2022-04-25
 */
public interface UserMapper extends BaseMapper<User> {

    List<Permission> findPermsByRoleId(Integer roleId);

    User findRolesByUsername(String primaryPrincipal);
}
