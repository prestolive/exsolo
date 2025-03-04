package cn.exsolo.bpm.flow.engine.bo;

/**
 * 节点的配置
 * @author prestolive
 * @date 2024/12/5
 **/
public class FlowNodeConfigBO {

    private String nodeId;

    /**
     * 是否退回节点
     */
    private Boolean isBackOnLine;

    private FlowOperatorRuleBO[] operatorRules;

    private FlowPortRuleBO[] portRules;

    public FlowPortRuleBO[] getPortRules() {
        return portRules;
    }

    public void setPortRules(FlowPortRuleBO[] portRules) {
        this.portRules = portRules;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public Boolean getBackOnLine() {
        return isBackOnLine;
    }

    public void setBackOnLine(Boolean backOnLine) {
        isBackOnLine = backOnLine;
    }

    public FlowOperatorRuleBO[] getOperatorRules() {
        return operatorRules;
    }

    public void setOperatorRules(FlowOperatorRuleBO[] operatorRules) {
        this.operatorRules = operatorRules;
    }
}
