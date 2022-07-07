package com.wjt.ieps.shiro;

import com.wjt.ieps.entity.Permission;
import com.wjt.ieps.entity.Role;
import com.wjt.ieps.entity.User;
import com.wjt.ieps.exception.IepsException;
import com.wjt.ieps.service.UserService;
import com.wjt.ieps.utils.ApplicationContextUtils;
import com.wjt.ieps.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.CollectionUtils;

import java.util.List;

public class CustomerRealm extends AuthorizingRealm {
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String username = (String) principalCollection.getPrimaryPrincipal();
        UserService userService = (UserService) ApplicationContextUtils.getBean("userServiceImpl");
        User user = userService.findRolesByUsername(username);
        if(!CollectionUtils.isEmpty(user.getRoles())){
            List<Role> roles = user.getRoles();
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            user.getRoles().forEach(role -> {
                simpleAuthorizationInfo.addRole(role.getRoleName());
                //权限信息
                List<Permission> perms = userService.findPermsByRoleId(role.getRoleId());
                if(!CollectionUtils.isEmpty(perms)){
                    perms.forEach(perm ->{
                        simpleAuthorizationInfo.addStringPermission(perm.getPermName());
                    });
                }
            });
            return simpleAuthorizationInfo;
        }
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String credentials = (String) token.getCredentials();
        String userName;
        try {
            //jwt验证token
            boolean verify = JwtUtils.verify(credentials);
            if (!verify) {
                throw new AuthenticationException("Token校验不正确");
            }
            userName = JwtUtils.getClaim(credentials, "username");
        } catch (Exception e) {
            throw new IepsException(20001,"用户身份校验失败");
        }

        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，不设置则使用默认的SimpleCredentialsMatcher
        return new SimpleAuthenticationInfo(
                //用户名
                userName,
                //凭证
                credentials,
                //realm name
                this.getName());
    }
//        String principal = (String) token.getPrincipal();
//
//        //在工厂中获取service对象
//        UserService userService = (UserService) ApplicationContextUtils.getBean("userServiceImpl");
//
//        User user = userService.getOne(new QueryWrapper<User>().eq("username",principal));
//
//        if(user!=null){
////            return new SimpleAuthenticationInfo(user.getUsername(),user.getPassword(), ByteSource.Util.bytes(user.getSalt()),this.getName());
//            return new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
//        }
//        return null;
//    }
}
