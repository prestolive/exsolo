package cn.exsolo.bpm.flow.engine.bo;

import java.util.Arrays;

/**
 * @author prestolive
 * @date 2021/10/24
 **/
public class FlowPortFilterBO {

    /**
     * 逻辑类型
     * include_all 满足以下所有条件
     * include_any 满足以下任意条件
     * exclude_all 不包含以下所有条件 （以全部满足返回false）
     * exclude_any 不包含以下任意条件 （以下任意满足返回false）
     * execute 执行节点
     */
    private String logic;

    private FlowPortFilterBO[] children;

    private FlowFormulaItemBO[] items;

    public FlowPortFilterBO[] getChildren() {
        return children;
    }

    public void setChildren(FlowPortFilterBO[] children) {
        this.children = children;
    }

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public FlowFormulaItemBO[] getItems() {
        return items;
    }

    public void setItems(FlowFormulaItemBO[] items) {
        this.items = items;
    }



    @Override
    public String toString() {
        return "FlowRuleComboBO{" +
                "logic='" + logic + '\'' +
                ", items=" + Arrays.toString(items) +
                '}';
    }
}
