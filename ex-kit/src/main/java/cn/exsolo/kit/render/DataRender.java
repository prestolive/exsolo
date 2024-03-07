package cn.exsolo.kit.render;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.MethodParameter;

import java.util.List;
import java.util.Map;

/**
 * @author prestolive
 * @date 2024/3/6
 **/
public interface DataRender {

    /**
     * 渲染初始化
     * @param keyField 关键字
     * @param methodParameter controller方法的参数，可以从这里获取其它注解
     */
    void initRender(String keyField, MethodParameter methodParameter);

    /**
     * 渲染数据前置
     * @param pairList  左边是根据keyFields获取的keyValue组合，keyFields可以多个
     */
    void preRender(List<Pair<Object, Map>> pairList);

    /**
     * 获取行渲染的数据帧
     * @param keyValue
     * @param row
     * @return
     */
    Map<String,Object> getRenderFrame(Object keyValue, Map row);

}
