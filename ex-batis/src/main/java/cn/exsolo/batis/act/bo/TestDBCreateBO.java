package cn.exsolo.batis.act.bo;

import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;

import java.math.BigDecimal;

/**
 * @author prestolive
 * @date 2021/3/8
 **/
@Table("test_db_create")
@Index(name="dddd",unique = true,fields = "code,name")
public class TestDBCreateBO {

    @Column(name="id",primary = true,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name="name",maxLength = 64,datatype = "varchar(64)")
    private String name;

    @Column(name="code",maxLength = 64,datatype = "varchar(64)")
    private String code;

    @Column(name="status",maxLength = 2,datatype = "smallint")
    private Integer status;

    @Column(name="status2",maxLength = 2,datatype = "smallint")
    private Integer status2;

    @Column(name="amount",maxLength = 20,scale = 2,datatype = "decimal(20,2)")
    private BigDecimal amount;

    @Column(name="nam2e",maxLength = 64,nullable = false,datatype = "varchar(128)")
    private String name2;



    public Integer getStatus2() {
        return status2;
    }

    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
