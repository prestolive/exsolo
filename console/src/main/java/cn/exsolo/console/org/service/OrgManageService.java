package cn.exsolo.console.org.service;

import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.console.item.ExOrgErrorCodeEnum;
import cn.exsolo.console.org.item.OrgNodeStatusEnum;
import cn.exsolo.console.org.po.OrgNodePO;
import cn.exsolo.console.org.po.OrgSchemaPO;
import cn.exsolo.kit.utils.ExAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/9/24
 **/
@Service
public class OrgManageService {

    @Autowired
    private BaseDAO baseDAO;

    public List<OrgSchemaPO> getAllSchema(){
        Condition cond = new Condition();
        cond.orderBy("orderNo");
        return baseDAO.queryBeanByCond(OrgSchemaPO.class,cond);
    }

    public void deleteSchema(String id){
        baseDAO.deleteByID(OrgSchemaPO.class,id);
    }

    public void createSchema(OrgSchemaPO orgSchema){
        ExAssert.isNull(orgSchema.getOrgSchemaName());
        ExAssert.isNull(orgSchema.getOrgSchemaCode());
        if (baseDAO.existsByCond(OrgSchemaPO.class,
                new Condition().lower().eq("orgSchemaName", orgSchema.getOrgSchemaName().toLowerCase()))) {
            throw new ExDeclaredException(ExOrgErrorCodeEnum.SCHEMA_NAME_ALREADY_EXISTS, orgSchema.getOrgSchemaName());
        }
        if (baseDAO.existsByCond(OrgSchemaPO.class,
                new Condition().lower().eq("orgSchemaCode", orgSchema.getOrgSchemaCode().toLowerCase()))) {
            throw new ExDeclaredException(ExOrgErrorCodeEnum.SCHEMA_CODE_ALREADY_EXISTS, orgSchema.getOrgSchemaCode());
        }
        baseDAO.insertOrUpdateValueObject(orgSchema);
    }

    public void modifySchema(OrgSchemaPO orgSchema){
        ExAssert.isNull(orgSchema.getOrgSchemaName());
        ExAssert.isNull(orgSchema.getOrgSchemaCode());
        if (baseDAO.existsByCond(OrgSchemaPO.class,
                new Condition().ne("id",orgSchema.getId()).lower().eq("orgSchemaName", orgSchema.getOrgSchemaName().toLowerCase()))) {
            throw new ExDeclaredException(ExOrgErrorCodeEnum.SCHEMA_NAME_ALREADY_EXISTS, orgSchema.getOrgSchemaName());
        }
        if (baseDAO.existsByCond(OrgSchemaPO.class,
                new Condition().ne("id",orgSchema.getId()).lower().eq("orgSchemaCode", orgSchema.getOrgSchemaCode().toLowerCase()))) {
            throw new ExDeclaredException(ExOrgErrorCodeEnum.SCHEMA_CODE_ALREADY_EXISTS, orgSchema.getOrgSchemaCode());
        }
        baseDAO.insertOrUpdateValueObject(orgSchema);
    }

    public void createNode(OrgNodePO org,String parentId){
        ExAssert.isNull(org.getOrgCode());
        ExAssert.isNull(org.getOrgName());
        org.setParentId(parentId);
        org.setStatus(OrgNodeStatusEnum.NORMAL);
        org.setSortNo(999);
        //FIXME 在同一个节点下检验名称或编码重复
        baseDAO.insertOrUpdateValueObject(org);
    }

    public List<OrgNodePO> getNodeChildren(String id){
        return baseDAO.queryBeanByCond(OrgNodePO.class,new Condition().eq("parentId",id).orderBy("sortNo"));
    }

    /**
     * 删除节点 //FIXME 删除的引用检查
     * @param id
     */
    public void deleteNode(String id){
        ExAssert.isNull(id);
        //FIXME 删除所有子节点
        List<OrgNodePO> children = getNodeChildren(id);
        if(children!=null&&children.size()>0){
            for(OrgNodePO child:children){
                deleteNode(child.getId());
            }
        }
        baseDAO.deleteByID(OrgNodePO.class,id);
    }

    public void changeOrder(String orgId,Integer newPos){
        OrgNodePO org = baseDAO.queryBeanByID(OrgNodePO.class,orgId);
        ExAssert.isNull(org);
        org.setSortNo(newPos);
        baseDAO.insertOrUpdateValueObject(org);
        reSortNode(org.getParentId());
    }

    private void reSortNode(String parentId){
        List<OrgNodePO> list = getNodeChildren(parentId);
        for(int i=0;i<list.size();i++){
            int sortNo = (i+1)*18;
            OrgNodePO row = list.get(i);
            row.setSortNo(sortNo);
            row.setState(2);
        }
        baseDAO.insertOrUpdateValueObjectBatch(list);
    }

}
