package cn.exsolo.auth.vo;

/**
 * @author prestolive
 * @date 2023/5/30
 **/
public class CaptchaCheckVO {

    private String ticket;

    private String captchaText;

    private String captchaValue;

    private String captchaImageBase64;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getCaptchaText() {
        return captchaText;
    }

    public void setCaptchaText(String captchaText) {
        this.captchaText = captchaText;
    }

    public String getCaptchaValue() {
        return captchaValue;
    }

    public void setCaptchaValue(String captchaValue) {
        this.captchaValue = captchaValue;
    }

    public String getCaptchaImageBase64() {
        return captchaImageBase64;
    }

    public void setCaptchaImageBase64(String captchaImageBase64) {
        this.captchaImageBase64 = captchaImageBase64;
    }
}
