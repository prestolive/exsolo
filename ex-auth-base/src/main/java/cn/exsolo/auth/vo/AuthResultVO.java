package cn.exsolo.auth.vo;

/**
 * @author prestolive
 * @date 2023/5/28
 **/
public class AuthResultVO {

    private Boolean success;

    private String token;

    private Boolean captchaRequire;

    private Long captchaExpireTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public AuthResultVO success(String token){
        this.token =token;
        this.success = true;
        this.captchaRequire = false;
        this.captchaExpireTime = System.currentTimeMillis();
        return this;
    }

    public AuthResultVO fail(){
        this.token = null;
        this.success = false;
        this.captchaRequire = false;
        this.captchaExpireTime = System.currentTimeMillis();
        return this;
    }

    public AuthResultVO failAndCaptchaRequire(long expireMillis){
        this.token = null;
        this.success = false;
        this.captchaRequire = true;
        this.captchaExpireTime = System.currentTimeMillis()+expireMillis;
        return this;
    }
}
