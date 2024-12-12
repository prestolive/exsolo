package cn.exsolo.basic.picker;

import cn.exsolo.basic.org.po.OrgNodePO;
import cn.exsolo.basic.render.OrgInfoDataRender;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.kit.picker.bo.ExPickerOptionBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2024/11/13
 **/
@Component
public class DefaultOrgPicker extends AbstractTreeNodePicker<OrgNodePO> {

    @Autowired
    private OrgInfoDataRender orgInfoDataRender;

    @Autowired
    private BaseDAO baseDAO;

    @Override
    public String getCode() {
        return "DEFAULT_ORG_PICKER";
    }

    @Override
    protected ExPickerOptionBO value2option(OrgNodePO origin) {
        ExPickerOptionBO bo = new ExPickerOptionBO();
        bo.setValue(origin.getId());
        bo.setLabel(origin.getOrgName());
        bo.setSub(origin.getOrgCode());
        Map<String, Object> frameData = orgInfoDataRender.getRenderFrame(origin.getId(), null);
        if(frameData!=null){
            Object fullName = frameData.get("fullName");
            bo.setEcho1(fullName==null?null:fullName.toString());
        }
        bo.setChildCounts(origin.getChildCounts());
        return bo;
    }

    @Override
    protected PageObject<OrgNodePO> query(Pagination pagination, String keyword, Condition fCond) {
        Condition cond = new Condition();
        if(StringUtils.isNotEmpty(keyword)){
            cond.and(new Condition().or(new Condition().lk("orgName",keyword),new Condition().lk("orgCode",keyword)));
        }
        if(fCond!=null){
            cond.and(fCond);
        }
        PageObject<OrgNodePO> page = baseDAO.queryBeanPageByCond(OrgNodePO.class,cond,pagination);
        //查询后就初始化渲染器，这里只查询，会将结果缓存，同一个线程可访问。
        Set keyValues = page.getValues().stream().map(row -> row.getId()).collect(Collectors.toSet());
        orgInfoDataRender.preRender(keyValues);
        return page;
    }
}