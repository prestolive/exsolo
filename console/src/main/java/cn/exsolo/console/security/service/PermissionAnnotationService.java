package cn.exsolo.console.security.service;

import cn.exsolo.auth.shiro.ext.stereotype.*;
import cn.exsolo.comm.utils.ExAnnotationUtil;
import cn.exsolo.console.security.vo.PermissionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author prestolive
 * @date 2023/7/12
 **/
@Service
public class PermissionAnnotationService {

    @Autowired
    private ApplicationContext context;

    private List<PermissionVO> permissions;

    public List<PermissionVO> getPermissions() {
        return permissions;
    }

    /**
     * 为了获取扫描包，所以在容器启动后再执行初始化
     */
    @PostConstruct
    public void init() {
        Map<String,PermissionVO> map = new LinkedHashMap<>();
        List<Class<?>> clzs = ExAnnotationUtil.getAnnotationFromContext(context, AccessProvider.class);
        for (Class clz : clzs) {
            Method[] methods = clz.getMethods();
            AccessProvider ap = (AccessProvider) clz.getAnnotation(AccessProvider.class);
            String module = ap.module();
            String node = ap.node();
            String moduleNodeLabel = ap.label();
            for (Method method : methods) {
                AccessView accessView = null;
                accessView = method.getAnnotation(AccessView.class);
                PermissionVO permissionVO = null;
                if (accessView != null) {
                    permissionVO = getPermissionVO(module, node, moduleNodeLabel, "view", "查看");
                }
                AccessEdit accessEdit = null;
                accessEdit = method.getAnnotation(AccessEdit.class);
                if (accessEdit != null) {
                    permissionVO = getPermissionVO(module, node, moduleNodeLabel, "edit", "编辑");
                }
                AccessConfig accessConfig = null;
                accessConfig = method.getAnnotation(AccessConfig.class);
                if (accessConfig != null) {
                    permissionVO = getPermissionVO(module, node, moduleNodeLabel, "config", "配置");
                }
                AccessCommon accessCommon = null;
                accessCommon = method.getAnnotation(AccessCommon.class);
                if (accessCommon != null) {
                    permissionVO = getPermissionVO(module, node, moduleNodeLabel, "common", "通用");
                }
                AccessCustom accessCustom = null;
                accessCustom = method.getAnnotation(AccessCustom.class);
                if (accessCustom != null) {
                    permissionVO = getPermissionVO(module, node, moduleNodeLabel, accessCustom.key(), accessCustom.label());
                }
                if(permissionVO!=null){
                    map.put(permissionVO.getPermission(),permissionVO);
                }
            }
        }
        this.permissions = new ArrayList<>(map.values());
    }

    private PermissionVO getPermissionVO(String module, String node, String moduleNodeLabel, String action, String actionLabel) {
        PermissionVO vo = new PermissionVO();
        vo.setModule(module);
        vo.setNode(node);
        vo.setAction(action);
        vo.setPermission(String.format("%s:%s:%s",module,node,action));
        vo.setModuleNodeLabel(moduleNodeLabel);
        vo.setActionLabel(actionLabel);
        return vo;
    }
}
