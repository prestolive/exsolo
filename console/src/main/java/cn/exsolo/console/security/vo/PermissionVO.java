package cn.exsolo.console.security.vo;

/**
 * 所有注解权限
 * @author prestolive
 * @date 2023/7/12
 **/
public class PermissionVO {

    private String module;

    private String node;

    private String action;

    private String permission;

    private String moduleNodeLabel;

    private String actionLabel;

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getNode() {
        return node;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getModuleNodeLabel() {
        return moduleNodeLabel;
    }

    public void setModuleNodeLabel(String moduleNodeLabel) {
        this.moduleNodeLabel = moduleNodeLabel;
    }

    public String getActionLabel() {
        return actionLabel;
    }

    public void setActionLabel(String actionLabel) {
        this.actionLabel = actionLabel;
    }
}
