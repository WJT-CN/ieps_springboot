package com.wjt.ieps.config.shiro;

import com.wjt.ieps.config.shiro.filter.JWTFilter;
import com.wjt.ieps.shiro.CustomerRealm;
import com.wjt.ieps.shiro.cache.RedisCacheManage;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //禁用session, 不保存用户登录状态。保证每次请求都重新认证
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator()
    {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    //创建shiroConfig负责来拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //添加自定义过滤器
        Map<String, Filter> filterMap = new HashMap<>(1);
        filterMap.put("jwt", new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        //配置系统受限资源
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.put("/home","anon");//公共资源
        map.put("/publish/**","anon");//公共资源
        map.put("/ieps/user/login","anon");//公共资源
        map.put("/swagger-ui.html/**","anon");//公共资源
        map.put("/ieps/item/downItem/**","anon");//公共资源
//        map.put("/ieps/user/admin","roles[校级管理员]");
        map.put("/**","jwt");//需要认证资源
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        //默认认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/home");
        return shiroFilterFactoryBean;
    }

    //创建安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager( Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置realm
        defaultWebSecurityManager.setRealm(realm);
        //关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        defaultWebSecurityManager.setSubjectDAO(subjectDAO);
        return defaultWebSecurityManager;
    }

    //创建自定义realm
    @Bean
    public Realm getRealm(){
        CustomerRealm customerRealm = new CustomerRealm();
        SimpleCredentialsMatcher simpleCredentialsMatcher = new SimpleCredentialsMatcher();
//        credentialsMatcher.setHashAlgorithmName("MD5");
//        credentialsMatcher.setHashIterations(1024);
        customerRealm.setCredentialsMatcher(simpleCredentialsMatcher);


        //开启缓存管理
//        customerRealm.setCacheManager(new EhCacheManager());//默认Ehcache缓存
//        customerRealm.setCacheManager(new RedisCacheManage());//使用Redis缓存
//        customerRealm.setCachingEnabled(true);
//        customerRealm.setAuthenticationCachingEnabled(true);
//        customerRealm.setAuthenticationCacheName("authenticationCache");
//        customerRealm.setAuthorizationCachingEnabled(true);
//        customerRealm.setAuthorizationCacheName("authorizationCache");
        return customerRealm;
    }

}
