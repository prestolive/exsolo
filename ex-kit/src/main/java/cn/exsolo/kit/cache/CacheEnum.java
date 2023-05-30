package cn.exsolo.kit.cache;

/**
 * @author prestolive
 * @date 2023/5/30
 **/
public enum CacheEnum {
    //账户锁定时间
    AUTH_ACCOUNT_LOCK(10*60),
    //当密码错误后记录错误次数缓存时间
    AUTH_RETRY_COUNT(3*60),
    //要求验证码的时间范围
    CAPTCHA_REQUIRE(5*60),
    //验证码的有效时间
    CAPTCHA_EXPIRE(3*60),
    //通用时间
    COMM_SHORT(1*60),
    COMM_MIDDLE(15*60),
    COMM_LONG(45*60);


    private int expireTimes;

    CacheEnum(int expireTimes) {
        this.expireTimes = expireTimes;
    }

}
