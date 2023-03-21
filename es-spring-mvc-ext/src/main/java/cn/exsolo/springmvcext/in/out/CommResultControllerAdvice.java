package cn.exsolo.springmvcext.in.out;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class CommResultControllerAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        // aClass能否强制转化为MappingJacksonValue
//        return MappingJacksonValue.class.isAssignableFrom(aClass);
        return true;
    }
    
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        MappingJacksonValue container = getOrCreateContainer(body);
        beforeBodyWriteInternal(container, serverHttpResponse);
        return container;
    }

    /**
     * 把response返回的body对象封装到MappingJacksonValue对象中
     * 以支持JSONP的跨域请求访问
     * @param body response返回的body对象
     * @return MappingJacksonValue格式对象
     */
    private MappingJacksonValue getOrCreateContainer(Object body){
        return body instanceof  MappingJacksonValue ?
                (MappingJacksonValue) body : new MappingJacksonValue(body);
    }
	
     /**
     * 把MappingJacksonValue封装的body对象包装成BaseResponse对象再封装进去
     * @param container 封装的MappingJacksonValue对象
     */
    private void beforeBodyWriteInternal(MappingJacksonValue container, ServerHttpResponse serverHttpResponse) {
        Object returnBody = container.getValue();
        BaseResponse<?> baseResponse = BaseResponse.ok(returnBody);
        container.setValue(baseResponse);
//        serverHttpResponse.setStatusCode(HttpStatus.resolve(baseResponse.getCode()));
    }
}