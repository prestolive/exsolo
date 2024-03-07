package cn.exsolo.springmvcext.in.out;

import cn.exsolo.kit.render.DataRender;
import cn.exsolo.kit.render.stereotype.DataRenderProvider;
import cn.exsolo.kit.render.stereotype.DataRenderProviders;
import cn.exsolo.springmvcext.SpringContext;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 1. 为所有的response套上标准的返回报文格式
 * 2. 实现数据渲染器
 *
 * @author prestolive
 */
@ControllerAdvice
public class CommResultControllerAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (body != null && body instanceof BaseResponse) {
            return body;
        }
        BaseResponse<?> baseResponse = new BaseResponse(0, null, null, body);
        List<DataRenderProvider> dataRenderProviderList = new ArrayList<>();
        DataRenderProvider dataRenderProviderOne = methodParameter.getMethodAnnotation(DataRenderProvider.class);
        if (dataRenderProviderOne != null) {
            dataRenderProviderList.add(dataRenderProviderOne);
        }
        DataRenderProviders dataRenderProviders = methodParameter.getMethodAnnotation(DataRenderProviders.class);
        if (dataRenderProviders != null) {
            for (DataRenderProvider row : dataRenderProviders.value()) {
                dataRenderProviderList.add(row);
            }
        }
        if (dataRenderProviderList.size() == 0) {
            return baseResponse;
        }
        //数据渲染器，先转成对象
        Map respMap = BeanUtil.beanToMap(baseResponse);
        for (DataRenderProvider dataRenderProvider : dataRenderProviderList) {
            //处理类
            Class processClz = dataRenderProvider.dataRenderClass();
            DataRender render = (DataRender) SpringContext.getContext().getBean(processClz);
            //查出数据行
            List<Map> targetRows = new ArrayList<>();
            //路径栈
            Stack<String> stack = new Stack<>();
            stack.add("data");
            stack.addAll(Arrays.stream(dataRenderProvider.path().split("\\.")).filter(str->StringUtils.isNotEmpty(str)).collect(Collectors.toList()));
            Collections.reverse(stack);
            fetchRows(stack, respMap, targetRows);
            //开始处理
            if (targetRows.size() > 0) {
                String keyField = dataRenderProvider.keyField();
                //初始化
                render.initRender(keyField, methodParameter);
                //提取key
                List<Pair<Object, Map>> pairList = new ArrayList<>();
                for (Map row : targetRows) {
                    Object keyValue = getKeyValue(row, keyField);
                    Pair pair = Pair.of(keyValue, row);
                    pairList.add(pair);
                }
                render.preRender(pairList);
                //渲染查询
                for (Pair<Object, Map> pair : pairList) {
                    Map<String, Object> rowFrame = render.getRenderFrame(pair.getLeft(), pair.getRight());
                    rowDataRender(pair.getRight(), rowFrame, dataRenderProvider);
                }
            }
        }
        return JSONObject.toJSON(respMap);
    }

    private void rowDataRender(Map row, Map<String, Object> renderFrame, DataRenderProvider dataRenderProvider) {
        if (renderFrame != null) {
            if (dataRenderProvider.wapperType() == DataRenderProvider.WapperType.alias) {
                String alias = dataRenderProvider.defineAlias();
                if (StringUtils.isEmpty(alias)) {
                    alias = "_" + dataRenderProvider.keyField();
                }
                row.put(alias, renderFrame);
            } else if (dataRenderProvider.wapperType() == DataRenderProvider.WapperType.flat) {
                for (String key : renderFrame.keySet()) {
                    row.put(key, renderFrame.get(key));
                }
            }
        }
    }


    private Object getKeyValue(Map row, String keyField) {
        Object obj = row.get(keyField);
        return obj;
    }

    /**
     * 找到目标行，目标行必须是对象或map，找到目标行后默认都转成map，保留属性的原始类型
     *
     * @param paths
     * @param targetObj
     * @param targetRows
     */
    private void fetchRows(Stack<String> paths, Map targetObj, List<Map> targetRows) {
        String key = paths.pop();
        boolean end = paths.size() == 0;
        Object obj = targetObj.get(key);
        if (obj == null) {
            return;
        }
        if (obj.getClass().isArray()) {
           //FIXME
        } else if (obj instanceof Collection) {
            Iterator it = ((Collection) obj).iterator();
                List list = new ArrayList();
                while (it.hasNext()) {
                    Map rowMap = BeanUtil.beanToMap(it.next());
                    list.add(rowMap);
                    if(end){
                        targetRows.add(rowMap);
                    }else{
                        fetchRows(paths, rowMap, targetRows);
                    }
                }
                //替换原对象
                targetObj.put(key,list);
        } else {
            Map rowMap = BeanUtil.beanToMap(obj);
            if (end) {
                targetRows.add(rowMap);
            } else {
                fetchRows(paths, rowMap, targetRows);
            }
            //替换原对象
            targetObj.put(key,rowMap);
        }
    }


}