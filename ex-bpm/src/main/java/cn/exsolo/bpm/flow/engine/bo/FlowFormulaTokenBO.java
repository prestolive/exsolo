package cn.exsolo.bpm.flow.engine.bo;

import java.util.Arrays;

/**
 * @author prestolive
 * @date 2021/10/24
 **/
public class FlowFormulaTokenBO {

    private FlowFormulaTokenType tokenType;

    private String token;

    private FlowFormulaTokenBO[] subTokens;

    public FlowFormulaTokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(FlowFormulaTokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public FlowFormulaTokenBO[] getSubTokens() {
        return subTokens;
    }

    public void setSubTokens(FlowFormulaTokenBO[] subTokens) {
        this.subTokens = subTokens;
    }

    @Override
    public String toString() {
        return "FlowRuleItemTokenBO{" +
                "tokenType=" + tokenType +
                ", token='" + token + '\'' +
                ", subTokens=" + Arrays.toString(subTokens) +
                '}';
    }
}
