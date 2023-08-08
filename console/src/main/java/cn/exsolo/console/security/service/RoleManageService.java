package cn.exsolo.console.security.service;

import cn.exsolo.batis.core.*;
import cn.exsolo.console.item.ExUserErrorCodeEnum;
import cn.exsolo.console.security.po.RolePO;
import cn.exsolo.console.security.po.RolePermissionPO;
import cn.exsolo.console.security.po.UserPO;
import cn.exsolo.console.security.po.UserRolePO;
import cn.exsolo.kit.utils.ExAssert;
import cn.exsolo.comm.ex.ExDeclaredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/5/16
 **/
@Service
public class RoleManageService {

    @Autowired
    private BaseDAO baseDAO;

    public PageObject<RolePO> page(Condition fCond, Pagination pagination) {
        Condition cond = new Condition();
        if(fCond!=null){
            cond.and(fCond);
        }
        cond.orderBy("createTs", Condition.DESC);
        return baseDAO.queryBeanPageByCond(RolePO.class, cond, pagination);
    }

    public RolePO get(String roleId){
        return baseDAO.queryBeanByID(RolePO.class,roleId);
    }

    public List<RolePermissionPO> getRolePowers(String roleId){
        return baseDAO.queryBeanByCond(RolePermissionPO.class,new Condition().eq("roleId",roleId));
    }

    public PageObject<UserPO> getUserByRole(String roleID,Condition fCond,  Pagination pagination){
        Condition cond = new Condition();
        if(fCond!=null){
            cond.and(fCond);
        }
        ConditionFilter filter= new ConditionFilter("id", UserRolePO.class,"userId");
        filter.eq("roleID",roleID);
        cond.exist(filter);
        cond.orderBy("createTs", Condition.DESC);
        return baseDAO.queryBeanPageByCond(UserPO.class, cond, pagination);
    }

    public void addRole(RolePO role) {
        if (baseDAO.existsByCond(RolePO.class, new Condition().lower().eq("roleName",role.getRoleName().toLowerCase()))) {
            throw new ExDeclaredException(ExUserErrorCodeEnum.ROLE_ALREADY_EXISTS,role.getRoleName());
        }
        //FIXME operator
        baseDAO.insertOrUpdateValueObject(role);
    }

    public void modifyRoleName(String roleId, String roleName){
        RolePO role = baseDAO.queryBeanByID(RolePO.class,roleId);
        ExAssert.isNull(role);
        role.setRoleName(roleName);
        //FIXME operator
        baseDAO.insertOrUpdateValueObject(role);
    }

    public void deleteRole(String roleId){
        //先清空权限
        configRolePermission(roleId,null);
        baseDAO.deleteByID(RolePO.class,roleId);
    }

    public void configRolePermission(String roleId, List<String> permissions){
        RolePO role = baseDAO.queryBeanByID(RolePO.class,roleId);
        ExAssert.isNull(role);
        baseDAO.deleteByCond(RolePermissionPO.class,new Condition().eq("roleId",roleId));
        if(permissions!=null&&permissions.size()>0){
            for(String permission:permissions){
                RolePermissionPO po = new RolePermissionPO();
                po.setRoleId(roleId);
                po.setPermission(permission);
                //FIXME operator
                baseDAO.insertOrUpdateValueObject(po);
            }
            role.setPermissionCount(permissions.size());
        }else{
            role.setPermissionCount(0);
        }
        baseDAO.insertOrUpdateValueObject(role);
        //更新相关角色的所有权限
        updateRoleFlatData(roleId);
    }

    private void updateRoleFlatData(String roleId){
        //TODO
    }


}
