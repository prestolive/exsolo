package cn.exsolo.console.org.service;

import cn.exsolo.auth.utils.SecurityUserContext;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.console.item.ExOrgErrorCodeEnum;
import cn.exsolo.console.org.po.OrgNodePO;
import cn.exsolo.console.org.po.OrgSchemaPO;
import cn.exsolo.console.org.vo.OrgTreeNodeVO;
import cn.exsolo.kit.item.ItemCommStatusEnum;
import cn.exsolo.kit.utils.ExAssert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2023/9/24
 **/
@Service
public class OrgManageService {

    @Autowired
    private BaseDAO baseDAO;

    public List<OrgSchemaPO> getAllSchema() {
        Condition cond = new Condition();
        cond.orderBy("orderNo");
        return baseDAO.queryBeanByCond(OrgSchemaPO.class, cond);
    }

    public void deleteSchema(String id) {
        baseDAO.deleteByID(OrgSchemaPO.class, id);
    }

    public void createSchema(OrgSchemaPO orgSchema) {
        checkSchema(orgSchema);
        orgSchema.setDefaultSchema(false);
        orgSchema.setOrderNo(0);
        baseDAO.insertOrUpdateValueObject(orgSchema);
    }

    public void modifySchema(OrgSchemaPO orgSchema) {
        checkSchema(orgSchema);
        baseDAO.insertOrUpdateValueObject(orgSchema);
    }

    private void checkSchema(OrgSchemaPO orgSchema) {
        ExAssert.isNull(orgSchema.getOrgSchemaName());
        ExAssert.isNull(orgSchema.getOrgSchemaCode());
        Condition existCond = new Condition();
        if (orgSchema.getId() != null) {
            existCond.ne("id", orgSchema.getId());
        }
        if (baseDAO.existsByCond(OrgSchemaPO.class,
                new Condition().and(existCond).lower().eq("orgSchemaName", orgSchema.getOrgSchemaName().toLowerCase()))) {
            throw new ExDeclaredException(ExOrgErrorCodeEnum.SCHEMA_NAME_ALREADY_EXISTS, orgSchema.getOrgSchemaName());
        }
        if (baseDAO.existsByCond(OrgSchemaPO.class,
                new Condition().and(existCond).lower().eq("orgSchemaCode", orgSchema.getOrgSchemaCode().toLowerCase()))) {
            throw new ExDeclaredException(ExOrgErrorCodeEnum.SCHEMA_CODE_ALREADY_EXISTS, orgSchema.getOrgSchemaCode());
        }
    }

    public OrgNodePO getOrg(String id) {
        return baseDAO.queryBeanByID(OrgNodePO.class, id);
    }

    public void createNode(OrgNodePO org, String parentId) {
        org.setParentId(parentId);
        org.setStatus(ItemCommStatusEnum.NORMAL);
        org.setSortNo(999);
        org.setModifiedBy(SecurityUserContext.getUserID());
        checkNode(org, parentId);
        baseDAO.insertOrUpdateValueObject(org);
        //重置同级的的顺序号和内码
        rebuildOrderAndInnerCode(org.getSchemaCode(), org.getParentId());
    }

    public void modifyNode(OrgNodePO org) {
        checkNode(org, org.getParentId());
        org.setModifiedBy(SecurityUserContext.getUserID());
        baseDAO.insertOrUpdateValueObject(org);
    }

    private void checkNode(OrgNodePO orgNode, String parentId) {
        ExAssert.isNull(orgNode.getOrgCode());
        ExAssert.isNull(orgNode.getOrgName());
        Condition existCond = new Condition();
        existCond.eq("parentId", parentId);
        if (orgNode.getId() != null) {
            existCond.ne("id", orgNode.getId());
        }
        if (baseDAO.existsByCond(OrgNodePO.class,
                new Condition().and(existCond).lower().eq("orgName", orgNode.getOrgName().toLowerCase()))) {
            throw new ExDeclaredException(ExOrgErrorCodeEnum.ORG_NAME_ALREADY_EXISTS, orgNode.getOrgName());
        }
    }

    /**
     * 获得下一代
     *
     * @param schemaCode
     * @param parentId
     * @return
     */
    public List<OrgNodePO> getNodeChildren(String schemaCode, String parentId) {
        ExAssert.isNull(schemaCode);
        Condition cond = new Condition();
        if (StringUtils.isEmpty(parentId)) {
            cond.isNull(parentId);
        } else {
            cond.eq("parentId", parentId);
        }
        cond.orderBy("sortNo");
        cond.orderBy("id");
        return baseDAO.queryBeanByCond(OrgNodePO.class, cond);
    }


