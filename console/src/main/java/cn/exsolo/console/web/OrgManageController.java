package cn.exsolo.console.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessEdit;
import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.console.org.po.OrgNodePO;
import cn.exsolo.console.org.po.OrgSchemaPO;
import cn.exsolo.console.org.service.OrgManageService;
import cn.exsolo.console.org.vo.OrgTreeNodeVO;
import cn.exsolo.console.render.UserInfoDataRenderValueMapper;
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
 * @date 2023/9/26
 **/

@Component
@RequestMapping("api/ex-basic/org/")
@RestController()
@AccessProvider(module = "sys", node = "org", label = "系统-组织管理")
public class OrgManageController {

    @Autowired
    private OrgManageService orgManageService;

    @AccessView
    @RequestMapping(path = "schemas", method = RequestMethod.POST)
    public List<OrgSchemaPO> schemas() {
        List<OrgSchemaPO> schemas = orgManageService.getAllSchema();
        return schemas;
    }

    @AccessEdit
    @RequestMapping(path = "create-schema", method = RequestMethod.POST)
    public void createSchema(@RequestJSON OrgSchemaPO orgSchema) {
        orgManageService.createSchema(orgSchema);
    }

    @AccessEdit
    @RequestMapping(path = "delete-schema", method = RequestMethod.POST)
    public void deleteSchema(@RequestParam() String id) {
        orgManageService.deleteSchema(id);
    }

    @AccessEdit
    @RequestMapping(path = "modify-schema", method = RequestMethod.POST)
    public void modifySchema(@RequestJSON OrgSchemaPO orgSchema) {
        orgManageService.modifySchema(orgSchema);
    }

    @AccessView
    @RequestMapping(path = "tree-node", method = RequestMethod.POST)
    public List<OrgTreeNodeVO> treeNode(@RequestJSON() String schemaId,@RequestJSON() String parentId) {
        List<OrgTreeNodeVO> trees = orgManageService.getTreeNode(schemaId,parentId,3);
        return trees;
    }
    @AccessView
    @RequestMapping(path = "get", method = RequestMethod.POST)
    public OrgNodePO getOrg(@RequestParam String id) {
        return orgManageService.getOrg(id);
    }

    @DataRenderProvider(path = "values", keyFields = {"modifiedBy"}, dataRenderClass = UserInfoDataRenderValueMapper.class)
    @AccessView
    @RequestMapping(path = "children-page", method = RequestMethod.POST)
    public PageObject<OrgNodePO> page(
            @RequestJSON String schemaId,
            @RequestJSON String parentId,
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return orgManageService.orgPage(schemaId,parentId,cond,pagination);
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
