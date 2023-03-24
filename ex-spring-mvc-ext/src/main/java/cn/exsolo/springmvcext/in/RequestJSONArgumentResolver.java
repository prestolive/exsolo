package cn.exsolo.springmvcext.in;

import cn.exsolo.springmvcext.stereotype.RequestJSON;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.multipart.support.MultipartResolutionDelegate;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author prestolive
 * @date 2023/1/18
 **/
public class RequestJSONArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestJSON.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String annoValue = parameter.getParameterAnnotation(RequestJSON.class).value();
        String fieldName = StringUtils.isEmpty(annoValue) ? parameter.getParameterName() : annoValue;
        Object inputContent = resolveName(fieldName, parameter, webRequest);
        if(ObjectUtils.isEmpty(inputContent)){
            return null;
        }
        Class clz = parameter.getParameterType();
        if (isPrimitive(clz)) {
            WebDataBinder binder = binderFactory.createBinder(webRequest, (Object) null, fieldName);
            Object obj = null;
            try {
                obj = binder.convertIfNecessary(inputContent, parameter.getParameterType(), parameter);
            } catch (ConversionNotSupportedException var11) {
                throw new MethodArgumentConversionNotSupportedException(fieldName, var11.getRequiredType(), fieldName, parameter, var11.getCause());
            } catch (TypeMismatchException var12) {
                throw new MethodArgumentTypeMismatchException(fieldName, var12.getRequiredType(), fieldName, parameter, var12.getCause());
            }
            return obj;
        } else {
            Object obj = JSON.parseObject(inputContent.toString(),clz);
            return obj;
        }
    }


    protected Object resolveName(String name, MethodParameter parameter, NativeWebRequest request) throws Exception {
        HttpServletRequest servletRequest = (HttpServletRequest) request.getNativeRequest(HttpServletRequest.class);
        Object arg;
        if (servletRequest != null) {
            arg = MultipartResolutionDelegate.resolveMultipartArgument(name, parameter, servletRequest);
            if (arg != MultipartResolutionDelegate.UNRESOLVABLE) {
                return arg;
            }
        }
        arg = null;
        MultipartRequest multipartRequest = (MultipartRequest) request.getNativeRequest(MultipartRequest.class);
        if (multipartRequest != null) {
            List<MultipartFile> files = multipartRequest.getFiles(name);
            if (!files.isEmpty()) {
                arg = files.size() == 1 ? files.get(0) : files;
            }
        }
        if (arg == null) {
            String[] paramValues = request.getParameterValues(name);
            if (paramValues != null) {
                arg = paramValues.length == 1 ? paramValues[0] : paramValues;
            }
        }
        return arg;
    }


    /**
     * 这个和fastJson是一致的，所以BigDecimal都算是基本类型，其他才是对象
     *
     * @param clazz
     * @return
     */
    private static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive()
                || clazz == Boolean.class
                || clazz == Character.class
                || clazz == Byte.class
                || clazz == Short.class
                || clazz == Integer.class
                || clazz == Long.class
                || clazz == Float.class
                || clazz == Double.class
                || clazz == BigInteger.class
                || clazz == BigDecimal.class
                || clazz == String.class
                || clazz == java.util.Date.class
                || clazz == java.sql.Date.class
                || clazz == java.sql.Time.class
                || clazz == java.sql.Timestamp.class
                || clazz.isEnum();
    }
}
