package cn.exsolo.bpm.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessEdit;
import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.bpm.org.po.OrgJobPO;
import cn.exsolo.bpm.org.service.OrgJobService;
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
@RequestMapping("api/ex-bpm/org/job/")
@RestController()
@AccessProvider(module = "bpm", node = "org", label = "BPM-组织")
public class OrgJobManagerController {

    @Autowired
    private OrgJobService orgJobService;

    @AccessView
    @RequestMapping(path = "page", method = RequestMethod.POST)
    public PageObject<OrgJobPO> page(
            @RequestJSON Condition cond,
            @RequestJSON Pagination pagination) {
        return orgJobService.page(cond, pagination);
    }

    @AccessView
    @RequestMapping(path = "info", method = RequestMethod.POST)
    public OrgJobPO get(@RequestParam() String id) {
        OrgJobPO info = orgJobService.get(id);
        return info;
    }

    @AccessEdit
    @RequestMapping(path = "add", method = RequestMethod.POST)
    public void add(@RequestJSON() OrgJobPO po) {
        orgJobService.add(po);
    }

    @AccessEdit
    @RequestMapping(path = "update", method = RequestMethod.POST)
    public void update(@RequestJSON() OrgJobPO po) {
        orgJobService.update(po);
    }
    @AccessEdit
    @RequestMapping(path = "delete", method = RequestMethod.POST)
    public void delete(@RequestParam() String id) {
        orgJobService.delete(id);
    }


}
