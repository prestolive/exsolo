package cn.exsolo.bpm.pub.picker;

import cn.exsolo.batis.core.*;
import cn.exsolo.kit.picker.IPicker;
import cn.exsolo.kit.picker.bo.ExPickerOptionBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prestolive
 * @date 2021/9/16
 **/
@Service
public class JobPicker  implements IPicker {

    @Autowired
    private BaseDAO baseDAO;

    @Override
    public String getCode() {
        return "BPM_ORG_JOB_PICKER";
    }

    @Override
    public PageObject<ExPickerOptionBO> find(Pagination pagination, String keyword, Condition customCond) {
        Condition cond = new Condition();
        if(StringUtils.isNotEmpty(keyword)) {
            cond.or(new Condition().lk("name", keyword),new Condition().lk("code",keyword));
        }
        if(customCond !=null){
            cond.and(customCond);
        }
        StringBuilder sql = new StringBuilder();
        Map<String,Object> values=  new HashMap<>();
        sql.append(commonSql);
        CommonOrmUtils.generateConditionSql(sql,"a",cond,values);
        sql.append(" order by a.id desc");
        PageObject<ExPickerOptionBO> page = baseDAO.queryForPage(sql.toString(),values,ExPickerOptionBO.class,pagination);
        return page;
    }

    private String commonSql = "select id as value,name as label,grade as sub from ex_bpm_org_job a where 1=1 ";


    @Override
    public List<ExPickerOptionBO> getList(List<String> ids) {
        Condition cond = new Condition();
        if(ids.size()==1){
            cond.eq("id",ids.get(0));
        }else{
            cond.in("id",ids);
        }
        StringBuilder sql = new StringBuilder();
        Map<String,Object> values=  new HashMap<>();
        sql.append(commonSql);
        CommonOrmUtils.generateConditionSql(sql,"a",cond,values);
        return baseDAO.queryForList(sql.toString(),values,ExPickerOptionBO.class);
    }
}
