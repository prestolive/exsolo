package cn.exsolo.kit.render;

import java.util.Collection;
import java.util.Map;

/**
 * @author prestolive
 * @date 2021/3/6
 **/
public interface DataRender {

    /**
     *  渲染数据前置
     * @param keyValues
     */
    void preRender(Collection<Object> keyValues);

    /**
     * 获取行渲染的数据帧
     * @param keyValue
     * @param row
     * @return
     */
    Map<String,Object> getRenderFrame(Object keyValue, Map row);

}
