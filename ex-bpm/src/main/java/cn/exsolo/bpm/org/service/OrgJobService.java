package cn.exsolo.bpm.org.service;

import cn.exsolo.auth.utils.SecurityUserContext;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.bpm.org.ExBpmOrgErrorCodeEnum;
import cn.exsolo.bpm.org.po.OrgJobPO;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.kit.utils.ExAssert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 职责档案
 * @author prestolive
 * @date 2021/6/27
 **/
@Service
public class OrgJobService {

    @Autowired
    private BaseDAO baseDAO;

    public OrgJobPO get(String id) {
        ExAssert.isNull(id);
        return baseDAO.queryBeanByID(OrgJobPO.class, id);
    }
    /**
     * 分页查询
     * @param fCond 前端动态条件
     * @param pagination 分页参数
     * @return
     */
    public PageObject<OrgJobPO> page(Condition fCond, Pagination pagination) {
        Condition cond = new Condition();
        if (fCond != null) {
            cond.and(fCond);
        }
        cond.orderBy("grade", Condition.DESC);
        cond.orderBy("createTs", Condition.DESC);
        cond.orderBy("id", Condition.DESC);
        return baseDAO.queryBeanPageByCond(OrgJobPO.class, cond, pagination);
    }

    /**
     * 新增
     * @param po
     */
    public void add(OrgJobPO po){
        checkCode(po);
        if(StringUtils.isEmpty(po.getModifiedBy())){
            po.setModifiedBy(SecurityUserContext.getUserID());
        }
        baseDAO.insertOrUpdateValueObject(po);
    }

    /**
     * 更新
     * @param po
     */
    public void update(OrgJobPO po){
        ExAssert.isNull(po, po.getId());
        checkCode(po);
        if(StringUtils.isEmpty(po.getModifiedBy())){
            po.setModifiedBy(SecurityUserContext.getUserID());
        }
        baseDAO.insertOrUpdateValueObject(po);
    }

    private void checkCode(OrgJobPO po){
        Condition cond = new Condition();
        cond.eq("code", po.getCode());

        if(StringUtils.isNotEmpty(po.getId())){
            cond.ne("id",po.getId());
        }
        if(baseDAO.existsByCond(OrgJobPO.class, cond)){
            throw new ExDeclaredException(ExBpmOrgErrorCodeEnum.JOB_CODE_DUPLICATE, po.getCode());
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(String id){
        ExAssert.isNull(id);
        baseDAO.deleteByID(OrgJobPO.class,id);
    }
}
