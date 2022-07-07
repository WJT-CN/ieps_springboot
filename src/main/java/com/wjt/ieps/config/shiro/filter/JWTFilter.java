package com.wjt.ieps.config.shiro.filter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.wjt.ieps.exception.IepsException;
import com.wjt.ieps.shiro.JWTToken;
import com.wjt.ieps.utils.JwtUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class JWTFilter extends BasicHttpAuthenticationFilter {

    //设置请求头中需要传递的字段名
    protected static final String AUTHORIZATION_HEADER = "Access-Token";

    /**
     *  表示是否允许访问，mappedValue就是[urls]配置中拦截器参数部分，
     *  如果允许访问返回true，否则false
     * @author cheetah
     * @date 2020/11/24
     * @param request:
     * @param response:
     * @param mappedValue:
     * @return: boolean
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    /**
     *  表示当访问拒绝时是否已经处理了，
     *  如果返回true表示需要继续处理，
     *  如果返回false表示该拦截器实例已经处理了，将直接返回即可
     * @author cheetah
     * @date 2020/11/24
     * @param request:
     * @param response:
     * @return: boolean
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        if ("OPTIONS".equals(req.getMethod())){
//            response.setStatus(org.apache.http.HttpStatus.SC_NO_CONTENT);
            return true;
        }
        if (isLoginAttempt(request, response)) {
            //生成jwt token
            JWTToken token = new JWTToken(req.getHeader(AUTHORIZATION_HEADER));
            //委托给Realm进行验证
            try {
                //调用登陆会走Realm中的身份验证方法
                getSubject(request, response).login(token);
                return true;
            } catch (Exception e) {
            }
        }else{
            throw new IepsException();
//            return true;
        }

        return false;
    }



    /**
     *  判断是否有头部参数
     * @author cheetah
     * @date 2020/11/24
     * @param request:
     * @param response:
     * @return: boolean
     */
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(AUTHORIZATION_HEADER);
        return authorization != null;
    }
}
