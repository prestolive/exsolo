package cn.exsolo.bpm.org.service;

import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.basic.org.service.OrgManageService;
import cn.exsolo.basic.security.po.UserPO;
import cn.exsolo.batis.core.*;
import cn.exsolo.bpm.org.ExBpmOrgErrorCodeEnum;
import cn.exsolo.bpm.org.po.OrgJobUserRelaPO;
import cn.exsolo.bpm.org.po.OrgUserRelaPO;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.kit.utils.ExAssert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author prestolive
 * @date 2021/9/16
 **/
@Service
public class OrgSettingService {

    @Autowired
    private BaseDAO baseDAO;

    @Autowired
    private OrgManageService orgManageService;

    public OrgNodePO getOrg(String orgId){
        ExAssert.isEmpty(orgId);
        return baseDAO.queryBeanByID(OrgNodePO.class,orgId);
    }

    /**
     * 新增用户和组织关联
     * @param orgId
     * @param userIds
     */
    public void addUserRela(String orgId, List<String> userIds,OrgJobUserRelaPO orgJobUserRela){
        ExAssert.isEmpty(orgId, userIds);
        for(String userId:userIds){
            Condition existCond = new Condition();
            existCond.eq("orgId",orgId).eq("userId",userId);
            if(orgJobUserRela!=null){
                existCond.eq("orgJobUserRelaId",orgJobUserRela.getId());
            }
            OrgUserRelaPO exist = baseDAO.queryOneBeanByCond(OrgUserRelaPO.class,existCond);
            if(exist==null){
                exist = new OrgUserRelaPO();
                exist.setOrgId(orgId);
                exist.setUserId(userId);
                if(orgJobUserRela!=null){
                    exist.setOrgJobUserRelaId(orgJobUserRela.getId());
                    exist.setOrgJobId(orgJobUserRela.getOrgJobId());
                }else{
                    exist.setOrgJobId(null);
                    exist.setOrgJobUserRelaId(null);
                }
            }
            baseDAO.insertOrUpdateValueObject(exist);
        }
    }

    /**
     * 删除用户组织关系
     * @param orgUserId
     */
    public void dropUserRela(String orgUserId){
        ExAssert.isEmpty(orgUserId);
        OrgUserRelaPO rela = baseDAO.queryBeanByID(OrgUserRelaPO.class,orgUserId);
        if(StringUtils.isNotEmpty(rela.getOrgJobUserRelaId())){
            throw new ExDeclaredException(ExBpmOrgErrorCodeEnum.ORG_USER_JOB_RELA_MUST_DELETE_FROM_JOB);
        }
        baseDAO.deleteByID(OrgUserRelaPO.class,orgUserId);
    }

    /**
     * 分页查询组织下的用户
     * @param orgId
     * @param fCond
     * @param pagination
     * @return
     */
    public PageObject<OrgUserRelaPO> userPage(String orgId,Boolean includeChild,Condition fCond, Pagination pagination) {
        OrgNodePO org = baseDAO.queryBeanByID(OrgNodePO.class, orgId);
        Condition cond = new Condition();
        if (fCond != null) {
            cond.and(fCond);
        }
        //按用户过滤
        ConditionFilter userFilter = new ConditionFilter("userId", UserPO.class, "id");
        if (fCond != null) {
            userFilter.and(fCond.withDomain1());
        }
        cond.exist(userFilter);
        //按组织的innerCode过滤所有下属组织的用户
        ConditionFilter orgFilter = new ConditionFilter("orgId", OrgNodePO.class, "id");
        if(includeChild!=null&&includeChild.booleanValue()){
            orgFilter.lkl("innerCode",org.getInnerCode());
        }else{
            orgFilter.eq("id",org.getId());
        }
        if (fCond != null) {
            orgFilter.and(fCond.withDomain2());
        }
        cond.exist(orgFilter);
        cond.orderBy("createTs", Condition.DESC);
        cond.orderBy("id", Condition.DESC);
        return baseDAO.queryBeanPageByCond(OrgUserRelaPO.class, cond, pagination);
    }

    /**
     * 新增用户在组织中的岗位设置
     * @param orgId
     * @param userIds
     */
    public void addOrgJobRela(String orgId,String orgJobId, List<String> userIds){
        ExAssert.isEmpty(orgId,orgJobId, userIds);
        for(String userId:userIds){
            if(baseDAO.existsByCond(OrgJobUserRelaPO.class,new Condition().eq("orgId",orgId).eq("orgJobId",orgJobId).eq("userId",userId))){
                continue;
            }
            OrgJobUserRelaPO rela = new OrgJobUserRelaPO();
            rela.setOrgId(orgId);
            rela.setOrgJobId(orgJobId);
            rela.setUserId(userId);
            baseDAO.insertOrUpdateValueObject(rela);
            List<String> subUserIds=  new ArrayList<>();
            subUserIds.add(userId);
            //自动增加用户和组织的关联关系
            addUserRela(orgId,subUserIds,rela);
        }
    }

