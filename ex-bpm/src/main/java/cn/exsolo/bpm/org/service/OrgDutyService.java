package cn.exsolo.bpm.org.service;

import cn.exsolo.auth.utils.SecurityUserContext;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.bpm.org.po.OrgDutyPO;
import cn.exsolo.kit.utils.ExAssert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 职责档案
 * @author prestolive
 * @date 2024/6/27
 **/
@Service
public class OrgDutyService {

    @Autowired
    private BaseDAO baseDAO;

    public OrgDutyPO get(String id) {
        ExAssert.isNull(id);
        return baseDAO.queryBeanByID(OrgDutyPO.class, id);
    }
    /**
     * 分页查询
     * @param fCond 前端动态条件
     * @param pagination 分页参数
     * @return
     */
    public PageObject<OrgDutyPO> page(Condition fCond, Pagination pagination) {
        Condition cond = new Condition();
        if (fCond != null) {
            cond.and(fCond);
        }
        cond.orderBy("createTs", Condition.DESC);
        cond.orderBy("id", Condition.DESC);
        return baseDAO.queryBeanPageByCond(OrgDutyPO.class, cond, pagination);
    }

    /**
     * 新增
     * @param po
     */
    public void add(OrgDutyPO po){
        if(StringUtils.isEmpty(po.getModifiedBy())){
            po.setModifiedBy(SecurityUserContext.getUserID());
        }
        baseDAO.insertOrUpdateValueObject(po);
    }

    /**
     * 更新
     * @param po
     */
    public void update(OrgDutyPO po){
        ExAssert.isNull(po, po.getId());
        if(StringUtils.isEmpty(po.getModifiedBy())){
            po.setModifiedBy(SecurityUserContext.getUserID());
        }
        OrgDutyPO exist = baseDAO.queryBeanByID(OrgDutyPO.class, po.getId());
        exist.setName(po.getName());
        baseDAO.insertOrUpdateValueObject(exist);
    }
}
