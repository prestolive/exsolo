package cn.exsolo.kit.item.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 对象类型表
 * @author prestolive
 * @date 2021/3/20
 **/
@Table(name="s_item_tag",indexes = @Index(columnList = "name",unique = true))
public class ItemTagPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 64,columnDefinition = "varchar(24)")
    private String id;

    /**
     * 对象类型 ITEM_SCHEMA 引用自 ItemSchemaEnum
     */
    @Column(name = "itemType",nullable = false,length = 32,columnDefinition = "varchar(32)")
    private String itemType;

    @Column(name = "name",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String name;

    @Column(name = "module",length = 256,columnDefinition = "varchar(64)")
    private String module;

    @Column(name = "clz",length = 256,columnDefinition = "varchar(256)")
    private String clz;

    @Column(name = "customAble",length = 2,columnDefinition = "boolean")
    private Boolean customAble;

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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getClz() {
        return clz;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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
