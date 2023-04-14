package cn.exsolo.console.security.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;
import cn.exsolo.console.item.ExUserStatusEnum;
import cn.exsolo.kit.item.stereotype.BaseData;

/**
 * @author prestolive
 * @date 2023/3/30
 **/

@Table("ex_user")
@BaseData(name="系统用户表")
@Index(name = "uq_ex_user_a",unique = true,fields = "userCode")
public class UserPO  extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "userCode",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String userCode;

    @Column(name = "userName",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String userName;

    @Column(name = "email",nullable = true,maxLength = 128,datatype = "varchar(128)")
    private String email;

    @Column(name = "phone",nullable = true,maxLength = 64,datatype = "varchar(64)")
    private String phone;

    @Column(name = "status",nullable = false,maxLength = 16,datatype = "varchar(16)")
    private ExUserStatusEnum status;

    @Column(name = "activeTs",nullable = true,maxLength = 19,datatype = "char(19)")
    private String activeTs;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
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

    public ExUserStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ExUserStatusEnum status) {
        this.status = status;
    }

    public String getActiveTs() {
        return activeTs;
    }

    public void setActiveTs(String activeTs) {
        this.activeTs = activeTs;
    }
}
