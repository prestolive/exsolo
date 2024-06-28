package cn.exsolo.bpm.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessEdit;
import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.bpm.console.bo.FlowInfoBO;
import cn.exsolo.bpm.console.po.FlowPO;
import cn.exsolo.bpm.console.service.FlowManageService;
import cn.exsolo.bpm.org.po.OrgDutyPO;
import cn.exsolo.bpm.org.service.OrgDutyService;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author prestolive
 * @date 2024/6/27
 **/

@Component
@RequestMapping("api/ex-bpm/org/duty/")
@RestController()
@AccessProvider(module = "bpm", node = "org", label = "BPM-组织")
public class OrgDutyManagerController {

    @Autowired
    private OrgDutyService orgDutyService;

    @AccessView
    @RequestMapping(path = "page", method = RequestMethod.POST)
    public PageObject<OrgDutyPO> page(
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return orgDutyService.page(cond, pagination);
    }

    @AccessView
    @RequestMapping(path = "info", method = RequestMethod.POST)
    public OrgDutyPO get(@RequestParam() String id) {
        OrgDutyPO info = orgDutyService.get(id);
        return info;
    }

    @AccessEdit
    @RequestMapping(path = "add", method = RequestMethod.POST)
    public void add(@RequestJSON() OrgDutyPO po) {
        orgDutyService.add(po);
    }

    @AccessEdit
    @RequestMapping(path = "update", method = RequestMethod.POST)
    public void update(@RequestJSON() OrgDutyPO po) {
        orgDutyService.update(po);
    }

}
