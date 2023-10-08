package cn.exsolo.auth.vo;

/**
 * @author prestolive
 * @date 2021/5/28
 **/
public class AuthResultVO {

    private Boolean success;

    private String accessToken;

    private String refreshToken;

    private Boolean captchaRequire;

    private Long captchaExpireTime;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    public Boolean getCaptchaRequire() {
        return captchaRequire;
    }

    public void setCaptchaRequire(Boolean captchaRequire) {
        this.captchaRequire = captchaRequire;
    }

    public Long getCaptchaExpireTime() {
        return captchaExpireTime;
    }

    public void setCaptchaExpireTime(Long captchaExpireTime) {
        this.captchaExpireTime = captchaExpireTime;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public AuthResultVO success(String accessToken,String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.success = true;
        this.captchaRequire = false;
        this.captchaExpireTime = System.currentTimeMillis();
        return this;
    }

    public AuthResultVO fail() {
        this.accessToken = null;
        this.success = false;
        this.captchaRequire = false;
        this.captchaExpireTime = System.currentTimeMillis();
        return this;
    }

    public AuthResultVO failAndCaptchaRequire(long expireMillis) {
        this.accessToken = null;
        this.success = false;
        this.captchaRequire = true;
        this.captchaExpireTime = System.currentTimeMillis() + expireMillis;
        return this;
    }
}
