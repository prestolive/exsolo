package cn.exsolo.bpm.org.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 组织职责表
 * @author prestolive
 * @date 2021/6/26
 **/
@Table(name="ex_bpm_org_job",indexes = @Index(columnList = "name",unique = true))
public class OrgJobPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "name",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String name;

    @Column(name = "code",nullable = false,length = 64,columnDefinition = "varchar(64)")
    private String code;

    @Column(name = "grade",nullable = false,length = 64,columnDefinition = "varchar(64)")
    private String grade;

    @Column(name = "modifiedBy",length = 24,columnDefinition = "char(24)")
    private String modifiedBy;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}