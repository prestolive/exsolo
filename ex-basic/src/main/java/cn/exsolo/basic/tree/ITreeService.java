package cn.exsolo.basic.tree;

import java.util.List;

/**
 * @author prestolive
 * @date 2024/11/15
 **/
public interface ITreeService<T extends CommonTreeNodePO> {

    T createTemplate();

    T getNode(T nodeQueryTemplate);

    T getParent(T nodeQueryTemplate);

    T createNode(T node, String parentId);

    T modifyNode(T node);

    List<T> getNodeBrothers(T nodeQueryTemplate);

    List<T> getNodeChildren(T nodeQueryTemplate);

    void deleteNode(T nodeQueryTemplate);

    void processDelete(T nodeQueryTemplate);

    void treeDataRender(List<T> nodes);
}
