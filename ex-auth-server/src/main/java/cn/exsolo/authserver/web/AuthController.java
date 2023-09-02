package cn.exsolo.authserver.web;

import cn.exsolo.auth.bo.TokenInfo;
import cn.exsolo.auth.shiro.LoginAuthenticationToken;
import cn.exsolo.auth.utils.AccessUtil;
import cn.exsolo.auth.utils.CaptchaUtil;
import cn.exsolo.auth.utils.TokenUtil;
import cn.exsolo.auth.vo.AuthResultVO;
import cn.exsolo.auth.vo.CaptchaCheckVO;
import cn.exsolo.auth.vo.CaptchaVO;
import cn.exsolo.authserver.item.ExAuthServerErrorCodeEnum;
import cn.exsolo.authserver.service.AuthCacheService;
import cn.exsolo.authserver.service.AuthService;
import cn.exsolo.authserver.vo.UserVO;
import cn.exsolo.batis.core.utils.GenerateID;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.comm.utils.TsUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Locale;


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

    @Value("${exsolo.auth.privateKey}")
    private String authPrivateKey;

    @Autowired
    private AuthCacheService authCacheService;

    @RequestMapping(path = "verify", method = RequestMethod.POST)
    public AuthResultVO verify(HttpServletRequest request,
                               @RequestParam String loginCode,
                               @RequestParam String password,
                               @RequestParam(required = false) String captchaTicket,
                               @RequestParam(required = false) String captchaValue) {
        //判断是否需要验证码验证
        if(authCacheService.isCaptchaRequire(loginCode)){
            if(StringUtils.isEmpty(captchaValue)){
                authCacheService.setCaptchaRequire(loginCode,true);
                AuthResultVO resultVO = new AuthResultVO().failAndCaptchaRequire(5*60*1000);
                throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_CAPTCHA_REQUIRE).withData(resultVO);
            }
            String cacheValue = authCacheService.getCaptcha(captchaTicket);
            if(!captchaValue.toLowerCase(Locale.ROOT).equals(cacheValue)){
                authCacheService.setCaptchaRequire(loginCode,true);
                AuthResultVO resultVO = new AuthResultVO().failAndCaptchaRequire(5*60*1000);
                throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_CAPTCHA_CHECK_FAIL).withData(resultVO);
            }
        }
        //登录逻辑
        try{
            LoginAuthenticationToken authToken = new LoginAuthenticationToken(loginCode,password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(authToken);
            //构建用户token信息
            UserVO user = authService.getUserByLoginCode(loginCode);
            TokenInfo tokenInfo = new TokenInfo();
            tokenInfo.setLoginCode(user.getLoginCode());
            tokenInfo.setUserId(user.getUserId());
            tokenInfo.setUserName(user.getUserName());
            tokenInfo.setTicket(GenerateID.generateShortUuid());
            tokenInfo.setTs(TsUtil.getTimestamp());
            tokenInfo.setIp(AccessUtil.getRequestClientIP(request));
            //TODO 时效时间改成可配置，默认3天
            int expireTimes = 60*24*1000*3;
            return new AuthResultVO().success(TokenUtil.crateToken(tokenInfo,expireTimes,authPrivateKey));
        }catch (IncorrectCredentialsException e){
            AuthResultVO resultVO ;
            if(authCacheService.isCaptchaRequire(loginCode)){
                resultVO = new AuthResultVO().failAndCaptchaRequire(5*60*1000);
            }else{
                resultVO = new AuthResultVO().fail();
            }
            throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_FAILED).withData(resultVO);
        }catch (UnknownAccountException e){
            AuthResultVO resultVO = new AuthResultVO().fail();
            throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_FAILED).withData(resultVO);
        }catch (ExcessiveAttemptsException e){
            AuthResultVO resultVO = new AuthResultVO().fail();
            throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_RETRY_TO_MUCH).withData(resultVO);
        }catch (Throwable e){
            throw e;
        }

    }


    @RequestMapping(path = "captcha", method = RequestMethod.GET)
    public CaptchaVO captcha(HttpServletRequest request) throws IOException {
        CaptchaVO captchaVO = new CaptchaVO();
        CaptchaCheckVO checkValue = CaptchaUtil.generateCaptchaCheckVO();
        //添加到缓存中
        authCacheService.setCaptcha(checkValue.getTicket(),checkValue.getCaptchaValue());
        captchaVO.setTicket(checkValue.getTicket());
        captchaVO.setCaptchaBase64(checkValue.getCaptchaImageBase64());
        return captchaVO;

    }




}
