package cn.exsolo.builder.sdk.item.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;

/**
 * 对象表
 * @author prestolive
 * @date 2023/3/14
 **/
@Table("s_item")
@Index(name = "idx_s_item_tag",unique = false,fields = "tag")
@Index(name = "uq_s_item_a",unique = true,fields = "tag,code")
public class ItemPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "tag",nullable = false,maxLength = 64,datatype = "varchar(64)")
    private String tag;

    /**
     * 对象类型 ITEM_SCHEMA 引用自 ItemSchemaEnum
     */
    @Column(name = "schema",nullable = false,maxLength = 32,datatype = "varchar(32)")
    private String schema;

    @Column(name = "code",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String code;

    @Column(name = "name",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String name;

    /**
     *  来源
     *  引用自 ItemOriginEnum
     *  APPLICATION-*:解决方案 预留，理想是根据产品来设置，还没想好
     *
     */
    @Column(name = "origin",nullable = false,maxLength = 32,datatype = "varchar(32)")
    private String origin;

    @Column(name = "text",nullable = false,maxLength = 2,datatype = "boolean")
    private Boolean text;

    @Column(name = "status",nullable = false,maxLength = 2,datatype = "smallint")
    private Integer status;

    @Column(name = "pid",maxLength = 24,datatype = "char(24)")
    private String pid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Boolean getText() {
        return text;
    }

    public void setText(Boolean text) {
        this.text = text;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
