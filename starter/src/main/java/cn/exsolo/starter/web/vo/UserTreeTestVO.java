package cn.exsolo.starter.web.vo;

import java.util.List;

/**
 * 文件描述
 *
 * @author prestolive
 * @version 1.0
 * @date 2025/4/9 10:23
 * Copyright © 2025 厦门象屿股份有限公司. All Rights Reserved
 **/
public class UserTreeTestVO {

    private String userId;

    private List<UserTreeTestVO> children;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<UserTreeTestVO> getChildren() {
        return children;
    }

    public void setChildren(List<UserTreeTestVO> children) {
        this.children = children;
    }
}
