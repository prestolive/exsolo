package cn.exsolo.authserver.web;

import cn.exsolo.auth.shiro.LoginAuthenticationToken;
import cn.exsolo.authserver.item.ExAuthServerErrorCodeEnum;
import cn.exsolo.authserver.service.AuthService;
import cn.exsolo.comm.ex.ExDeclaredException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author prestolive
 * @date 2023/5/26
 **/

@Component
@RequestMapping("api/auth/")
@RestController()
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping(path = "verify", method = RequestMethod.POST)
    public void verify(@RequestParam String loginCode,
                       @RequestParam String password,
                       @RequestParam(required = false) String captchaCode) {
        //判断是否需要验证码验证
        try{
            LoginAuthenticationToken token = new LoginAuthenticationToken(loginCode,password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
        }catch (AuthenticationException e){
            throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_FAILED);
        }

    }

}
