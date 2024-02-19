package cn.exsolo.bpm.flow.engine;

import cn.exsolo.bpm.console.po.FlowPO;
import cn.exsolo.bpm.flow.engine.bo.FlowConfigBO;
import cn.exsolo.bpm.flow.engine.bo.FlowNodeBO;
import cn.exsolo.bpm.flow.engine.bo.FlowNodeType;
import cn.exsolo.bpm.flow.engine.bo.FlowPathBO;
import cn.hutool.crypto.SecureUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2021/10/31
 **/
public class FlowEngineUtils {

    public static FlowConfigBO getInitFlow(FlowPO flowPO) {
        FlowConfigBO bo = new FlowConfigBO();
        bo.setCode(flowPO.getCode());
        bo.setLabel(flowPO.getLabel());
        bo.setVersion(1);

        FlowNodeBO start = new FlowNodeBO();
        start.setId("start");
        start.setLabel("开始");
        start.setType(FlowNodeType.start);
        start.setX(1300);
        start.setY(1200);
        FlowNodeBO end = new FlowNodeBO();
        end.setId("end");
        end.setLabel("结束");
        end.setType(FlowNodeType.end);
        end.setX(1500);
        end.setY(1200);
        bo.setNodes(new FlowNodeBO[]{start, end});
        FlowPathBO path = new FlowPathBO();
        path.setId("P01");
        path.setStartId("start");
        path.setEndId("end");
        path.setStartPort("RC");
        path.setEndPort("LC");
        bo.setPaths(new FlowPathBO[]{path});
        return bo;

    }

    public static String getFlowHash(FlowConfigBO bo) {
        List<String> strs = new ArrayList<>();
        strs.add(bo.getCode());
        if (bo.getNodes() != null) {
            String nodes = Arrays.stream(bo.getNodes()).sorted(new Comparator<FlowNodeBO>() {
                @Override
                public int compare(FlowNodeBO o1, FlowNodeBO o2) {
                    return o1.getId().compareTo(o2.getId());
                }
            }).map(node -> node.getId() + node.getType()).collect(Collectors.joining());
            strs.add(nodes);
        }
        if (bo.getPaths() != null) {
            String paths = Arrays.stream(bo.getPaths()).sorted(new Comparator<FlowPathBO>() {
                @Override
                public int compare(FlowPathBO o1, FlowPathBO o2) {
                    return (o1.getStartId() + o1.getEndId()).compareTo(o2.getStartId() + o2.getEndId());
                }
            }).map(path -> path.getId() + path.getStartId() + path.getEndId()).collect(Collectors.joining());
            strs.add(paths);
        }
        return SecureUtil.md5(StringUtils.join(strs, ","));

    }
}
