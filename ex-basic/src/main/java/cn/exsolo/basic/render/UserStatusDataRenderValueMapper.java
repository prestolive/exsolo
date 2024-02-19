package cn.exsolo.basic.render;

import cn.exsolo.kit.item.ItemCommStatusEnum;
import cn.exsolo.kit.render.DataRenderValueMapper;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prestolive
 * @date 2021/9/20
 **/
@Component
public class UserStatusDataRenderValueMapper implements DataRenderValueMapper {

    /**
     * FIXME 增加多语言翻译功能
     * @param pairList 数据
     * @param keyFields 关键字字段
     * @param methodParameter 原controller方法的methodParameter，如果有需要增加提取注解的，可以通过它实现
     * @return
     */
    @Override
    public Map<String, JSONObject> getDataByKeys(List<Pair<String, JSONObject>> pairList, String[] keyFields, MethodParameter methodParameter) {
        Map<String,JSONObject> valueMapper= new HashMap<>();
        for(ItemCommStatusEnum item:ItemCommStatusEnum.values()){
            JSONObject target = new JSONObject();
            target.put("label",item.getLabel());
            valueMapper.put(item.name(),target);
        }
        return valueMapper;
    }

    @Override
    public void customRender(String keyValue, JSONObject row) {

    }
}
