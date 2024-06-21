package cn.exsolo.basic.item;

/**
 * 树形工具管理
 * @author prestolive
 * @date 2021/4/1
 **/
public enum ExTreeErrorCodeEnum {

    TREE_NODE_NAME_ALREADY_EXISTS("节点在同级下已存在");

    private String label;

    ExTreeErrorCodeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
