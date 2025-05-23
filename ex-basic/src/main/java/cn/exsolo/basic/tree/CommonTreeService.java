package cn.exsolo.basic.tree;

import cn.exsolo.basic.item.ExTreeErrorCodeEnum;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.kit.utils.ExAssert;
import cn.exsolo.kit.utils.ExPageWork;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 通用树形数据模型
 * @author prestolive
 * @date 2021/5/28
 **/
public abstract class CommonTreeService<T extends CommonTreeNodePO> implements ITreeService<T> {

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

    @Override
    public T getNode(T nodeQueryTemplate) {
        ExAssert.isNull(nodeQueryTemplate.getId());
        return baseDAO.queryBeanByID(nodeQueryTemplate.getClass(), nodeQueryTemplate.getId());
    }

    @Override
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
    @Override
    public T createNode(T node, String parentId) {
        node.setParentId(parentId);
        node.setSortNo(999);
        node.setChildCounts(0);
        defaultValueSetting(node);
        checkNode(node);
        baseDAO.insertOrUpdateValueObject(node);
        //重置同级的的顺序号和内码
        rebuildSelfLevel(node);
        return node;
    }

    /**
     * 修改节点
     * @param node
     * @return
     */
    @Override
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
    @Override
    public List<T> getNodeBrothers(T nodeQueryTemplate) {
        ExAssert.isNull(nodeQueryTemplate.getGenus());
        Condition cond = new Condition();
        cond.eq("genus", nodeQueryTemplate.getGenus());
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
    @Override
    public List<T> getNodeChildren(T nodeQueryTemplate) {
        ExAssert.isNull(nodeQueryTemplate.getGenus());
        Condition cond = new Condition();
        cond.eq("genus", nodeQueryTemplate.getGenus());
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
    @Override
    public void deleteNode(T nodeQueryTemplate) {
        T node = getNode(nodeQueryTemplate);
        processDelete(node);
        //重置同级的的顺序号和内码
        rebuildSelfLevel(node);
    }

    @Override
    public void processDelete(T nodeQueryTemplate){
        ExAssert.isNull(nodeQueryTemplate.getId());
        T node = getNode(nodeQueryTemplate);
        List<T> children = getNodeChildren(node);
        if (children != null && children.size() > 0) {
            for (T child : children) {
                processDelete(child);
            }
        }
        baseDAO.deleteByID(nodeQueryTemplate.getClass(), nodeQueryTemplate.getId());
    }

    private class NodeLoop {
        public void process(Map<String, T> parentMap,List<T> paths,T currNode) {
            paths.add(currNode);
            if (StringUtils.isNotEmpty(currNode.getParentId())) {
                T parent = parentMap.get(currNode.getParentId());
                if (parent != null) {
                    process(parentMap, paths, parent);
                }
            }
        }
    }

    /**
     * 树形节点数据渲染
     * @param nodes
     */
    @Override
    public void treeDataRender(List<T> nodes){

        NodeLoop nodeLoop = new NodeLoop();
        new ExPageWork().pageExecute(nodes, 200, new ExPageWork.IPageExecute<T>() {
            @Override
            public void execute(List<T> list) {
                Set<String> ids = list.stream().map(row->row.getId()).collect(Collectors.toSet());
                Class clz = nodes.get(0).getClass();
                Set<String> codeSet = new HashSet<>();
                for (T node : list) {
                    String innerCode = node.getInnerCode();
                    for (int i = innerCode.length(); i >= 2; i = i - 2) {
                        codeSet.add(innerCode.substring(0,i));
                    }
                }
                List<T> parents =  baseDAO.queryBeanByCond(clz, new Condition().in("innerCode", codeSet));
                Map<String, T> parentMap = new HashMap<>();
                for (T parent : parents) {
                    parentMap.put(parent.getId(),parent);
                }
                for(T node:list){
                    List<T> paths = new ArrayList<>();
                    nodeLoop.process(parentMap,paths,node);
                    Collections.reverse(paths);
                    node.setPaths((List<CommonTreeNodePO>) paths);
                }
            }
        });
    }

    private void rebuildSelfLevel(T node){
        List<T> brothers = getNodeBrothers(node);
        if(StringUtils.isNotEmpty(node.getParentId())){
            T parent = getParent(node);
            rebuildList(parent.getInnerCode(),brothers);
            parent.setChildCounts(brothers.size());
            baseDAO.insertOrUpdateValueObject(parent);
        }else{
            rebuildList("",brothers);
        }
    }

    private int rebuildList(String rootInnerCodePath,List<T> list){
        for (int i=0;i<list.size();i++) {
            T row = list.get(i);
            int index = i % 256;
            String code = String.format("%02x", index).toUpperCase(Locale.ROOT);
            row.setInnerCode(rootInnerCodePath + code);
            row.setDeep(row.getInnerCode().length() / 2);
            row.setSortNo((i + 1) * 10);
            List<T> children = getNodeChildren(row);
            if(children!=null&&children.size()>0){
                row.setChildCounts(children.size());
                baseDAO.insertOrUpdateValueObject(row);
                rebuildList(row.getInnerCode(), children);
            }else{
                baseDAO.insertOrUpdateValueObject(row);
            }
        }
        return list.size();
    }




}
