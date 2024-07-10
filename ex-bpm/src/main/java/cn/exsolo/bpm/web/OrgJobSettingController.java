package cn.exsolo.bpm.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.basic.item.ExOrgSchemaEnum;
import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.basic.org.service.OrgManageService;
import cn.exsolo.basic.render.UserInfoDataRender;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.bpm.BpmSettingProvider;
import cn.exsolo.kit.render.stereotype.DataRenderProvider;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author prestolive
 * @date 2024/7/10
 **/
@Component
@RequestMapping("api/ex-bpm/org/job-setting/")
@RestController()
@AccessProvider(module = "bpm", node = "org", label = "BPM-组织")
public class OrgJobSettingController {

    @Autowired
    private OrgManageService orgManageService;

    @DataRenderProvider(path = "values", keyField = "modifiedBy", dataRenderClass = UserInfoDataRender.class)
    @AccessView
    @RequestMapping(path = "org-children-page", method = RequestMethod.POST)
    public PageObject<OrgNodePO> page(
            @RequestJSON String parentId,
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        ExOrgSchemaEnum defaultScheme = BpmSettingProvider.BPM_DEFAULT_SCHEMA;
        return orgManageService.orgPage(defaultScheme.name(),parentId,cond,pagination);
    }
}
