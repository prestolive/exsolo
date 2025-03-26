package cn.exsolo.basic.picker;

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
 * @date 2021/8/24
 **/
@Service
public class DefaultUserPicker implements IPicker {

    @Autowired
    private BaseDAO baseDAO;

    @Override
    public String getCode() {
        return "DEFAULT_USER_PICKER";
    }

    @Override
    public PageObject<ExPickerOptionBO> find(Pagination pagination, String keyword, Condition customCond) {
        Condition cond = new Condition();
        if(StringUtils.isNotEmpty(keyword)) {
            cond.or(new Condition().lk("userName", keyword),new Condition().lk("loginCode", keyword));
        }
        if(customCond !=null){
            cond.and(customCond);
        }
        cond.orderBy("a.id",Condition.DESC);
        Map<String,Object> values = new HashMap<>();
        return baseDAO.queryForPage(commonSql,cond,values,ExPickerOptionBO.class,pagination);
    }

    private String commonSql = "select id as value,userName as label,loginCode as sub from ex_user a";


    @Override
    public List<ExPickerOptionBO> getList(List<String> ids) {
        Condition cond = new Condition();
        cond.in("id",ids);
        StringBuilder sql = new StringBuilder();
        Map<String,Object> values=  new HashMap<>();
        sql.append(commonSql);
        CommonOrmUtils.generateConditionSql(sql,"a",cond,values);
        return baseDAO.queryForList(sql.toString(),values,ExPickerOptionBO.class);
    }

    public ExPickerOptionBO[] get(String... id) {
        return null;
    }
}
