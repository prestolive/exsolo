package cn.exsolo.bpm.console.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author prestolive
 * @date 2021/10/25
 **/

@Table(name="ex_bpm_flow_content")
public class FlowContentPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "varchar(64)")
    private String id;

    @Column(name = "content",columnDefinition = "text")
    private String content;

    @Override
    public String getId() {
        return id;
    }

    @Override
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
