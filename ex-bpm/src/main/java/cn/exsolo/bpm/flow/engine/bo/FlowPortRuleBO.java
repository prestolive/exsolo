package cn.exsolo.bpm.flow.engine.bo;

/**
 * @author prestolive
 * @date 2025/1/10
 **/
public class FlowPortRuleBO {

    private String pathId;

    private String targetNodeId;

    private FlowPortFilterBO filter;

    public String getPathId() {
        return pathId;
    }

    public void setPathId(String pathId) {
        this.pathId = pathId;
    }

    public String getTargetNodeId() {
        return targetNodeId;
    }

    public void setTargetNodeId(String targetNodeId) {
        this.targetNodeId = targetNodeId;
    }

    public FlowPortFilterBO getFilter() {
        return filter;
    }

    public void setFilter(FlowPortFilterBO filter) {
        this.filter = filter;
    }
}
