package cn.exsolo.starter.web;

import cn.exsolo.basic.render.UserInfoDataRender;
import cn.exsolo.basic.security.po.UserPO;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.kit.render.stereotype.DataRenderProvider;
import cn.exsolo.starter.web.vo.TestPkgVO;
import cn.exsolo.starter.web.vo.UserTreeTestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件描述
 *
 * @author prestolive
 * @version 1.0
 * @date 2025/4/9 10:20
 * Copyright © 2025 厦门象屿股份有限公司. All Rights Reserved
 **/

@Component
@RequestMapping("api/auth/test/")
@RestController()
public class TestRenderController {

    @Autowired
    private BaseDAO baseDAO;
    @DataRenderProvider(path = "user(loop->children)", keyField = "userId", dataRenderClass = UserInfoDataRender.class)
    @RequestMapping(path = "render", method = RequestMethod.GET)
    public TestPkgVO test(){
        List<UserPO> tessCases= baseDAO.queryBeanByCond(UserPO.class,new Condition());
        int count = 1;
        TestPkgVO pkg = new TestPkgVO();
        UserTreeTestVO daye = new UserTreeTestVO();
        daye.setUserId(tessCases.get((count++)%tessCases.size()-1).getId());

        UserTreeTestVO baba = new UserTreeTestVO();
        baba.setUserId(tessCases.get((count++)%tessCases.size()-1).getId());
        daye.setChildren(Arrays.asList(baba));
        UserTreeTestVO erzi = new UserTreeTestVO();
        erzi.setUserId(tessCases.get((count++)%tessCases.size()-1).getId());
        baba.setChildren(Arrays.asList(erzi));

        UserTreeTestVO sunzi = new UserTreeTestVO();
        sunzi.setUserId(tessCases.get((count++)%tessCases.size()-1).getId());
        erzi.setChildren(Arrays.asList(sunzi));

        pkg.setUser(daye);
        return pkg;
    }
}
