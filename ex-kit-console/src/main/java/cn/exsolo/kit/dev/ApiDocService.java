package cn.exsolo.kit.dev;

import cn.exsolo.batis.core.stereotype.Column;
import cn.exsolo.comm.utils.EsAnnotationUtil;
import cn.exsolo.kit.console.api.ApiDocController;
import cn.exsolo.kit.dev.bo.ApiDocBO;
import cn.exsolo.kit.dev.bo.ApiDocClzBO;
import cn.exsolo.kit.dev.bo.ApiDocTypeBO;
import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Scan the documents generated by Spring mvc for urls, request parameters, and return parameters
 * to generate front-end typescript code
 *
 * @author prestolive
 * @date 2023/3/24
 **/
@Service
public class ApiDocService {

    @Autowired
    private ApplicationContext applicationContext;

    private static DefaultParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();

    public List<ApiDocClzBO> getAllController() {
        List<Class<?>> list = EsAnnotationUtil.getAnnotationFromContext(applicationContext, RequestMapping.class);
        return list.stream().map(row -> {
            ApiDocClzBO bo = new ApiDocClzBO();
            bo.setModule(getModuleFromClz(row.getName()));
            bo.setClz(row.getName());
            return bo;
        }).collect(Collectors.toList());
    }

    public List<ApiDocBO> processClz(Class clz) {
        RequestMapping clzAnna = (RequestMapping) clz.getAnnotation(RequestMapping.class);
        String urlPath = clzAnna.value()[0];
        Method[] methods = ReflectUtil.getMethods(clz);
        List<ApiDocBO> list = new ArrayList<>();
        for (Method method : methods) {
            RequestMapping methodAnna = method.getAnnotation(RequestMapping.class);
            if (methodAnna == null) {
                continue;
            }
            ApiDocBO doc = new ApiDocBO();
            String methodPath = getHttpPath(methodAnna);
            doc.setPath(urlPath + methodPath);
            doc.setMethod(getHttpMethod(methodAnna));
            doc.setName(pathToName(doc.getPath()));
            doc.setNameLower(firstCharLower(doc.getName()));
            //解析返回参数
            doc.setReturnType(getDocTypeBO( doc.getName(),null, method.getGenericReturnType()));
            //解析参数
            String[] paramNames = discoverer.getParameterNames(method);
            Type[] paramTypes = method.getGenericParameterTypes();
            if (paramTypes.length > 0) {
                List<ApiDocTypeBO> paramTypeList = new ArrayList<>();
                for (int i=0;i<paramTypes.length;i++) {
                    Type type = paramTypes[i];
                    if("javax.servlet.http.HttpServletRequest".equals(type.getTypeName())||
                            "javax.servlet.http.HttpServletResponse".equals(type.getTypeName())||
                            "javax.servlet.ServletRequest".equals(type.getTypeName())||
                            "javax.servlet.ServletResponse".equals(type.getTypeName())){
                        continue;
                    }
                    String paramName = paramNames[i];
                    paramTypeList.add(getDocTypeBO(doc.getName(),paramName, type));
                }
                doc.setParamTypes(paramTypeList);
            }
            list.add(doc);
        }
        return list;
    }

    private String getHttpMethod(RequestMapping methodAnna) {
        RequestMethod[] httpMethods = methodAnna.method();
        if (httpMethods == null || httpMethods.length == 0) {
            return "post";
        }
        for (RequestMethod m : httpMethods) {
            if (m.name().equals(RequestMethod.POST.name())) {
                return "post";
            }
        }
        for (RequestMethod m : httpMethods) {
            if (m.name().equals(RequestMethod.GET.name())) {
                return "get";
            }
        }
        return "post";
    }

    private String getHttpPath(RequestMapping methodAnna) {
        if (methodAnna.value().length > 0) {
            return methodAnna.value()[0];
        }
        if (methodAnna.path().length > 0) {
            return methodAnna.path()[0];
        }
        return null;
    }

