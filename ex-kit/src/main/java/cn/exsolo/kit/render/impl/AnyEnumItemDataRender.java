package cn.exsolo.kit.render.impl;

import cn.exsolo.kit.render.DataRender;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author prestolive
 * @date 2024/3/6
 **/
@Component
public class AnyEnumItemDataRender implements DataRender {

    @Override
    public void initRender(String keyField, MethodParameter methodParameter) {

    }

    @Override
    public void preRender(List<Pair<Object, Map>> pairList) {

    }

    @Override
    public Map<String, Object> getRenderFrame(Object keyValue, Map row) {
        if (keyValue != null) {
            if (keyValue instanceof Enum) {
                try {
                    Method getLabelMethod = keyValue.getClass().getMethod("getLabel");
                    String label = (String) getLabelMethod.invoke(keyValue);
                    if (StringUtils.isNotEmpty(label)) {
                        Map<String, Object> result = new HashMap<>();
                        result.put("label", label);
                        return result;
                    }
                    return null;
                } catch (NoSuchMethodException e) {
                    return null;
                } catch (InvocationTargetException e) {
                    return null;
                } catch (IllegalAccessException e) {
                    return null;
                }
            }
        }
        return null;
    }
}
