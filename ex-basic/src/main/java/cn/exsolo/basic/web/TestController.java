package cn.exsolo.basic.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.basic.org.service.OrgManageService;
import cn.exsolo.basic.tree.CommonTreeWebController;
import cn.exsolo.basic.tree.ITreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author prestolive
 * @date 2024/11/15
 **/

@Component
@RequestMapping("api/ex-basic/tree/")
@RestController()
@AccessProvider(module = "sys", node = "tree", label = "测试树形节点")
public class TestController extends CommonTreeWebController<OrgNodePO> {

    @Autowired
    private OrgManageService orgManageService;

    @Override
    protected ITreeService<OrgNodePO> getTreeService() {
        return orgManageService;
    }
}
