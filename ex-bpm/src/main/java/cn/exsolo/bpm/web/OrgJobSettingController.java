package cn.exsolo.bpm.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessEdit;
import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.basic.org.service.OrgManageService;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.bpm.BpmSettingProvider;
import cn.exsolo.bpm.org.po.OrgJobUserRelaPO;
import cn.exsolo.bpm.org.service.OrgJobSetting;
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
 * @date 2021/7/10
 **/
@Component
@RequestMapping("api/ex-bpm/org/user/")
@RestController()
@AccessProvider(module = "bpm", node = "org", label = "BPM-组织")
public class OrgJobSettingController {

    @Autowired
    private OrgManageService orgManageService;

    @Autowired
    private OrgJobSetting orgJobSetting;


    @AccessView
    @RequestMapping(path = "org-nodes", method = RequestMethod.POST)
    public List<OrgNodePO> nodes(@RequestParam(required = false) String parentId) {
        OrgNodePO nodeQueryTemplate=  new OrgNodePO();
        nodeQueryTemplate.setId(parentId);
        nodeQueryTemplate.setGenus(BpmSettingProvider.BPM_DEFAULT_SCHEMA.name());
        return orgManageService.getNodeChildren(nodeQueryTemplate);
    }

    @AccessView
    @RequestMapping(path = "page", method = RequestMethod.POST)
    public PageObject<OrgJobUserRelaPO> page(
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return orgJobSetting.page(cond, pagination);
    }

    @AccessView
    @RequestMapping(path = "info", method = RequestMethod.POST)
    public OrgJobUserRelaPO get(@RequestParam() String id) {
        OrgJobUserRelaPO info = orgJobSetting.get(id);
        return info;
    }

    @AccessEdit
    @RequestMapping(path = "add", method = RequestMethod.POST)
    public void add(@RequestJSON() OrgJobUserRelaPO po) {
        orgJobSetting.add(po);
    }

    @AccessEdit
    @RequestMapping(path = "update", method = RequestMethod.POST)
    public void update(@RequestJSON() OrgJobUserRelaPO po) {
        orgJobSetting.update(po);
    }
    @AccessEdit
    @RequestMapping(path = "delete", method = RequestMethod.POST)
    public void delete(@RequestParam() String id) {
        orgJobSetting.delete(id);
    }

}
