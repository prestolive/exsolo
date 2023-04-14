package cn.exsolo.batis.core;

import cn.exsolo.batis.core.stereotype.Column;

/**
 * Created by prestolive on 2017/6/15.
 * @author prestolive
 */
public abstract class AbstractSanBatisPO {

    @Column(name = "ts",nullable = false,maxLength = 19,datatype = "char(19)")
    private String ts;

    @Column(name = "createTs",nullable = false,maxLength = 19,datatype = "char(19)")
    private String createTs;

    private int state;

    public abstract String getId();

    public abstract void setId(String id);

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
