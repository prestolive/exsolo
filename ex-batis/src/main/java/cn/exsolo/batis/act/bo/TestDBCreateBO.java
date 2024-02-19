package cn.exsolo.batis.act.bo;


import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author prestolive
 * @date 2021/3/8
 **/
@Table(name="test_db_create",indexes = @Index(unique = true,columnList = "code,name"))

public class TestDBCreateBO {

    @Id
    @Column(name="id",length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name="name",length = 64,columnDefinition = "varchar(64)")
    private String name;

    @Column(name="code",length = 64,columnDefinition = "varchar(64)")
    private String code;

    @Column(name="status",length = 2,columnDefinition = "smallint")
    private Integer status;

    @Column(name="status2",length = 2,columnDefinition = "smallint")
    private Integer status2;

    @Column(name="amount",length = 20,scale = 2,columnDefinition = "decimal(20,2)")
    private BigDecimal amount;

    @Column(name="nam2e",length = 64,nullable = false,columnDefinition = "varchar(128)")
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
