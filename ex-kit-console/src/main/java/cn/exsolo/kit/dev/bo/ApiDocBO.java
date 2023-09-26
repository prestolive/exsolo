package cn.exsolo.kit.dev.bo;

import java.util.List;

/**
 * @author prestolive
 * @date 2021/3/24
 **/
public class ApiDocBO {

    private String path;

    /**
     * post or get
     */
    private String method;

    private String name;

    private String nameLower;

    private ApiDocTypeBO returnType;

    private List<ApiDocTypeBO> paramTypes;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameLower() {
        return nameLower;
    }

    public void setNameLower(String nameLower) {
        this.nameLower = nameLower;
    }

    public ApiDocTypeBO getReturnType() {
        return returnType;
    }

    public void setReturnType(ApiDocTypeBO returnType) {
        this.returnType = returnType;
    }

    public List<ApiDocTypeBO> getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(List<ApiDocTypeBO> paramTypes) {
        this.paramTypes = paramTypes;
    }
}
