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
import cn.exsolo.bpm.flow.engine.bo.FlowConfigBO;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author prestolive
 * @date 2023/10/31
 **/

@Component
@RequestMapping("api/ex-bpm/flow/")
@RestController()
@AccessProvider(module = "bpm", node = "flow", label = "BPM-流程管理")
public class FlowManageController {

    @Autowired
    private FlowManageService flowManageService;

    @AccessView
    @RequestMapping(path = "page", method = RequestMethod.POST)
    public PageObject<FlowPO> page(
            @RequestJSON() String[] status,
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return flowManageService.page(cond, pagination);
    }

    @AccessView
    @RequestMapping(path = "info", method = RequestMethod.POST)
    public FlowInfoBO get(@RequestParam() String id) {
        FlowInfoBO info = new FlowInfoBO();
        info.setFlow(flowManageService.get(id));
        info.setVersionList(flowManageService.getVersionList(info.getFlow().getCode()));
        return info;
    }

    @AccessEdit
    @RequestMapping(path = "add", method = RequestMethod.POST)
    public void add(@RequestJSON() FlowPO flow) {
        flowManageService.updateOrSaveFlow(flow);
    }

    @AccessEdit
    @RequestMapping(path = "update", method = RequestMethod.POST)
    public void update(@RequestJSON() FlowPO flow) {
        flowManageService.updateOrSaveFlow(flow);
    }

    @AccessEdit
    @RequestMapping(path = "content/load", method = RequestMethod.POST)
    public FlowConfigBO saveContent(@RequestParam() String code,@RequestParam Integer version) {
        return flowManageService.getContent(code,version);
    }

    @AccessEdit
    @RequestMapping(path = "content/new-version", method = RequestMethod.POST)
    public FlowConfigBO newVersion(@RequestParam() String code) {
        FlowPO flowPO = flowManageService.getByCode(code);
        FlowConfigBO config = flowManageService.addNewVersion(flowPO);
        return config;
    }

    @AccessEdit
    @RequestMapping(path = "content/active-version", method = RequestMethod.POST)
    public void activeVersion(@RequestParam() String code,@RequestParam() Integer version) {
        flowManageService.activeVersion(code,version);
    }


    @AccessEdit
    @RequestMapping(path = "content/save-content", method = RequestMethod.POST)
    public void saveContent(@RequestJSON() FlowConfigBO config) {
        flowManageService.saveVersion(config);
    }

}
