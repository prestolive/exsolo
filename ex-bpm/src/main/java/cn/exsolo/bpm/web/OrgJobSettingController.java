package cn.exsolo.bpm.web;

import cn.exsolo.auth.shiro.ext.stereotype.AccessProvider;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.basic.org.service.OrgManageService;
import cn.exsolo.bpm.BpmSettingProvider;
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
@RequestMapping("api/ex-bpm/org/job-setting/")
@RestController()
@AccessProvider(module = "bpm", node = "org", label = "BPM-组织")
public class OrgJobSettingController {

    @Autowired
    private OrgManageService orgManageService;

    @AccessView
    @RequestMapping(path = "org-nodes", method = RequestMethod.POST)
    public List<OrgNodePO> nodes(@RequestParam(required = false) String parentId) {
        OrgNodePO nodeQueryTemplate=  new OrgNodePO();
        nodeQueryTemplate.setId(parentId);
        nodeQueryTemplate.setGenus(BpmSettingProvider.BPM_DEFAULT_SCHEMA.name());
        return orgManageService.getNodeChildren(nodeQueryTemplate);
    }

}