    /**
     * 删除用户在组织中的岗位设置
     * @param orgJobUserRelaId
     */
    public void dropOrgJobRela(String orgJobUserRelaId){
        ExAssert.isEmpty(orgJobUserRelaId);
        baseDAO.deleteByCond(OrgUserRelaPO.class,new Condition().eq("orgJobUserRelaId",orgJobUserRelaId));
        baseDAO.deleteByID(OrgJobUserRelaPO.class,orgJobUserRelaId);
    }

    /**
     * 获取部门下的职责设置
     * @param orgId
     * @return
     */
    public List<OrgJobUserRelaPO> getOrgJobUserRelaList(String orgId){
        Condition cond = new Condition();
        cond.eq("orgId",orgId);
        cond.orderBy("userId");
        cond.orderBy("orgJobId");
        return baseDAO.queryBeanByCond(OrgJobUserRelaPO.class, cond);
    }

//    /**
//     * 为部门设置岗位
//     * @param jobId
//     * @param orgId
//     */
//    public void addJob2Org(String jobId, String orgId) {
//        ExAssert.isNull(jobId, orgId);
//        List<OrgJobRelaPO> exists = baseDAO.queryBeanByCond(OrgJobRelaPO.class,
//                new Condition().eq("orgId", orgId).eq("orgJobId", jobId));
//        if (exists.size() > 0) {
//            throw new ExDeclaredException(ExBpmOrgErrorCodeEnum.JOB_ALREADY_RELA_ORG);
//        }
//        OrgJobRelaPO rela = new OrgJobRelaPO();
//        rela.setOrgId(orgId);
//        rela.setOrgJobId(jobId);
//        rela.setModifiedBy(SecurityUserContext.getUserID());
//        baseDAO.insertOrUpdateValueObject(rela);
//    }
//
//    /**
//     * 从部门删除岗位
//     * @param orgJobRelaId
//     */
//    public void dropJobFromOrg(String orgJobRelaId) {
//        ExAssert.isNull(orgJobRelaId);
//        OrgJobRelaPO rela = baseDAO.queryBeanByID(OrgJobRelaPO.class, orgJobRelaId);
//        if(rela!=null){
//            //FIXME 删除相应的人员关联、BPM的流程设置
//            baseDAO.deleteByID(OrgJobRelaPO.class, orgJobRelaId);
//        }
//    }
//
//    public void setJobUser(String orgId,String orgJobRelaId,List<String> userIds){
//        ExAssert.isNull(orgJobRelaId);
//        ExAssert.isEmpty(userIds);
//        for(String userId:userIds){
//            Condition existCond = new Condition();
//        }
//    }
//
//
//    public OrgJobUserRelaPO get(String id) {
//        ExAssert.isNull(id);
//        return baseDAO.queryBeanByID(OrgJobUserRelaPO.class, id);
//    }
//
//    /**
//     * 分页查询
//     * @param fCond 前端动态条件
//     * @param pagination 分页参数
//     * @return
//     */
//    public PageObject<OrgJobUserRelaPO> page(Condition fCond, Pagination pagination) {
//        Condition cond = new Condition();
//        if (fCond != null) {
//            cond.and(fCond);
//        }
//        cond.orderBy("createTs", Condition.DESC);
//        cond.orderBy("id", Condition.DESC);
//        return baseDAO.queryBeanPageByCond(OrgJobUserRelaPO.class, cond, pagination);
//    }
//
//    /**
//     * 新增
//     * @param po
//     */
//    public void add(OrgJobUserRelaPO po){
//        if(StringUtils.isEmpty(po.getModifiedBy())){
//            po.setModifiedBy(SecurityUserContext.getUserID());
//        }
//        baseDAO.insertOrUpdateValueObject(po);
//    }
//
//    /**
//     * 更新
//     * @param po
//     */
//    public void update(OrgJobUserRelaPO po){
//        ExAssert.isNull(po, po.getId());
//        if(StringUtils.isEmpty(po.getModifiedBy())){
//            po.setModifiedBy(SecurityUserContext.getUserID());
//        }
//        OrgJobUserRelaPO exist = baseDAO.queryBeanByID(OrgJobUserRelaPO.class, po.getId());
////        exist.setName(po.getName());
//        baseDAO.insertOrUpdateValueObject(exist);
//    }
//
//    /**
//     * 删除
//     * @param id
//     */
//    public void delete(String id){
//        ExAssert.isNull(id);
//        baseDAO.deleteByID(OrgJobUserRelaPO.class,id);
//    }
}
