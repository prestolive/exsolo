package cn.exsolo.auth.utils;

import cn.hutool.http.useragent.UserAgent;
import cn.hutool.http.useragent.UserAgentParser;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;

/**
 * Created by wuby on 2018/8/21.
 * @author prestolive
 */
public class AccessUtil {

    public static String TOKEN_FIELD = "access_token";

    public static String getRequestClientIP(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }


    /**
     * APP和微信客户端 token 从http header中获取，PC需要从cookie获取
     * @param
     * @param req
     * @return
     */
    public static String getToken(ServletRequest arg){
        HttpServletRequest req = (HttpServletRequest) arg;
        String token = null;

        token = req.getHeader("authorization");
        if (StringUtils.isEmpty(token)) {
            token = req.getHeader("Authorization");
        }
        if (StringUtils.isEmpty(token)) {
            Cookie[] cookies = req.getCookies();
            if(cookies!=null){
                for(Cookie cookie : cookies){
                    if(cookie.getName().equals(TOKEN_FIELD)){
                        token = cookie.getValue();
                    }
                }
            }
        }
        return token;
    }

    public static String getClient(HttpServletRequest req){
        String client = req.getParameter("_client");
        if(StringUtils.isEmpty(client)){
            UserAgent userAgent = UserAgentParser.parse(req.getHeader("User-Agent"));
            client = userAgent.getOs().getName();
        }
        return client;
    }

}
