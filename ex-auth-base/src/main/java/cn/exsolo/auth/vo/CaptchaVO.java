package cn.exsolo.auth.vo;

/**
 * @author prestolive
 * @date 2023/5/30
 **/
public class CaptchaVO {

    private String ticket;

    private String captchaBase64;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getCaptchaBase64() {
        return captchaBase64;
    }

    public void setCaptchaBase64(String captchaBase64) {
        this.captchaBase64 = captchaBase64;
    }
}