    private String pathToName(String path) {
        List<String> words = splitCode(path);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < words.size(); i++) {
            String word = words.get(i).toLowerCase(Locale.ROOT);
            word = firstCharUpper(word);
            sb.append(word);
        }
        return sb.toString();
    }

    private String classToName(String clz) {
        String[] arr = clz.split("\\.");
        return arr[arr.length-1];
    }

    private Class getClassByName(String className) {
        try {
            if ("long".equals(className)) {
                className = Long.class.getName();
            } else if ("int".equals(className)) {
                className = Integer.class.getName();
            } else if ("short".equals(className)) {
                className = Short.class.getName();
            } else if ("double".equals(className)) {
                className = Double.class.getName();
            }
            return Class.forName(className);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private ApiDocTypeBO getDocTypeBO(String rootName,String name, Type type) {
        if("void".equals(type.getTypeName())){
            return null;
        }
        boolean forceArray  = false;
        String typeName = type.getTypeName();
        if(typeName.endsWith("[]")){
            forceArray = true;
            typeName = typeName.replace("[]","");
        }
        Class rawClz = null;
        Class realClz = null;
        if (type instanceof ParameterizedType) {
            rawClz = getClassByName(((ParameterizedType) type).getRawType().getTypeName());
            Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
            if (genericTypes.length > 0) {
                realClz = getClassByName(genericTypes[0].getTypeName());
            } else {
                realClz = rawClz;
            }
        } else {
            rawClz = getClassByName(typeName);
            realClz = rawClz;
        }
        ApiDocTypeBO bo = new ApiDocTypeBO();
        bo.setListType(forceArray||isArray(rawClz));
        bo.setClz(rawClz.getName());
        String returnName=classToName(rawClz.getName());
        bo.setName(StringUtils.isEmpty(name)?returnName:name);
        String jsType = JsTypeMapEnum.getJavaScriptTypeName(realClz);
        if ("object".equals(jsType)) {
            Field[] fields = realClz.getDeclaredFields();
            boolean loop = false;
            List<ApiDocTypeBO> types = new ArrayList<>();
            for (Field field : fields) {
                Column col = field.getAnnotation(Column.class);
                if(field.getGenericType().getTypeName().equals(type.getTypeName())){
                    loop = true;
                    break;
                }
                ApiDocTypeBO docType = getDocTypeBO(rootName, field.getName(), field.getGenericType());
                if (col != null) {
                    docType.setDatatype(docType.getDatatype());
                }
                types.add(docType);
            }
            if(!loop){
                bo.setFieldTypes(types);
            }
            bo.setTsType(firstCharUpper(classToName(realClz.getName())));
            bo.setObject(true);
        } else {
            bo.setTsType(jsType);
            bo.setObject(false);
        }
        return bo;
    }


    private String firstCharUpper(String word) {
        return word.substring(0, 1).toUpperCase(Locale.ROOT) + word.substring(1, word.length());
    }

    private String firstCharLower(String word) {
        return word.substring(0, 1).toLowerCase(Locale.ROOT) + word.substring(1, word.length());
    }

    private List<String> splitCode(String code) {

        List<String> list = new ArrayList<>();
        StringBuilder sb = null;
        int idx = 0;
        while (idx < code.length()) {
            String c = code.substring(idx, idx + 1);
            if (sb == null) {
                sb = new StringBuilder();
                sb.append(c);
            } else {
                if (StringUtils.isAllUpperCase(c) || "_".equals(c) || "-".equals(c) || "/".equals(c)) {
                    list.add(sb.toString());
                    sb = new StringBuilder();
                    sb.append(c);
                } else {
                    sb.append(c);
                }
            }
            idx++;
        }
        list.add(sb == null ? "" : sb.toString());
        List<String> result = new ArrayList<>();
        for (String str : list) {
            str = str.replaceAll("-", "");
            str = str.replaceAll("_", "");
            str = str.replaceAll("/", "");
            str = str.trim();
            if (str.length() > 0) {
                result.add(str.toLowerCase(Locale.ROOT));
            }
        }
        return result;
    }

    private boolean isArray(Class clz) {
        return clz.isArray() || Collection.class.isAssignableFrom(clz);
    }

    private String getModuleFromClz(String clzName) {
        String[] arr = clzName.split("\\.");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (arr.length >= (i + 1)) {
                list.add(arr[i]);
            }
        }
        return StringUtils.join(list, ".");
    }

    public static void main(String[] args) {
        ApiDocService service = new ApiDocService();
        List<ApiDocBO> list = service.processClz(ApiDocController.class);
        System.out.println(list.size());
    }


}
