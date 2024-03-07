package cn.exsolo.basic.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessEdit;
import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.basic.render.UserInfoDataRender;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.basic.org.service.OrgManageService;
import cn.exsolo.basic.org.vo.OrgTreeNodeVO;
import cn.exsolo.basic.render.UserInfoDataRenderValueMapper;
import cn.exsolo.kit.render.stereotype.DataRenderProvider;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author prestolive
 * @date 2021/9/26
 **/

@Component
@RequestMapping("api/ex-basic/org/")
@RestController()
@AccessProvider(module = "sys", node = "org", label = "系统-组织管理")
public class OrgManageController {

    @Autowired
    private OrgManageService orgManageService;

    @AccessView
    @RequestMapping(path = "tree-node", method = RequestMethod.POST)
    public List<OrgTreeNodeVO> treeNode(@RequestJSON() String schemaCode,@RequestJSON() String parentId,@RequestJSON() Integer deep) {
        List<OrgTreeNodeVO> trees = orgManageService.getTreeNode(schemaCode,parentId,deep);
        return trees;
    }
    @AccessView
    @RequestMapping(path = "get", method = RequestMethod.POST)
    public OrgNodePO getOrg(@RequestParam String id) {
        return orgManageService.getOrg(id);
    }

    @DataRenderProvider(path = "values", keyField = "modifiedBy", dataRenderClass = UserInfoDataRender.class)
    @AccessView
    @RequestMapping(path = "children-page", method = RequestMethod.POST)
    public PageObject<OrgNodePO> page(
            @RequestJSON String schemaCode,
            @RequestJSON String parentId,
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return orgManageService.orgPage(schemaCode,parentId,cond,pagination);
    }

    @AccessEdit
    @RequestMapping(path = "create-node", method = RequestMethod.POST)
    public void createNOde(@RequestJSON OrgNodePO orgNode,@RequestJSON String parentId) {
        orgManageService.createNode(orgNode,parentId);
    }

    @AccessEdit
    @RequestMapping(path = "delete-node", method = RequestMethod.POST)
    public void deleteNode(@RequestParam() String id) {
        orgManageService.deleteNode(id);
    }

    @AccessEdit
    @RequestMapping(path = "modify-node", method = RequestMethod.POST)
    public void modifyNode(@RequestJSON OrgNodePO orgNode) {
        orgManageService.modifyNode(orgNode);
    }

}
