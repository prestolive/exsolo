package cn.exsolo.bpm.console.bo;

import cn.exsolo.bpm.console.po.FlowPO;
import cn.exsolo.bpm.console.po.FlowVersionPO;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/11/21
 **/
public class FlowInfoBO {

    private FlowPO flow;

    private List<FlowVersionPO> versionList;

    public FlowPO getFlow() {
        return flow;
    }

    public void setFlow(FlowPO flow) {
        this.flow = flow;
    }

    public List<FlowVersionPO> getVersionList() {
        return versionList;
    }

    public void setVersionList(List<FlowVersionPO> versionList) {
        this.versionList = versionList;
    }
}
