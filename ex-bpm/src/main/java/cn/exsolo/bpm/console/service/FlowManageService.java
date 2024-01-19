package cn.exsolo.bpm.console.service;

import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.bpm.console.ExBpmConsoleErrorCodeEnum;
import cn.exsolo.bpm.console.ExBpmFlowStatusEnum;
import cn.exsolo.bpm.console.po.FlowContentPO;
import cn.exsolo.bpm.console.po.FlowVersionPO;
import cn.exsolo.bpm.console.po.FlowPO;
import cn.exsolo.bpm.flow.engine.FlowEngineUtils;
import cn.exsolo.bpm.flow.engine.bo.FlowConfigBO;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.kit.utils.ExAssert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/10/25
 **/
@Service
public class FlowManageService {

    @Autowired
    private BaseDAO baseDAO;

    public void updateOrSaveFlow(FlowPO flow) {
        ExAssert.isNull(flow, flow.getCode());
        boolean isNew = StringUtils.isEmpty(flow.getId());
        //检查code是否已经存在
        Condition checkCond = new Condition();
        checkCond.eq("code", flow.getCode());
        if (StringUtils.isNotEmpty(flow.getId())) {
            checkCond.ne("id", flow.getId());
        }
        if (baseDAO.existsByCond(FlowPO.class, checkCond)) {
            throw new ExDeclaredException(ExBpmConsoleErrorCodeEnum.CODE_ALREADY_EXISTS, flow.getCode());
        }
        if (isNew) {
            addNewVersion(flow);
        }
        //保存
        baseDAO.insertOrUpdateValueObject(flow);
    }


    public PageObject<FlowPO> page(Condition fCond, Pagination pagination) {
        Condition cond = new Condition();
        if (fCond != null) {
            cond.and(fCond);
        }
        cond.orderBy("createTs", Condition.DESC);
        return baseDAO.queryBeanPageByCond(FlowPO.class, cond, pagination);
    }

    public FlowPO get(String id) {
        ExAssert.isNull(id);
        return baseDAO.queryBeanByID(FlowPO.class, id);
    }

    public FlowPO getByCode(String code) {
        ExAssert.isNull(code);
        return baseDAO.queryOneBeanByCond(FlowPO.class, new Condition().eq("code", code));
    }

    public List<FlowVersionPO> getVersionList(String code) {
        return baseDAO.queryBeanByCond(FlowVersionPO.class, new Condition().eq("code", code).orderBy("version"));
    }

    public void delete(String id) {
        ExAssert.isNull(id);
        //FIXME 实例检查
        FlowPO flowPO = get(id);
        baseDAO.deleteByCond(FlowVersionPO.class, new Condition().eq("code", flowPO.getCode()));
        baseDAO.deleteByID(FlowPO.class, id);
    }

    /**
     * 流程内容、版本部分
     **/

    public void activeVersion(String code, Integer version) {
        List<FlowVersionPO> existVersions = getExistsVersions(code);
        for(FlowVersionPO versionPO:existVersions){
            if(versionPO.getVersion()==version){
                if(versionPO.getStatus()==ExBpmFlowStatusEnum.editing){
                    versionPO.setStatus(ExBpmFlowStatusEnum.activated);
                    baseDAO.insertOrUpdateValueObject(versionPO);
                }
            }else{
                if(versionPO.getStatus()==ExBpmFlowStatusEnum.activated){
                    versionPO.setStatus(ExBpmFlowStatusEnum.closed);
                    baseDAO.insertOrUpdateValueObject(versionPO);
                }
            }
        }
    }

    public FlowConfigBO addNewVersion(FlowPO flowPO) {
        ExAssert.isNull(flowPO);
        //
        List<FlowVersionPO> existVersions = getExistsVersions(flowPO.getCode());
        //判断如果有编辑中的版本就禁止再新增
        if (existVersions.stream().anyMatch(row -> ExBpmFlowStatusEnum.editing == row.getStatus())) {
            throw new ExDeclaredException(ExBpmConsoleErrorCodeEnum.EDITING_VERSION_REPEAT, flowPO.getCode());
        }
        Integer version = existVersions.size() > 0 ? (existVersions.get(0).getVersion() + 1) : 1;
        //
        FlowConfigBO config = FlowEngineUtils.getInitFlow(flowPO);
        config.setVersion(version);
        saveVersion(config);
        return config;
    }

    public void saveVersion(FlowConfigBO config) {
        FlowVersionPO version = getVersion(config.getCode(), config.getVersion());
        if (version == null) {
            version = new FlowVersionPO();
            version.setCode(config.getCode());
            version.setVersion(config.getVersion());
            version.setStatus(ExBpmFlowStatusEnum.editing);
            String hash = FlowEngineUtils.getFlowHash(config);
            version.setHash(hash);
        }
        saveContent(config);
        //修改记录
        baseDAO.insertOrUpdateValueObject(version);
    }


    public FlowConfigBO getContent(String code, Integer version) {
        String id = String.format("%s_%d", code, version);
        FlowContentPO contentPO = baseDAO.queryBeanByID(FlowContentPO.class, id);
        FlowConfigBO configBO = JSONObject.parseObject(contentPO.getContent(), FlowConfigBO.class);
        return configBO;
    }

    private List<FlowVersionPO> getExistsVersions(String code) {
        return baseDAO.queryBeanByCond(FlowVersionPO.class, new Condition().eq("code", code).orderBy("version", Condition.DESC));
    }

    private void saveContent(FlowConfigBO config) {
        String id = String.format("%s_%d", config.getCode(), config.getVersion());
        baseDAO.deleteByCond(FlowContentPO.class, new Condition().eq("id", id));
        FlowContentPO contentPO = new FlowContentPO();
        contentPO.setId(id);
        contentPO.setContent(JSONObject.toJSONString(config));
        baseDAO.insertOrUpdateValueObject(contentPO);
    }

    private FlowVersionPO getVersion(String code, Integer version) {
        Condition cond = new Condition();
        cond.eq("code", code);
        cond.eq("version", version);
        FlowVersionPO content = baseDAO.queryOneBeanByCond(FlowVersionPO.class, cond);
        return content;
    }

}
