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
     * 前置处理方法
     * @param pairList 数据
     * @param keyFields 关键字字段
     * @param methodParameter 原controller方法的methodParameter，如果有需要增加提取注解的，可以通过它实现
     */
    Map<String,JSONObject> getData(List<Pair<String,JSONObject>> pairList,
                                   String[] keyFields, MethodParameter methodParameter);

}
