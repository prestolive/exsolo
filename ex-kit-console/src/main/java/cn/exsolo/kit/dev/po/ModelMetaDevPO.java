package cn.exsolo.kit.dev.po;

import cn.exsolo.batis.core.AbstractPO;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Model管理配置的持久化
 * @author prestolive
 * @date 2024/6/27
 **/
@Table(name="ex_kit_model_meta")
public class ModelMetaDevPO extends AbstractPO {

    @Id
    @Column(name = "id",nullable = false,length = 24,columnDefinition = "varchar(64)")
    private String id;

    @Id
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
