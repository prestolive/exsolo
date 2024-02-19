package cn.exsolo.springmvcext.in;

import cn.hutool.http.ContentType;

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

        String contentType = request.getContentType();
        contentType=contentType==null? ContentType.JSON.toString():contentType.toLowerCase();
        if (contentType.startsWith(ContentType.JSON.toString())) {
            PlatParamsRequestWrapper requestWrapper = new PlatParamsRequestWrapper( (HttpServletRequest) request );
            chain.doFilter(requestWrapper, response);
        }else{
            chain.doFilter(request,response);
        }
    }

    @Override
    public void destroy() {
    }
}
