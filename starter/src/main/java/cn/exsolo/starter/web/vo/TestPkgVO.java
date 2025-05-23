package cn.exsolo.starter.web.vo;

import java.util.List;

/**
 * 文件描述
 *
 * @author prestolive
 * @version 1.0
 * @date 2025/4/9 10:24
 * Copyright © 2025 厦门象屿股份有限公司. All Rights Reserved
 **/
public class TestPkgVO {

    private List<UserTreeTestVO> userList;

    private UserTreeTestVO user;

    public List<UserTreeTestVO> getUserList() {
        return userList;
    }

    public void setUserList(List<UserTreeTestVO> userList) {
        this.userList = userList;
    }

    public UserTreeTestVO getUser() {
        return user;
    }

    public void setUser(UserTreeTestVO user) {
        this.user = user;
    }
}
