package cn.santoise.batis.act.bo;

import cn.santoise.batis.core.stereotype.Column;
import cn.santoise.batis.core.stereotype.Index;
import cn.santoise.batis.core.stereotype.Table;

/**
 * @author wbingy
 * @date 2023/3/8
 **/
@Table("test_db_create")
@Index(name = "test_indexxxx",unique = true,fields = "code,name")
@Index(name = "dfdfdf",unique = false,fields = "code")
public class TestDBCreateBO {

    @Column(name="id",primary = true,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name="name",maxLength = 64,datatype = "varchar(64)")
    private String name;

    @Column(name="code",maxLength = 64,datatype = "varchar(64)")
    private String code;

    @Column(name="status",maxLength = 2,datatype = "smallint")
    private Integer status;

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
