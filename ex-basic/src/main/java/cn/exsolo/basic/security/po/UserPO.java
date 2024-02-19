package cn.exsolo.basic.security.po;

import cn.exsolo.batis.core.AbstractPO;
import cn.exsolo.kit.item.ItemCommStatusEnum;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author prestolive
 * @date 2021/3/30
 **/

@Table(name="ex_user",indexes = @Index(columnList = "loginCode",unique = true))
public class UserPO  extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "loginCode",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String loginCode;

    @Column(name = "userName",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String userName;

    @Column(name = "email",nullable = true,length = 128,columnDefinition = "varchar(128)")
    private String email;

    @Column(name = "phone",nullable = true,length = 64,columnDefinition = "varchar(64)")
    private String phone;

    @Column(name = "status",nullable = false,length = 16,columnDefinition = "varchar(16)")
    private ItemCommStatusEnum status;

    @Column(name = "activeTs",nullable = true,length = 19,columnDefinition = "char(19)")
    private String activeTs;


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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ItemCommStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemCommStatusEnum status) {
        this.status = status;
    }

    public String getActiveTs() {
        return activeTs;
    }

    public void setActiveTs(String activeTs) {
        this.activeTs = activeTs;
    }

}
