package cn.exsolo.kit.setting.vo;

/**
 * 配置模块类
 * @author prestolive
 * @date 2023/10/4
 **/
public class ExSettingProviderVO {

    private String moduleName;

    private String modulePackage;

    private String moduleClz;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModulePackage() {
        return modulePackage;
    }

    public void setModulePackage(String modulePackage) {
        this.modulePackage = modulePackage;
    }

    public String getModuleClz() {
        return moduleClz;
    }

    public void setModuleClz(String moduleClz) {
        this.moduleClz = moduleClz;
    }
}
