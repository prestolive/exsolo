package cn.exsolo.authserver.service;

import cn.exsolo.kit.cache.CacheEnum;
import cn.exsolo.kit.cache.IExCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author prestolive
 * @date 2023/5/30
 **/
@Service
public class AuthCacheService {

    @Autowired
    private IExCache iExCache;

    public void setCaptcha(String ticket,String captchaValue){
        String cacheKey = "auth:captcha:"+ticket;
        iExCache.getCache(CacheEnum.CAPTCHA_EXPIRE).putString(cacheKey,captchaValue);
    }

    public String getCaptcha(String ticket){
        String cacheKey = "auth:captcha:"+ticket;
        return iExCache.getCache(CacheEnum.CAPTCHA_EXPIRE).getString(cacheKey);
    }

    public int addAuthRetryCount(String loginCode){
        String cacheKey = "auth:retry:"+loginCode;
        Integer count = iExCache.getCache(CacheEnum.AUTH_RETRY_COUNT).getInt(cacheKey);
        if(count ==null){
            count=0;
        }
        ++count;
        iExCache.getCache(CacheEnum.AUTH_RETRY_COUNT).putInt(cacheKey,count);
        return count;
    }
    public void removeAuthRetryCount(String loginCode){
        String cacheKey = "auth:retry:"+loginCode;
        iExCache.getCache(CacheEnum.AUTH_RETRY_COUNT).remove(cacheKey);
    }

    public void setCaptchaRequire(String loginCode,boolean require){
        String cacheKey = "auth:captchaRequire:"+loginCode;
        iExCache.getCache(CacheEnum.CAPTCHA_REQUIRE).putBoolean(cacheKey,require);
    }

    public boolean isCaptchaRequire(String loginCode){
        String cacheKey = "auth:captchaRequire:"+loginCode;
        return iExCache.getCache(CacheEnum.CAPTCHA_REQUIRE).isTrue(cacheKey);
    }

}
