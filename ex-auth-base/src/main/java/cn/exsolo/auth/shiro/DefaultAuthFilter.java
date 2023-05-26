package cn.exsolo.auth.shiro;

import cn.exsolo.auth.utils.AccessUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author prestolive
 * @date 2023/5/24
 **/
public class DefaultAuthFilter extends BasicHttpAuthenticationFilter {

    @Override
    public void doFilterInternal(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        super.doFilterInternal(request, response, chain);
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String tokenStr = AccessUtil.getToken(request);
        DefaultAuthenticationToken token = new DefaultAuthenticationToken(tokenStr);
        getSubject(request,response).login(token);
        return true;
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        String token = AccessUtil.getToken(request);
        if(StringUtils.isEmpty(token)){
            return false;
        }else{
            try {
                return executeLogin(request,response);
            } catch (Throwable e) {
                return false;
            }
        }
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
//        return super.onAccessDenied(request, response);
    }
}
