package cn.exsolo.basic.tree;

import cn.exsolo.basic.item.ExTreeErrorCodeEnum;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.kit.utils.ExAssert;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Locale;

/**
 * 通用树形数据模型
 * @author prestolive
 * @date 2024/5/28
 **/
public abstract class CommonTreeService<T extends CommonTreeNodePO> {

    /**
     * 判断节点是否重复
     * @param brother
     * @param target
     * @return
     */
    protected abstract boolean nodeEquals(T brother, T target);

    /**
     * 设置默认值
     * @param target
     */
    protected abstract void defaultValueSetting(T target);

    @Autowired
    private BaseDAO baseDAO;

    public T getNode(T nodeQueryTemplate) {
        ExAssert.isNull(nodeQueryTemplate.getId());
        return baseDAO.queryBeanByID(nodeQueryTemplate.getClass(), nodeQueryTemplate.getId());
    }

    public T getParent(T nodeQueryTemplate) {
        ExAssert.isNull(nodeQueryTemplate.getParentId());
        return baseDAO.queryBeanByID(nodeQueryTemplate.getClass(), nodeQueryTemplate.getParentId());
    }

    /**
     * 创建节点
     * @param node
     * @param parentId
     * @return
     */
    public T createNode(T node, String parentId) {
        node.setParentId(parentId);
        node.setSortNo(999);
        node.setChildCounts(0);
        defaultValueSetting(node);
        checkNode(node);
        baseDAO.insertOrUpdateValueObject(node);
        //重置同级的的顺序号和内码
        rebuildOrderAndInnerCode(node);

        return node;
    }

    /**
     * 修改节点
     * @param node
     * @return
     */
    public T modifyNode(T node) {
        checkNode(node);
        defaultValueSetting(node);
        baseDAO.insertOrUpdateValueObject(node);
        return node;
    }

    /**
     * 检查节点
     * @param node
     */
    private void checkNode(T node) {
        List<T> brothers = getNodeBrothers(node);
        if (brothers.stream().filter(row -> !row.getId().equals(node.getId())).anyMatch(row -> nodeEquals(row, node))) {
            //TODO
            throw new ExDeclaredException(ExTreeErrorCodeEnum.TREE_NODE_NAME_ALREADY_EXISTS);
        }
    }

    /**
     * 获取所有同级兄弟 包含自己
     * @param nodeQueryTemplate
     * @return
     */
    public List<T> getNodeBrothers(T nodeQueryTemplate) {
        ExAssert.isNull(nodeQueryTemplate.getSchema());
        Condition cond = new Condition();
        cond.eq("schema", nodeQueryTemplate.getSchema());
        if (StringUtils.isEmpty(nodeQueryTemplate.getParentId())) {
            cond.isEmpty("parentId");
        } else {
            cond.eq("parentId", nodeQueryTemplate.getParentId());
        }
        cond.orderBy("sortNo");
        cond.orderBy("id");
        return baseDAO.queryBeanByCond(nodeQueryTemplate.getClass(), cond);
    }
    /**
     * 获得下一代
     * @param nodeQueryTemplate
     * @return
     */
    public List<T> getNodeChildren(T nodeQueryTemplate) {
        ExAssert.isNull(nodeQueryTemplate.getSchema());
        Condition cond = new Condition();
        cond.eq("schema", nodeQueryTemplate.getSchema());
        if (StringUtils.isEmpty(nodeQueryTemplate.getId())) {
            cond.isEmpty("parentId");
        } else {
            cond.eq("parentId", nodeQueryTemplate.getId());
        }
        cond.orderBy("sortNo");
        cond.orderBy("id");
        return baseDAO.queryBeanByCond(nodeQueryTemplate.getClass(), cond);
    }


    /**
     * 删除节点
     * @param nodeQueryTemplate
     */
    public void deleteNode(T nodeQueryTemplate) {
        ExAssert.isNull(nodeQueryTemplate.getId());
        T node = getNode(nodeQueryTemplate);
        List<T> children = getNodeChildren(node);
        if (children != null && children.size() > 0) {
            for (T child : children) {
                deleteNode(child);
            }
        }
        baseDAO.deleteByID(nodeQueryTemplate.getClass(), nodeQueryTemplate.getId());
        //重置同级的的顺序号和内码
        rebuildOrderAndInnerCode(node);
    }

    /**
     * 重置 node 当前层级的兄弟和自己的内码
     * @param node
     */
    public void rebuildOrderAndInnerCode(T node) {
//        List<T> list = getNodeBrothers(node);
//        String innerCodePath = "";
//        String parentId = node.getParentId();
//        if (StringUtils.isNotEmpty(parentId)) {
//            T parent = getParent(node);
//            parent.setChildCounts(list.size());
//            baseDAO.insertOrUpdateValueObject(parent);
//            innerCodePath += (parent.getInnerCode() == null ? "" : parent.getInnerCode());
//        }
//        for (int i = 0; i < list.size(); i++) {
//            int index = i % 256;
//            String code = String.format("%02x", index).toUpperCase(Locale.ROOT);
//            T row = list.get(i);
//            row.setInnerCode(innerCodePath + code);
//            row.setDeep(row.getInnerCode().length() / 2);
//            row.setSortNo((i + 1) * 10);
//            baseDAO.insertOrUpdateValueObject(row);
//            rebuildOrderAndInnerCode(row);
//        }

    }

}
