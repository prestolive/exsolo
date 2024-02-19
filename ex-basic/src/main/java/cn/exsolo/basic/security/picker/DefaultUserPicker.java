package cn.exsolo.basic.security.picker;

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
            cond.lk("userName", keyword);
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

    private String commonSql = "select id as value,userName as label,loginCode as sub from ex_user a where 1=1 ";

    @Override
    public ExPickerOptionBO getSingle(String id) {
        List<String> ids = new ArrayList<>();
        ids.add(id);
        List<ExPickerOptionBO> list = getList(ids);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

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
