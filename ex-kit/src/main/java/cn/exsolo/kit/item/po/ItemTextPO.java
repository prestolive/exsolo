package cn.exsolo.kit.item.po;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.batis.core.stereotype.Table;

/**
 * @author prestolive
 * @date 2023/3/15
 **/
@Table("s_item_text")
public class ItemTextPO extends AbstractSanBatisPO {

    @Column(name = "id",primary = true,nullable = false,maxLength = 24,datatype = "char(24)")
    private String id;

    @Column(name = "content",nullable = false,datatype = "text")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
