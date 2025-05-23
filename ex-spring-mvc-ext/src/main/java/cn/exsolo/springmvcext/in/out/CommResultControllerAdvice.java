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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 1. 为所有的response套上标准的返回报文格式
 * 2. 实现数据渲染器
 *
 * path写法
 * 1、常规：order.goods
 * 2、迭代.order.list(loop->children)
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
        if (body instanceof BaseResponse) {
            return body;
        }
        BaseResponse<?> baseResponse = new BaseResponse<>(0, null, null, body);
        List<DataRenderProvider> dataRenderProviderList = new ArrayList<>();
        DataRenderProvider dataRenderProviderOne = methodParameter.getMethodAnnotation(DataRenderProvider.class);
        if (dataRenderProviderOne != null) {
            dataRenderProviderList.add(dataRenderProviderOne);
        }
        DataRenderProviders dataRenderProviders = methodParameter.getMethodAnnotation(DataRenderProviders.class);
        if (dataRenderProviders != null) {
            dataRenderProviderList.addAll(Arrays.asList(dataRenderProviders.value()));
        }
        if (dataRenderProviderList.isEmpty()) {
            return baseResponse;
        }
        //数据渲染器，先转成对象
        Map respMap = RenderUtils.data2map(baseResponse);
        for (DataRenderProvider dataRenderProvider : dataRenderProviderList) {
            //处理类
            Class processClz = dataRenderProvider.dataRenderClass();
            DataRender render = (DataRender) SpringContext.getContext().getBean(processClz);
            //渲染
            RenderUtils.render(respMap,dataRenderProvider.path(),dataRenderProvider.keyField(),render,dataRenderProvider.defineAlias(),dataRenderProvider.wapperType().name());
        }
        return JSONObject.toJSON(respMap);
    }

}