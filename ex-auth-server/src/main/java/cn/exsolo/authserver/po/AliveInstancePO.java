package cn.exsolo.authserver.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用于记录refresh token 的表，该表的id就是refresh token中的ticketId
 * @author prestolive
 * @date 2021/10/5
 **/
@Table(name="s_alive")
public class AliveInstancePO extends AbstractPO {

    @Id
    @Column(name = "id",  nullable = false, length = 24, columnDefinition = "char(24)")
    private String id;

    @Column(name = "loginCode",length = 128,columnDefinition = "varchar(128)")
    private String loginCode;

    /**
     * 平台 pc app h5 微信小程序 支付宝小程序等，一般用于约束同一个平台允许在线一个账号
     */
    @Column(name = "platform",length = 32,columnDefinition = "varchar(32)")
    private String platform;
    /**
     * 登录ip
     */
    @Column(name = "ip",length = 64,columnDefinition = "varchar(64)")
    private String ip;

    @Column(name = "ua",length = 256,columnDefinition = "varchar(256)")
    private String ua;

    /**
     * 登录日期和过期日期，也没什么用，纯记录而已
     */
    @Column(name = "activeTs",length = 19,columnDefinition = "char(19)")
    private String activeTs;

    @Column(name = "expireTs",length = 19,columnDefinition = "char(19)")
    private String expireTs;

    /**
     * 状态 0 是normal，1 已经过期通过定时任务定期更新，非0都是要下线的。-1 挤下线 -2 手动强制掉线
     * 只有 -1 -2 会在使用refresh token请求新access token时候直接给予拒绝，其它的状态只是为了查询
     */
    @Column(name = "status",length = 2,columnDefinition = "int(2)")
    private Integer status;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUa() {
        return ua;
    }

    public void setUa(String ua) {
        this.ua = ua;
    }

    public String getActiveTs() {
        return activeTs;
    }

    public void setActiveTs(String activeTs) {
        this.activeTs = activeTs;
    }

    public String getExpireTs() {
        return expireTs;
    }

    public void setExpireTs(String expireTs) {
        this.expireTs = expireTs;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
