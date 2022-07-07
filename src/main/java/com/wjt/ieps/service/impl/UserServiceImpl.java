package com.wjt.ieps.service.impl;

import com.wjt.ieps.entity.Permission;
import com.wjt.ieps.entity.User;
import com.wjt.ieps.mapper.UserMapper;
import com.wjt.ieps.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wjt
 * @since 2022-04-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User findRolesByUsername(String primaryPrincipal) {
        return baseMapper.findRolesByUsername(primaryPrincipal);
    }

    @Override
    public List<Permission> findPermsByRoleId(Integer roleId) {
        return baseMapper.findPermsByRoleId(roleId);
    }
}
