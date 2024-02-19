package cn.exsolo.basic.item;

/**
 * 组织管理
 * @author prestolive
 * @date 2021/4/1
 **/
public enum ExOrgErrorCodeEnum {

    SCHEMA_NAME_ALREADY_EXISTS("组织类型名称 %s 已存在"),
    SCHEMA_CODE_ALREADY_EXISTS("组织类型编码 %s 已存在"),
    ORG_NAME_ALREADY_EXISTS("组织名称 %s 在同级下已存在");

    private String label;

    ExOrgErrorCodeEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
