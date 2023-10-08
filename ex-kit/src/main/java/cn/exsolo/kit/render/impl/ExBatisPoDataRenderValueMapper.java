package cn.exsolo.kit.render.impl;

import cn.exsolo.batis.core.AbstractSanBatisPO;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.comm.ex.ExDevException;
import cn.exsolo.kit.render.DataRenderValueMapper;
import cn.exsolo.kit.utils.ExPageWork;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2021/9/21
 **/
public abstract class ExBatisPoDataRenderValueMapper implements DataRenderValueMapper {

    public  abstract Class<? extends AbstractSanBatisPO> getPoClass();

    public abstract String getMappedKeyField();

    public abstract Condition createCondition();

    public abstract String[] getValueFields();

    @Autowired
    private BaseDAO baseDAO;

    @Override
    public Map<String, JSONObject> getDataByKeys(List<Pair<String, JSONObject>> pairList, String[] keyFields, MethodParameter methodParameter) {
        if (keyFields.length > 1) {
            throw new ExDevException("ExBatisPoDataRender不支持1个以上的映射关键字");
        }
        List<String> keyValues = pairList.stream().map(pair->pair.getLeft()).distinct().collect(Collectors.toList());
        //分页执行查出数据
        Map<String, JSONObject> map = new HashMap<>(10);
        ExPageWork.IPageExecute pageExecute = new ExPageWork.IPageExecute<String>() {
            @Override
            public void execute(List<String> list) {
                Condition cond = createCondition();
                cond.in(getMappedKeyField(),list);
                List queryList = baseDAO.queryBeanByCond(getPoClass(),cond);
                if(queryList.size()>0){
                    for(Object row:queryList){
                        JSONObject obj = (JSONObject) JSONObject.toJSON(row);
                        String key = obj.getString(getMappedKeyField());
                        JSONObject copy = new JSONObject();
                        for(String field:getValueFields()){
                            copy.put(field,obj.getString(field));
                        }
                        map.put(key,copy);
                    }
                }
            }
        };
        new ExPageWork().pageExecute(keyValues,500,pageExecute);
        return map;
    }


}
