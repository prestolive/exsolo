package cn.exsolo.console.security.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Table;
import cn.exsolo.kit.item.stereotype.BaseData;

/**
 * 一个用户最多保存5条记录
 * 1、每修改一次代码，增加一行，如果和当前密码一样则抛错。
 * 2、每次修改成功后保存，删除出当前密码那行以外，所有创建时间到现在少于3天的密码，以及删除5条以外的记录。
 * @author prestolive
 * @date 2021/3/30
 **/

@Table("ex_user_encrypt")
@BaseData(name="系统用户密码表")
public class UserEncryptPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "userId",nullable = false,maxLength = 24,datatype = "char(24)")
    private String userId;

    @Column(name = "salt",nullable = false,maxLength = 8,datatype = "char(8)")
    private String salt;

    @Column(name = "encrypt",nullable = false,maxLength = 32,datatype = "char(32)")
    private String encrypt;

    @Column(name = "level",nullable = true,maxLength = 2,datatype = "smallint")
    private Integer level;

    @Column(name = "active",nullable = false,maxLength = 2,datatype = "boolean")
    private Boolean active;

    @Column(name = "expireTs",nullable = false,maxLength = 19,datatype = "char(19)")
    private String expireTs;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(String encrypt) {
        this.encrypt = encrypt;
    }

    public String getExpireTs() {
        return expireTs;
    }

    public void setExpireTs(String expireTs) {
        this.expireTs = expireTs;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
