package cn.exsolo.builder.sdk.item.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;

/**
 * 对象类型表
 * @author prestolive
 * @date 2023/3/20
 **/
@Table("s_item_tag")
@Index(name="uq_item_tag_name",unique = true,fields = "name")
public class ItemTagPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 64,datatype = "varchar(24)")
    private String id;

    /**
     * 对象类型 ITEM_SCHEMA 引用自 ItemSchemaEnum
     */
    @Column(name = "schema",nullable = false,maxLength = 32,datatype = "varchar(32)")
    private String schema;

    @Column(name = "name",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String name;

    @Column(name = "clz",nullable = false,maxLength = 256,datatype = "varchar(256)")
    private String clz;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }
}
