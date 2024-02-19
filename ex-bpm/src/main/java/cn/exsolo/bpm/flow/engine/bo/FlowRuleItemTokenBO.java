package cn.exsolo.bpm.flow.engine.bo;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author prestolive
 * @date 2021/10/24
 **/
public class FlowRuleItemTokenBO {

    private FlowRuleItemTokenType tokenType;

    private String token;

    private FlowRuleItemTokenBO[] subTokens;

    public FlowRuleItemTokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(FlowRuleItemTokenType tokenType) {
        this.tokenType = tokenType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public FlowRuleItemTokenBO[] getSubTokens() {
        return subTokens;
    }

    public void setSubTokens(FlowRuleItemTokenBO[] subTokens) {
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
