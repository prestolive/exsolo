package cn.exsolo.bpm.flow.engine.bo;

import java.util.Arrays;

/**
 * @author prestolive
 * @date 2023/10/24
 **/
public class FlowConfigBO {

    private String code;

    private String label;

    private FlowNodeBO[] nodes;

    private FlowPathBO[] paths;

    private FlowContextFieldBO[] fields;

    private Integer version;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public FlowNodeBO[] getNodes() {
        return nodes;
    }

    public void setNodes(FlowNodeBO[] nodes) {
        this.nodes = nodes;
    }

    public FlowPathBO[] getPaths() {
        return paths;
    }

    public void setPaths(FlowPathBO[] paths) {
        this.paths = paths;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public FlowContextFieldBO[] getFields() {
        return fields;
    }

    public void setFields(FlowContextFieldBO[] fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "FlowConfigBO{" +
                "code='" + code + '\'' +
                ", label='" + label + '\'' +
                ", nodes=" + Arrays.toString(nodes) +
                ", paths=" + Arrays.toString(paths) +
                ", version=" + version +
                '}';
    }
}
