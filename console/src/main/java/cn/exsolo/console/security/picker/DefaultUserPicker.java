package cn.exsolo.console.security.picker;

import cn.exsolo.batis.core.*;
import cn.exsolo.console.security.po.UserPO;
import cn.exsolo.kit.picker.IPicker;
import cn.exsolo.kit.picker.bo.ExPickerOptionBO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author prestolive
 * @date 2023/8/24
 **/
@Service
public class DefaultUserPicker implements IPicker {

    @Autowired
    private BaseDAO baseDAO;

    @Override
    public String getCode() {
        return "default_user_picker";
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
        sql.append("select id as value,userName as label,loginCode as sub from ex_user a where 1=1 ");
        CommonOrmUtils.generateConditionSql(sql,"a",cond,values);
        sql.append(" order by a.id desc");
        PageObject<ExPickerOptionBO> page = baseDAO.queryForPage(sql.toString(),values,ExPickerOptionBO.class,pagination);
        return page;
    }

    @Override
    public ExPickerOptionBO get(String id) {
        return null;
    }

    public ExPickerOptionBO[] get(String... id) {
        return null;
    }
}
