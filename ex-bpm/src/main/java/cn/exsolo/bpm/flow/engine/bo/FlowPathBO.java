package cn.exsolo.bpm.flow.engine.bo;

/**
 * @author prestolive
 * @date 2021/10/24
 **/
public class FlowPathBO {

    private String id;

    private String label;

    private String startId;

    private String endId;

    private String startPort;

    private String endPort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public String getStartPort() {
        return startPort;
    }

    public void setStartPort(String startPort) {
        this.startPort = startPort;
    }

    public String getEndPort() {
        return endPort;
    }

    public void setEndPort(String endPort) {
        this.endPort = endPort;
    }

    @Override
    public String toString() {
        return "FlowPathBO{" +
                "id='" + id + '\'' +
                ", label='" + label + '\'' +
                ", startId='" + startId + '\'' +
                ", endId='" + endId + '\'' +
                ", startPort='" + startPort + '\'' +
                ", endPort='" + endPort + '\'' +
                '}';
    }
}
