package cn.exsolo.bpm.flow.engine.bo;

import java.util.Arrays;

/**
 * @author prestolive
 * @date 2023/10/24
 **/
public class FlowRuleItemBO {

    /**
     * 用于显示的完整公式
     */
    private String formula;

    private FlowRuleItemTokenBO[] tokens;

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public FlowRuleItemTokenBO[] getTokens() {
        return tokens;
    }

    public void setTokens(FlowRuleItemTokenBO[] tokens) {
        this.tokens = tokens;
    }


    @Override
    public String toString() {
        return "FlowRuleItemBO{" +
                "formula='" + formula + '\'' +
                ", tokens=" + Arrays.toString(tokens) +
                '}';
    }
}
