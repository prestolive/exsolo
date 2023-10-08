package cn.exsolo.kit.render;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.MethodParameter;

import java.util.List;
import java.util.Map;

/**
 * 为数据渲染器提供数据
 * Created by prestolive on 2017/7/28.
 * @Author prestolive
 */
public interface DataRenderValueMapper {

    /**
     *
     * 根据key 取得要渲染的数据
     * @param pairList 数据
     * @param keyFields 关键字字段
     * @param methodParameter 原controller方法的methodParameter，如果有需要增加提取注解的，可以通过它实现
     * @return keyValue,根据keyValue查到的对象
     */
    Map<String,JSONObject> getDataByKeys(List<Pair<String,JSONObject>> pairList,
                                         String[] keyFields, MethodParameter methodParameter);


    /**
     * 特殊的渲染处理
     * @param keyValue
     * @param row
     */
    void customRender(String keyValue,JSONObject row);
}
