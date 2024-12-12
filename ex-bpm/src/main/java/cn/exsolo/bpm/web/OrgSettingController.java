package cn.exsolo.bpm.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessEdit;
import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.basic.org.service.OrgManageService;
import cn.exsolo.basic.render.OrgInfoDataRender;
import cn.exsolo.basic.render.UserInfoDataRender;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.bpm.BpmSettingProvider;
import cn.exsolo.bpm.org.po.OrgJobPO;
import cn.exsolo.bpm.org.po.OrgJobUserRelaPO;
import cn.exsolo.bpm.org.po.OrgUserRelaPO;
import cn.exsolo.bpm.org.service.OrgJobService;
import cn.exsolo.bpm.org.service.OrgSettingService;
import cn.exsolo.bpm.render.OrgJobDataRender;
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
 * @date 2021/7/10
 **/
@Component
@RequestMapping("api/ex-bpm/org/")
@RestController()
@AccessProvider(module = "bpm", node = "org", label = "BPM-组织")
public class OrgSettingController {

    @Autowired
    private OrgManageService orgManageService;

    @Autowired
    private OrgSettingService orgSettingService;

    /** 岗位设置 **/

    @Autowired
    private OrgJobService orgJobService;

    @AccessView
    @RequestMapping(path = "job/page", method = RequestMethod.POST)
    public PageObject<OrgJobPO> jobPage(
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return orgJobService.page(cond, pagination);
    }

    @AccessView
    @RequestMapping(path = "job/info", method = RequestMethod.POST)
    public OrgJobPO jobGet(@RequestParam() String id) {
        OrgJobPO info = orgJobService.get(id);
        return info;
    }

    @AccessEdit
    @RequestMapping(path = "job/add", method = RequestMethod.POST)
    public void jobAdd(@RequestJSON() OrgJobPO po) {
        orgJobService.add(po);
    }

    @AccessEdit
    @RequestMapping(path = "job/update", method = RequestMethod.POST)
    public void jobUpdate(@RequestJSON() OrgJobPO po) {
        orgJobService.update(po);
    }
    @AccessEdit
    @RequestMapping(path = "job/delete", method = RequestMethod.POST)
    public void jobDelete(@RequestParam() String id) {
        orgJobService.delete(id);
    }

    /** 组织设置 **/

    @AccessView
    @RequestMapping(path = "org-nodes", method = RequestMethod.POST)
    public List<OrgNodePO> nodes(@RequestParam(required = false) String parentId) {
        OrgNodePO nodeQueryTemplate=  new OrgNodePO();
        nodeQueryTemplate.setId(parentId);
        nodeQueryTemplate.setGenus(BpmSettingProvider.BPM_DEFAULT_SCHEMA.name());
        return orgManageService.getNodeChildren(nodeQueryTemplate);
    }

    @AccessView
    @DataRenderProvider(path = "",keyField = "id", dataRenderClass = OrgInfoDataRender.class)
    @RequestMapping(path = "info", method = RequestMethod.POST)
    public OrgNodePO org(@RequestParam String orgId) {
        return orgSettingService.getOrg(orgId);
    }

    @AccessView
    @DataRenderProvider(path = "values",keyField = "orgId", dataRenderClass = OrgInfoDataRender.class)
    @DataRenderProvider(path = "values",keyField = "userId", dataRenderClass = UserInfoDataRender.class)
    @DataRenderProvider(path = "values",keyField = "orgJobId", dataRenderClass = OrgJobDataRender.class)
    @RequestMapping(path = "user-page", method = RequestMethod.POST)
    public PageObject<OrgUserRelaPO> page(
            @RequestJSON String orgId,
            @RequestJSON Boolean includeChild,
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return orgSettingService.userPage(orgId,includeChild, cond, pagination);
    }

    @AccessEdit
    @RequestMapping(path = "user-rela", method = RequestMethod.POST)
    public void orgUserRela(@RequestJSON String orgId,@RequestJSON List<String> userIds) {
        orgSettingService.addUserRela(orgId,userIds,null);
    }

    @AccessEdit
    @RequestMapping(path = "user-drop", method = RequestMethod.POST)
    public void orgUserDrop(@RequestJSON String orgUserId) {
        orgSettingService.dropUserRela(orgUserId);
    }

    @AccessEdit
    @RequestMapping(path = "job-rela", method = RequestMethod.POST)
    public void orgJobUserRela(@RequestJSON String orgId,@RequestJSON String orgJobId,@RequestJSON List<String> userIds) {
        orgSettingService.addOrgJobRela(orgId,orgJobId,userIds);
    }

    @AccessEdit
    @RequestMapping(path = "job-drop", method = RequestMethod.POST)
    public void orgJobUserDrop(@RequestJSON String orgJobUserRelaId) {
        orgSettingService.dropOrgJobRela(orgJobUserRelaId);
    }

    @AccessView
    @DataRenderProvider(path = "",keyField = "orgId", dataRenderClass = OrgInfoDataRender.class)
    @DataRenderProvider(path = "",keyField = "userId", dataRenderClass = UserInfoDataRender.class)
    @DataRenderProvider(path = "",keyField = "orgJobId", dataRenderClass = OrgJobDataRender.class)
    @RequestMapping(path = "job-list", method = RequestMethod.POST)
    public List<OrgJobUserRelaPO> orgJobUserList(@RequestJSON String orgId) {
        return orgSettingService.getOrgJobUserRelaList(orgId);
    }

//    @AccessView
//    @RequestMapping(path = "page", method = RequestMethod.POST)
//    public PageObject<OrgJobUserRelaPO> page(
//            @RequestJSON Condition cond,
//            @RequestJSON Pagination pagination) {
//        return orgJobSettingService.page(cond, pagination);
//    }

//    @AccessView
//    @RequestMapping(path = "info", method = RequestMethod.POST)
//    public OrgJobUserRelaPO get(@RequestParam() String id) {
//        OrgJobUserRelaPO info = orgJobSettingService.get(id);
//        return info;
//    }

//    @AccessEdit
//    @RequestMapping(path = "add", method = RequestMethod.POST)
//    public void add(@RequestJSON() OrgJobUserRelaPO po) {
//        orgJobSettingService.add(po);
//    }
//
//    @AccessEdit
//    @RequestMapping(path = "update", method = RequestMethod.POST)
//    public void update(@RequestJSON() OrgJobUserRelaPO po) {
//        orgJobSettingService.update(po);
//    }
//    @AccessEdit
//    @RequestMapping(path = "delete", method = RequestMethod.POST)
//    public void delete(@RequestParam() String id) {
//        orgJobSettingService.delete(id);
//    }

}
