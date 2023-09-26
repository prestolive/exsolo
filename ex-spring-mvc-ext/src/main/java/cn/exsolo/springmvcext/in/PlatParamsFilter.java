package cn.exsolo.springmvcext.in;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author prestolive
 * @date 2021/1/18
 **/
@WebFilter(filterName = "flat" , urlPatterns = "/*")
public class PlatParamsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        PlatParamsRequestWrapper requestWrapper = new PlatParamsRequestWrapper( (HttpServletRequest) request );
        chain.doFilter(requestWrapper, response);
    }

    @Override
    public void destroy() {
    }
}
