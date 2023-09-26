package cn.exsolo.kit.item.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;
import cn.exsolo.kit.item.ItemSchemaEnum;

/**
 * 对象类型表
 * @author prestolive
 * @date 2021/3/20
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
    private ItemSchemaEnum schema;

    @Column(name = "name",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String name;

    @Column(name = "module",maxLength = 256,datatype = "varchar(64)")
    private String module;

    @Column(name = "clz",maxLength = 256,datatype = "varchar(256)")
    private String clz;

    @Column(name = "customAble",maxLength = 2,datatype = "boolean")
    private Boolean customAble;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public ItemSchemaEnum getSchema() {
        return schema;
    }

    public void setSchema(ItemSchemaEnum schema) {
        this.schema = schema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public Boolean getCustomAble() {
        return customAble;
    }

    public void setCustomAble(Boolean customAble) {
        this.customAble = customAble;
    }
}
