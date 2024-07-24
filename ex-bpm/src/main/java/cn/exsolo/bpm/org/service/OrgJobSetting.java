package cn.exsolo.bpm.org.service;

import cn.exsolo.auth.utils.SecurityUserContext;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.bpm.org.ExBpmOrgErrorCodeEnum;
import cn.exsolo.bpm.org.po.OrgJobRelaPO;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.kit.utils.ExAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author prestolive
 * @date 2021/9/16
 **/
@Service
public class OrgJobSetting {

    @Autowired
    private BaseDAO baseDAO;

    /**
     * 为部门设置岗位
     * @param jobId
     * @param orgId
     */
    public void addJob2Org(String jobId, String orgId) {
        ExAssert.isNull(jobId, orgId);
        List<OrgJobRelaPO> exists = baseDAO.queryBeanByCond(OrgJobRelaPO.class,
                new Condition().eq("orgId", orgId).eq("orgJobId", jobId));
        if (exists.size() > 0) {
            throw new ExDeclaredException(ExBpmOrgErrorCodeEnum.JOB_ALREADY_RELA_ORG);
        }
        OrgJobRelaPO rela = new OrgJobRelaPO();
        rela.setOrgId(orgId);
        rela.setOrgJobId(jobId);
        rela.setModifiedBy(SecurityUserContext.getUserID());
        baseDAO.insertOrUpdateValueObject(rela);
    }

    /**
     * 从部门删除岗位
     * @param orgJobRelaId
     */
    public void dropJobFromOrg(String orgJobRelaId) {
        ExAssert.isNull(orgJobRelaId);
        OrgJobRelaPO rela = baseDAO.queryBeanByID(OrgJobRelaPO.class, orgJobRelaId);
        if(rela!=null){
            //FIXME 删除相应的人员关联、BPM的流程设置
            baseDAO.deleteByID(OrgJobRelaPO.class, orgJobRelaId);
        }
    }

    public void setJobUser(String orgId,String orgJobRelaId,List<String> userIds){
        ExAssert.isNull(orgJobRelaId);
        ExAssert.isEmpty(userIds);
        for(String userId:userIds){
            Condition existCond = new Condition();
        }
    }
}
