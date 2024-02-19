package cn.exsolo.kit.item.po;

import cn.exsolo.batis.core.AbstractPO;
import cn.exsolo.kit.item.ItemCommStatusEnum;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * 对象表
 * @author prestolive
 * @date 2021/3/14
 **/
@Table(name="s_item",indexes = {@Index(columnList = "tag"),@Index(columnList = "tag,code",unique = true)})
public class ItemPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "char(24)")
    private String id;

    @Column(name = "tag",nullable = false,length = 64,columnDefinition = "varchar(64)")
    private String tag;

    @Column(name = "itemType",nullable = false,length = 32,columnDefinition = "varchar(32)")
    private String itemType;

    @Column(name = "code",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String code;

    @Column(name = "name",nullable = false,length = 128,columnDefinition = "varchar(128)")
    private String name;

    @Column(name = "sys",nullable = false,length = 2,columnDefinition = "boolean")
    private Boolean sys;

    @Column(name = "text",nullable = false,length = 2,columnDefinition = "boolean")
    private Boolean text;

    @Column(name = "status",nullable = false,length = 16,columnDefinition = "varchar(16)")
    private ItemCommStatusEnum status;

    @Column(name = "pid",length = 24,columnDefinition = "char(24)")
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
