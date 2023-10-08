package cn.exsolo.kit.item.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Index;
import cn.exsolo.batis.core.stereotype.Table;
import cn.exsolo.kit.item.ItemCommStatusEnum;

/**
 * 对象表
 * @author prestolive
 * @date 2021/3/14
 **/
@Table("s_item")
@Index(name = "idx_s_item_tag",unique = false,fields = "tag")
@Index(name = "uq_s_item_a",unique = true,fields = "tag,code")
public class ItemPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "tag",nullable = false,maxLength = 64,datatype = "varchar(64)")
    private String tag;

    @Column(name = "itemType",nullable = false,maxLength = 32,datatype = "varchar(32)")
    private String itemType;

    @Column(name = "code",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String code;

    @Column(name = "name",nullable = false,maxLength = 128,datatype = "varchar(128)")
    private String name;

    @Column(name = "sys",nullable = false,maxLength = 2,datatype = "boolean")
    private Boolean sys;

    @Column(name = "text",nullable = false,maxLength = 2,datatype = "boolean")
    private Boolean text;

    @Column(name = "status",nullable = false,maxLength = 16,datatype = "varchar(16)")
    private ItemCommStatusEnum status;

    @Column(name = "pid",maxLength = 24,datatype = "char(24)")
    private String pid;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
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

    public Boolean getSys() {
        return sys;
    }

    public void setSys(Boolean sys) {
        this.sys = sys;
    }

    public Boolean getText() {
        return text;
    }

    public void setText(Boolean text) {
        this.text = text;
    }

    public ItemCommStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ItemCommStatusEnum status) {
        this.status = status;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