    public PageObject<OrgNodePO> orgPage(String schemaCode, String parentId, Condition fCond, Pagination pagination) {
        ExAssert.isNull(schemaCode);
        Condition cond = new Condition();
        cond.eq("schemaCode", schemaCode);
        if (StringUtils.isNotEmpty(parentId)) {
            OrgNodePO parent = getOrg(parentId);
            cond.lkl("innerCode", parent.getInnerCode() + "__");
        }
        if (fCond != null) {
            cond.and(fCond);
        }
        cond.orderBy("sortNo");
        cond.orderBy("id");
        return baseDAO.queryBeanPageByCond(OrgNodePO.class, cond, pagination);
    }

    public void deleteNode(String id) {
        OrgNodePO node = getOrg(id);
        deleteNode(node);
    }

    /**
     * 删除节点 //FIXME 删除的引用检查
     *
     * @param node
     */
    public void deleteNode(OrgNodePO node) {
        ExAssert.isNull(node);
        //FIXME 删除所有子节点
        List<OrgNodePO> children = getNodeChildren(node.getSchemaCode(), node.getId());
        if (children != null && children.size() > 0) {
            for (OrgNodePO child : children) {
                deleteNode(child);
            }
        }
        baseDAO.deleteByID(OrgNodePO.class, node.getId());
    }

    public void changeOrder(String orgId, Integer newPos) {
        OrgNodePO org = getOrg(orgId);
        ExAssert.isNull(org);
        org.setSortNo(newPos);
        baseDAO.insertOrUpdateValueObject(org);
//        reSortNode(org.getParentId());
    }

//    private void reSortNode(String parentId) {
//        List<OrgNodePO> list = getNodeChildren(parentId);
//        for (int i = 0; i < list.size(); i++) {
//            int sortNo = (i + 1) * 18;
//            OrgNodePO row = list.get(i);
//            row.setSortNo(sortNo);
//            row.setState(2);
//        }
//        baseDAO.insertOrUpdateValueObjectBatch(list);
//    }

    /**
     * 获取树形节点
     *
     * @param schemaCode  组织类型ID
     * @param parentId  父级ID
     * @param deepLevel 往下加载几级数据
     * @return
     */
    public List<OrgTreeNodeVO> getTreeNode(String schemaCode, String parentId, Integer deepLevel) {
        Condition cond = new Condition();
        cond.eq("schemaCode", schemaCode);
        if (StringUtils.isNotEmpty(parentId)) {
            cond.eq("parentId", parentId);
        }
        cond.orderBy("innerCode");
        List<OrgNodePO> list = baseDAO.queryBeanByCond(OrgNodePO.class, cond);
        //将第一级作为种子
        List<OrgTreeNodeVO> trees = list.stream().filter(row -> StringUtils.isEmpty(row.getParentId())).map(row -> treePO2VO(row)).collect(Collectors.toList());
        for (OrgTreeNodeVO item : trees) {
            deepFetch(item, list);
        }
        return trees;
    }

    private OrgTreeNodeVO treePO2VO(OrgNodePO po) {
        OrgTreeNodeVO vo = new OrgTreeNodeVO();
        vo.setValue(po.getId());
        vo.setParentId(po.getParentId());
        vo.setLabel(po.getOrgName());
        vo.setCode(po.getOrgCode());
        vo.setSortNo(po.getSortNo());
        vo.setInnerCode(po.getInnerCode());
        vo.setChildCounts(po.getChildCounts());
        return vo;
    }

    private void deepFetch(OrgTreeNodeVO curr, List<OrgNodePO> list) {
        String currId = curr.getValue();
        List<OrgTreeNodeVO> children = new ArrayList<>();
        Iterator<OrgNodePO> it = list.iterator();
        while (it.hasNext()) {
            OrgNodePO po = it.next();
            if (StringUtils.isEmpty(po.getParentId())) {
                continue;
            }
            if (!po.getParentId().equals(currId)) {
                continue;
            }
            it.remove();
            children.add(treePO2VO(po));
        }
        curr.setChildren(children);
        for (OrgTreeNodeVO child : children) {
            deepFetch(child, list);
        }
    }


    public void rebuildOrderAndInnerCode(String schemaCode, String parentId) {
        List<OrgNodePO> list = getNodeChildren(schemaCode, parentId);
        if (list == null || list.size() == 0) {
            return;
        }
        String innerCodePath = "";
        if (StringUtils.isNotEmpty(parentId)) {
            OrgNodePO parent = getOrg(parentId);
            innerCodePath += (parent.getInnerCode() == null ? "" : parent.getInnerCode());
        }
        for (int i = 0; i < list.size(); i++) {
            int index = i % 256;
            String code = String.format("%02x", index);
            OrgNodePO row = list.get(i);
            row.setInnerCode(innerCodePath + code);
            row.setSortNo((i + 1) * 10);
            baseDAO.insertOrUpdateValueObject(row);
            rebuildOrderAndInnerCode(schemaCode, row.getId());
        }

    }

    public static void main(String[] args) {
        System.out.println(String.format("%02x", 15).toUpperCase(Locale.ROOT));
    }


}
