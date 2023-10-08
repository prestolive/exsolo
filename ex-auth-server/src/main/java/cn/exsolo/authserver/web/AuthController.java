package cn.exsolo.authserver.web;

import cn.exsolo.auth.AuthBaseSettingProvider;
import cn.exsolo.auth.shiro.LoginAuthenticationToken;
import cn.exsolo.auth.utils.CaptchaUtil;
import cn.exsolo.auth.utils.AccessTokenUtil;
import cn.exsolo.auth.vo.AuthResultVO;
import cn.exsolo.auth.vo.CaptchaCheckVO;
import cn.exsolo.auth.vo.CaptchaVO;
import cn.exsolo.authserver.item.ExAuthServerErrorCodeEnum;
import cn.exsolo.authserver.service.AuthCacheService;
import cn.exsolo.authserver.service.AuthService;
import cn.exsolo.authserver.utils.RefreshTokenUtil;
import cn.exsolo.authserver.vo.UserVO;
import cn.exsolo.batis.core.utils.GenerateID;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.kit.utils.ExAssert;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Ref;
import java.util.Locale;


/**
 * @author prestolive
 * @date 2021/5/26
 **/

@Component
@RequestMapping("api/auth/")
@RestController()
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class.getName());

    @Autowired
    private AuthService authService;

    @Autowired
    private CacheManager cacheManager;

    @Value("${exsolo.auth.privateKey}")
    private String authPrivateKey;

    @Value("${exsolo.auth.refreshSecretKey}")
    private String authRefreshSecretKey;

    @Autowired
    private AuthCacheService authCacheService;

    @RequestMapping(path = "verify", method = RequestMethod.POST)
    public AuthResultVO verify(HttpServletRequest request,
                               @RequestParam String loginCode,
                               @RequestParam String password,
                               @RequestParam(required = false) String captchaTicket,
                               @RequestParam(required = false) String captchaValue) {
        //判断是否需要验证码验证
        if (authCacheService.isCaptchaRequire(loginCode)) {
            if (StringUtils.isEmpty(captchaValue)) {
                authCacheService.setCaptchaRequire(loginCode, true);
                AuthResultVO resultVO = new AuthResultVO().failAndCaptchaRequire(5 * 60 * 1000);
                throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_CAPTCHA_REQUIRE).withData(resultVO);
            }
            String cacheValue = authCacheService.getCaptcha(captchaTicket);
            if (!captchaValue.toLowerCase(Locale.ROOT).equals(cacheValue)) {
                authCacheService.setCaptchaRequire(loginCode, true);
                AuthResultVO resultVO = new AuthResultVO().failAndCaptchaRequire(5 * 60 * 1000);
                throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_CAPTCHA_CHECK_FAIL).withData(resultVO);
            }
        }
        //登录逻辑
        try {
            LoginAuthenticationToken authToken = new LoginAuthenticationToken(loginCode, password);
            Subject subject = SecurityUtils.getSubject();
            subject.login(authToken);
            //生成refresh token信息
            String aliveId = GenerateID.next();
            //构建用户token信息
            UserVO user = authService.getUserByLoginCode(loginCode);
            //生成access token
            int accessTokenExpireTimes = AuthBaseSettingProvider.ACCESS_TOKEN_EXPIRE_SECOND;
            String accessToken = AccessTokenUtil.createAccessToken(request,user.getUserId(),user.getLoginCode(),aliveId,accessTokenExpireTimes,authPrivateKey);
            //生成refresh token
            int refreshTokenExpireTimes = AuthBaseSettingProvider.REFRESH_TOKEN_EXPIRE_SECOND;
            String refreshToken = RefreshTokenUtil.createRefreshToken(request,user.getUserId(),user.getLoginCode(),aliveId, refreshTokenExpireTimes, authRefreshSecretKey);
            //保存到数据库 用于用于下次请求access token时候检查用
            try{
                authService.saveAliveInstance(refreshToken,refreshTokenExpireTimes);
            }catch (Throwable e){
                //这步也不是那么必要，如果出错了，先保证能正常登录
                log.error(e.getMessage(),e);
            }
            //返回给前端
            return new AuthResultVO().success(accessToken, refreshToken);
        } catch (IncorrectCredentialsException e) {
            AuthResultVO resultVO;
            if (authCacheService.isCaptchaRequire(loginCode)) {
                resultVO = new AuthResultVO().failAndCaptchaRequire(5 * 60 * 1000);
            } else {
                resultVO = new AuthResultVO().fail();
            }
            throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_FAILED).withData(resultVO);
        } catch (UnknownAccountException e) {
            AuthResultVO resultVO = new AuthResultVO().fail();
            throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_FAILED).withData(resultVO);
        } catch (ExcessiveAttemptsException e) {
            AuthResultVO resultVO = new AuthResultVO().fail();
            throw new ExDeclaredException(ExAuthServerErrorCodeEnum.AUTH_RETRY_TO_MUCH).withData(resultVO);
        } catch (Throwable e) {
            throw e;
        }
    }



    @RequestMapping(path = "refresh-token", method = RequestMethod.POST)
    public String refreshToken(HttpServletRequest request, HttpServletResponse  response,@RequestParam String refreshToken){
        ExAssert.isNull(refreshToken);
        //验签检查refresh token
        try {
            RefreshTokenUtil.verifyRefreshToken(refreshToken, authRefreshSecretKey);
        }catch (TokenExpiredException e){
            response.setStatus(401);
            return "";
        }
        //分配新的accessToken
        int accessTokenExpireTimes = AuthBaseSettingProvider.ACCESS_TOKEN_EXPIRE_SECOND;
        Triple<String,String,String> triple = RefreshTokenUtil.getUserInfo(refreshToken);
        String accessToken = AccessTokenUtil.createAccessToken(request,triple.getMiddle(),triple.getRight(),triple.getLeft(),accessTokenExpireTimes,authPrivateKey);
        return accessToken;
    }

    @RequestMapping(path = "captcha", method = RequestMethod.POST)
    public CaptchaVO captcha(HttpServletRequest request) throws IOException {
        CaptchaVO captchaVO = new CaptchaVO();
        CaptchaCheckVO checkValue = CaptchaUtil.generateCaptchaCheckVO();
        //添加到缓存中
        authCacheService.setCaptcha(checkValue.getTicket(), checkValue.getCaptchaValue());
        captchaVO.setTicket(checkValue.getTicket());
        captchaVO.setCaptchaBase64(checkValue.getCaptchaImageBase64());
        return captchaVO;

    }


}
