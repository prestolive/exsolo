package cn.exsolo.springmvcext.in.out;

import cn.exsolo.kit.render.DataRenderValueMapper;
import cn.exsolo.kit.render.stereotype.DataRenderProvider;
import cn.exsolo.kit.render.stereotype.DataRenderProviders;
import cn.exsolo.springmvcext.SpringContext;
import com.alibaba.fastjson.JSON;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
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
    public Object beforeBodyWrite(Object body,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {
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
        JSONObject obj = (JSONObject) JSONObject.toJSON(baseResponse);
        for (DataRenderProvider dataRenderProvider : dataRenderProviderList) {
            //处理类
            Class processClz = dataRenderProvider.dataRenderClass();
            DataRenderValueMapper render = (DataRenderValueMapper) SpringContext.getContext().getBean(processClz);
            //查出数据行
            List<JSONObject> requireRows = new ArrayList<>();
            String path = dataRenderProvider.path();
            String[] strs = path.split("\\.");
            Stack<String> stack = new Stack<>();
            for (int i = strs.length - 1; i >= 0; i--) {
                stack.push(strs[i]);
            }
            stack.push("data");
            loopPickRow(stack, obj, requireRows);
            //开始处理
            if (requireRows.size() > 0) {
                String[] keyFields = dataRenderProvider.keyFields();
                List<Pair<String, JSONObject>> pairList = new ArrayList<>();
                for (JSONObject row : requireRows) {
                    String keyValue = getKeyValue(row, keyFields);
                    Pair pair = Pair.of(keyValue, row);
                    pairList.add(pair);
                }
                //渲染查询
                Map<String, JSONObject> valueMapper = render.getData(pairList, keyFields, methodParameter);
                for(Pair<String, JSONObject> pair:pairList){
                    render(pair,valueMapper,dataRenderProvider);
                }
            }
        }
        return obj;
    }

    private void render(Pair<String, JSONObject> targetPair,Map<String, JSONObject> valueMapper,DataRenderProvider dataRenderProvider){
        String keyValue = targetPair.getLeft();
        JSONObject target = targetPair.getRight();
        JSONObject valueObj = valueMapper.get(keyValue);
        if(valueObj==null){
            return;
        }
        if(dataRenderProvider.wapperType()==DataRenderProvider.WapperType.alias){
            String alias = dataRenderProvider.defineAlias();
            if(StringUtils.isEmpty(alias)){
                alias = "_"+StringUtils.join(dataRenderProvider.keyFields());
            }
            target.put(alias,valueObj.clone());
        }else if(dataRenderProvider.wapperType()==DataRenderProvider.WapperType.flat){
            for(String key:valueObj.keySet()){
                target.put(key,valueObj.get(key));
            }
        }
    }

    private String getKeyValue(JSONObject row, String[] keyFields) {
        String str = "";
        for (String keyField : keyFields) {
            String val = row.getString(keyField);
            str += val;
        }
        return str;
    }


    private void loopPickRow(Stack<String> paths, JSONObject targetObject, List<JSONObject> requireRows) {
        String key = paths.pop();
        boolean end = paths.size() == 0;
        Object obj = targetObject.get(key);
        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;
            if (end) {
                requireRows.add(jsonObject);
            } else {
                loopPickRow(paths, jsonObject, requireRows);
            }
        } else if (obj instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) obj;
            if (end) {
                requireRows.addAll(jsonArray.stream().map(item -> (JSONObject) item).collect(Collectors.toList()));
            } else {
                jsonArray.forEach(item -> {
                    JSONObject jsonObject = (JSONObject) item;
                    loopPickRow(paths, jsonObject, requireRows);
                });
            }
        }
        paths.add(key);
    }

}