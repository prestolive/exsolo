package cn.exsolo.bpm.flow.engine.bo;

import cn.exsolo.bpm.console.ExBpmRuleOptionOrgEnum;
import cn.exsolo.bpm.console.ExBpmRuleOptionUserEnum;

/**
 * 节点操作人规则
 * @author prestolive
 * @date 2024/12/4
 **/
public class FlowOperatorRuleBO {

    private String targetType;

    private ExBpmRuleOptionUserEnum targetFrom;

    private String targetValue;

    private String targetField;

    private String searchJobValue;

    private ExBpmRuleOptionOrgEnum searchOrgFrom;

    private String searchOrgValue;

    private String searchOrgField;

    public String getTargetField() {
        return targetField;
    }

    public void setTargetField(String targetField) {
        this.targetField = targetField;
    }

    public String getSearchOrgField() {
        return searchOrgField;
    }

    public void setSearchOrgField(String searchOrgField) {
        this.searchOrgField = searchOrgField;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public ExBpmRuleOptionUserEnum getTargetFrom() {
        return targetFrom;
    }

    public void setTargetFrom(ExBpmRuleOptionUserEnum targetFrom) {
        this.targetFrom = targetFrom;
    }

    public String getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(String targetValue) {
        this.targetValue = targetValue;
    }

    public String getSearchJobValue() {
        return searchJobValue;
    }

    public void setSearchJobValue(String searchJobValue) {
        this.searchJobValue = searchJobValue;
    }

    public ExBpmRuleOptionOrgEnum getSearchOrgFrom() {
        return searchOrgFrom;
    }

    public void setSearchOrgFrom(ExBpmRuleOptionOrgEnum searchOrgFrom) {
        this.searchOrgFrom = searchOrgFrom;
    }

    public String getSearchOrgValue() {
        return searchOrgValue;
    }

    public void setSearchOrgValue(String searchOrgValue) {
        this.searchOrgValue = searchOrgValue;
    }
}
