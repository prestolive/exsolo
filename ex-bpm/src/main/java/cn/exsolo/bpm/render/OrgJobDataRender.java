package cn.exsolo.bpm.render;

import cn.exsolo.kit.render.impl.SqlCacheDataRender;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

/**
 * @author prestolive
 * @date 2021/3/6
 **/
@Component
public class OrgJobDataRender extends SqlCacheDataRender {

    @Override
    public String getSql() {
        return "select id as keyValue,name,code,grade from ex_bpm_org_job";
    }
}
