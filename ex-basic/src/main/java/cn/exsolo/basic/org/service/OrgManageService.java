package cn.exsolo.basic.org.service;

import cn.exsolo.auth.utils.SecurityUserContext;
import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.basic.tree.CommonTreeService;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.kit.item.ItemCommStatusEnum;
import cn.exsolo.kit.utils.ExAssert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author prestolive
 * @date 2021/9/24
 **/
@Service
public class OrgManageService extends CommonTreeService<OrgNodePO> {

    @Autowired
    private BaseDAO baseDAO;

    @Override
    protected boolean nodeEquals(OrgNodePO brother, OrgNodePO target) {
        return brother.getOrgName().equals(target.getOrgName());
    }

    @Override
    protected void defaultValueSetting(OrgNodePO target) {
        target.setModifiedBy(SecurityUserContext.getUserID());
        if(target.getStatus()==null){
            target.setStatus(ItemCommStatusEnum.NORMAL);
        }
    }


    public PageObject<OrgNodePO> orgPage(String schema, String parentId, Condition fCond, Pagination pagination) {
        ExAssert.isNull(schema);
        Condition cond = new Condition();
        cond.eq("schema", schema);
        if (StringUtils.isNotEmpty(parentId)) {
            OrgNodePO nodeQueryTemplate=  new OrgNodePO();
            nodeQueryTemplate.setId(parentId);
            OrgNodePO parent = getNode(nodeQueryTemplate);
            cond.lkl("innerCode", parent.getInnerCode() + "__");
        }
        if (fCond != null) {
            cond.and(fCond);
        }
        cond.orderBy("sortNo");
        cond.orderBy("id");
        return baseDAO.queryBeanPageByCond(OrgNodePO.class, cond, pagination);
    }

}
