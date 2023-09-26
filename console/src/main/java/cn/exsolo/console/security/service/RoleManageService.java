package cn.exsolo.console.security.service;

import cn.exsolo.auth.utils.SecurityUserContext;
import cn.exsolo.batis.core.*;
import cn.exsolo.console.item.ExUserErrorCodeEnum;
import cn.exsolo.console.security.po.*;
import cn.exsolo.kit.utils.ExAssert;
import cn.exsolo.comm.ex.ExDeclaredException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author prestolive
 * @date 2021/5/16
 **/
@Service
public class RoleManageService {

    @Autowired
    private BaseDAO baseDAO;

    public PageObject<RolePO> rolePage(Condition fCond, Pagination pagination) {
        Condition cond = new Condition();
        if (fCond != null) {
            cond.and(fCond);
        }
        cond.orderBy("createTs", Condition.DESC);
        return baseDAO.queryBeanPageByCond(RolePO.class, cond, pagination);
    }

    public RolePO get(String roleId) {
        return baseDAO.queryBeanByID(RolePO.class, roleId);
    }

    public List<RolePermissionPO> permissionRole(String roleId) {
        return baseDAO.queryBeanByCond(RolePermissionPO.class, new Condition().eq("roleId", roleId));
    }

    public PageObject<UserPO> userPageByRole(String roleID, Condition fCond, Pagination pagination) {
        Condition cond = new Condition();
        if (fCond != null) {
            cond.and(fCond);
        }
        ConditionFilter filter = new ConditionFilter("id", UserRolePO.class, "userId");
        filter.eq("roleID", roleID);
        cond.exist(filter);
        cond.orderBy("createTs", Condition.DESC);
        return baseDAO.queryBeanPageByCond(UserPO.class, cond, pagination);
    }

    public void addRole(RolePO role) {
        if (baseDAO.existsByCond(RolePO.class, new Condition().lower().eq("roleName", role.getRoleName().toLowerCase()))) {
            throw new ExDeclaredException(ExUserErrorCodeEnum.ROLE_ALREADY_EXISTS, role.getRoleName());
        }
        //FIXME operator
        baseDAO.insertOrUpdateValueObject(role);
    }

    public void modifyRoleName(String roleId, String roleName) {
        RolePO role = baseDAO.queryBeanByID(RolePO.class, roleId);
        ExAssert.isNull(role);
        role.setRoleName(roleName);
        //FIXME operator
        baseDAO.insertOrUpdateValueObject(role);
    }

    public void deleteRole(String roleId) {
        //先清空权限
        permissionSet(roleId, null);
        baseDAO.deleteByID(RolePO.class, roleId);
    }

    public void permissionSet(String roleId, String[] permissions) {
        RolePO role = baseDAO.queryBeanByID(RolePO.class, roleId);
        ExAssert.isNull(role);
        baseDAO.deleteByCond(RolePermissionPO.class, new Condition().eq("roleId", roleId));
        if (permissions != null && permissions.length > 0) {
            for (String permission : permissions) {
                RolePermissionPO po = new RolePermissionPO();
                po.setRoleId(roleId);
                po.setPermission(permission);
                //FIXME operator
                baseDAO.insertOrUpdateValueObject(po);
            }
            role.setPermissionCount(permissions.length);
        } else {
            role.setPermissionCount(0);
        }
        baseDAO.insertOrUpdateValueObject(role);
        //更新相关角色的所有权限
        updateRoleFlatData(roleId);
    }

    /**
     * 为权限分配个人用户
     * @param roleId
     * @param userIds
     */
    public void userSet(String roleId, String[] userIds) {
        for(String userId:userIds){
            updateRoleUser(roleId,userId,null);
        }
    }

    /**
     * 当角色发生变化的时候更新权限
     * @param roleId
     */
    private void updateRoleFlatData(String roleId) {
        List<UserRolePO> existUserRoles = baseDAO.queryBeanByCond(UserRolePO.class, new Condition().eq("roleId",roleId));
        for(UserRolePO userRole:existUserRoles){
            updateRoleUser(roleId,userRole.getUserId(),userRole.getOrgId());
        }
    }

    /**
     * 更新用户角色及相关的权限
     *
     * @param roleId
     * @param userId
     * @param orgId
     */
    private void updateRoleUser(String roleId, String userId, String orgId) {
        //查询和补充UserRolePO，已有的保留，方便知道最早的配置人
        Condition cond = new Condition();
        cond.eq("roleId", roleId);
        cond.eq("userId", userId);
        if (StringUtils.isNotEmpty(orgId)) {
            cond.eq("orgId", orgId);
        }
        List<UserRolePO> existUserRoles = baseDAO.queryBeanByCond(UserRolePO.class, cond);
        UserRolePO userRole = null;
        if (existUserRoles.size() == 0) {
            userRole = new UserRolePO();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRole.setOrgId(orgId);
            userRole.setOperator(SecurityUserContext.getUserID());
            baseDAO.insertOrUpdateValueObject(userRole);
        } else {
            userRole = existUserRoles.get(0);
        }
        //更新权限总表，删除多余的，保留已有的，添加缺少的
        //该角色的所有权限
        List<RolePermissionPO> permissionPOS = baseDAO.queryBeanByCond(RolePermissionPO.class,new Condition().eq("roleId",roleId));
        Set<String> permissionSet = permissionPOS.stream().map(item->item.getPermission()).collect(Collectors.toSet());
        //改角色目前存在的权限
        List<UserFlatPermissionPO> existPermissions = baseDAO.queryBeanByCond(UserFlatPermissionPO.class,cond);
        //剩下的就是要删除的
        Set<String> toDeletePermissions = existPermissions.stream().map(item->item.getPermission()).collect(Collectors.toSet());
        toDeletePermissions.removeAll(permissionSet);
        if(existPermissions.size()>0){
            List<UserFlatPermissionPO> deleteRequires = existPermissions.stream().filter(item->toDeletePermissions.contains(item.getPermission())).collect(Collectors.toList());
            for(UserFlatPermissionPO toDelete:deleteRequires){
                baseDAO.deleteByID(UserFlatPermissionPO.class,toDelete.getId());
            }
        }
        //需要增补的权限
        Set<String> existPermissionSet = existPermissions.stream().map(item->item.getPermission()).collect(Collectors.toSet());
        permissionSet.removeAll(existPermissionSet);
        if(permissionSet.size()>0){
            for(String permission:permissionSet){
                UserFlatPermissionPO item = new UserFlatPermissionPO();
                item.setUserId(userRole.getUserId());
                item.setUserRoleId(userRole.getId());
                item.setRoleId(userRole.getRoleId());
                item.setOrgId(userRole.getOrgId());
                item.setOperator(SecurityUserContext.getUserID());
                item.setPermission(permission);
                baseDAO.insertOrUpdateValueObject(item);
            }
        }

    }

    /**
     * 删除角色中的用户
     * @param roleId
     * @param userId
     */
    public void userDelete(String roleId, String userId){
        Condition cond = new Condition();
        cond.eq("roleId", roleId);
        cond.eq("userId", userId);
        cond.isNull("orgId");
        List<UserRolePO> existUserRoles = baseDAO.queryBeanByCond(UserRolePO.class, cond);
        for(UserRolePO userRolePO:existUserRoles){
            baseDAO.deleteByID(UserRolePO.class,userRolePO.getId());
            baseDAO.deleteByCond(UserFlatPermissionPO.class,new Condition().eq("userRoleId",userRolePO.getId()));
        }
    }


}
