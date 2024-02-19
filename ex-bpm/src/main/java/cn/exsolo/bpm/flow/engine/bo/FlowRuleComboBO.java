package cn.exsolo.bpm.flow.engine.bo;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author prestolive
 * @date 2021/10/24
 **/
public class FlowRuleComboBO {

    /**
     * 逻辑类型 and 或 or
     */
    private String logic;

    private FlowRuleItemBO[] items;

    public String getLogic() {
        return logic;
    }

    public void setLogic(String logic) {
        this.logic = logic;
    }

    public FlowRuleItemBO[] getItems() {
        return items;
    }

    public void setItems(FlowRuleItemBO[] items) {
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
