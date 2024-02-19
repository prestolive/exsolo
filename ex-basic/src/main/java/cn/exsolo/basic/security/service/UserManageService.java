package cn.exsolo.basic.security.service;

import cn.exsolo.auth.passwd.PasswdService;
import cn.exsolo.basic.item.ExUserErrorCodeEnum;
import cn.exsolo.basic.security.po.UserPO;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.comm.utils.TsUtil;
import cn.exsolo.kit.item.ItemCommStatusEnum;
import cn.exsolo.kit.utils.ExAssert;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author prestolive
 * @date 2021/4/1
 **/
@Service
public class UserManageService {

    @Autowired
    private BaseDAO baseDAO;

    @Autowired
    private PasswdService passwdService;

    public PageObject<UserPO> page(Condition fCond, List<ItemCommStatusEnum> enumStatus, Pagination pagination) {
        Condition cond = new Condition();
        if(fCond!=null){
            cond.and(fCond);
        }
        if(ObjectUtils.isNotEmpty(enumStatus)){
            cond.in("status",enumStatus);
        }
        cond.orderBy("createTs", Condition.DESC);
        return baseDAO.queryBeanPageByCond(UserPO.class, cond, pagination);
    }

    public UserPO get(String userId){
        return baseDAO.queryBeanByID(UserPO.class,userId);
    }

    public void addNewUser(UserPO userPO, String password) {
        if (baseDAO.existsByCond(UserPO.class, new Condition().lower().eq("loginCode", userPO.getLoginCode().toLowerCase()))) {
            throw new ExDeclaredException(ExUserErrorCodeEnum.USER_ALREADY_EXISTS, userPO.getLoginCode());
        }
        saveUser(userPO);
        updatePassword(userPO, password, true);
    }

    /**
     * 修改用户
     * @param userPO
     */
    public void modifyUser(UserPO userPO) {
        ExAssert.isNull(userPO, userPO.getId());
        UserPO exist = baseDAO.queryBeanByID(UserPO.class, userPO.getId());
        //copy
        exist.setLoginCode(userPO.getLoginCode());
        exist.setUserName(userPO.getUserName());
        exist.setPhone(userPO.getPhone());
        exist.setEmail(userPO.getEmail());
        saveUser(userPO);
    }

    public void changePassword(String userId,String password){
        ExAssert.isNull(userId,password);
        UserPO exist = baseDAO.queryBeanByID(UserPO.class, userId);
        updatePassword(exist, password, true);
    }

    private void saveUser(UserPO userPO) {
        ExAssert.isNull(userPO, userPO.getLoginCode(), userPO.getUserName());
        if (userPO.getStatus() == null) {
            userPO.setStatus(ItemCommStatusEnum.NORMAL);
        }
        if (StringUtils.isEmpty(userPO.getActiveTs())) {
            userPO.setActiveTs(TsUtil.getTimestamp());
        }
        baseDAO.insertOrUpdateValueObject(userPO);
    }

    /**
     * 修改密码
     *
     * @param userId
     * @param password
     * @param force    是否强制修改，为true不校验密码重复、密码字典存在。
     */
    private void updatePassword(String userId, String password, boolean force) {
        ExAssert.isNull(userId, password);
        UserPO userPO = baseDAO.queryBeanByID(UserPO.class, userId);
        ExAssert.isNull(userPO);
        updatePassword(userPO, password, force);
    }

    /**
     * 修改密码
     *
     * @param userPO
     * @param password
     * @param force    是否强制修改，为true不校验密码重复、密码字典存在。
     */
    private void updatePassword(UserPO userPO, String password, boolean force) {
        ExAssert.isNull(userPO, password);
        passwdService.updatePassword(userPO.getId(),password,force);
    }

    /**
     * 修改状态
     *
     * @param userId
     * @param status
     */
    public void updateUserStatus(String userId, ItemCommStatusEnum status) {
        ExAssert.isNull(userId, status);
        UserPO userPO = baseDAO.queryBeanByID(UserPO.class, userId);
        ExAssert.isNull(userPO);
        userPO.setStatus(status);
        baseDAO.insertOrUpdateValueObject(userPO);
    }

}
