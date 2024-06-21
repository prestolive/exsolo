package cn.exsolo.basic.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessEdit;
import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.auth.utils.SecurityUserContext;
import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.basic.org.service.OrgManageService;
import cn.exsolo.basic.org.vo.OrgTreeNodeVO;
import cn.exsolo.basic.render.UserInfoDataRender;
import cn.exsolo.basic.security.po.UserPO;
import cn.exsolo.basic.tree.CommonTreeNodePO;
import cn.exsolo.basic.tree.CommonTreeService;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.kit.item.ItemCommStatusEnum;
import cn.exsolo.kit.render.stereotype.DataRenderProvider;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import com.google.common.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Or;
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

    @DataRenderProvider(path = "values", keyField = "modifiedBy", dataRenderClass = UserInfoDataRender.class)
    @AccessView
    @RequestMapping(path = "children-page", method = RequestMethod.POST)
    public PageObject<OrgNodePO> page(
            @RequestJSON String schema,
            @RequestJSON String parentId,
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return orgManageService.orgPage(schema,parentId,cond,pagination);
    }

    @AccessView
    @RequestMapping(path = "get", method = RequestMethod.POST)
    public OrgNodePO getOrg(@RequestParam String id) {
        OrgNodePO nodeQueryTemplate=  new OrgNodePO();
        nodeQueryTemplate.setId(id);
        return orgManageService.getNode(nodeQueryTemplate);
    }

    @AccessView
    @RequestMapping(path = "nodes", method = RequestMethod.POST)
    public List<OrgNodePO> nodes(@RequestParam() String schema,@RequestParam(required = false) String parentId) {
        OrgNodePO nodeQueryTemplate=  new OrgNodePO();
        nodeQueryTemplate.setId(parentId);
        nodeQueryTemplate.setSchema(schema);
        return orgManageService.getNodeChildren(nodeQueryTemplate);
    }

    @AccessEdit
    @RequestMapping(path = "create-node", method = RequestMethod.POST)
    public OrgNodePO createNOde(@RequestJSON OrgNodePO orgNode,@RequestJSON String parentId) {
        return orgManageService.createNode(orgNode,parentId);
    }

    @AccessEdit
    @RequestMapping(path = "delete-node", method = RequestMethod.POST)
    public void deleteNode(@RequestParam() String id) {
        OrgNodePO nodeQueryTemplate=  new OrgNodePO();
        nodeQueryTemplate.setId(id);
        orgManageService.deleteNode(nodeQueryTemplate);
    }

    @AccessEdit
    @RequestMapping(path = "modify-node", method = RequestMethod.POST)
    public OrgNodePO modifyNode(@RequestJSON OrgNodePO orgNode) {
        return orgManageService.modifyNode(orgNode);
    }

}
