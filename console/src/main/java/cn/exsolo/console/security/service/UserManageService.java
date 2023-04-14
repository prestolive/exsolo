package cn.exsolo.console.security.service;

import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.PageObject;
import cn.exsolo.batis.core.Pagination;
import cn.exsolo.batis.core.utils.GenerateID;
import cn.exsolo.batis.core.utils.TsUtil;
import cn.exsolo.console.item.ExUserErrorCodeEnum;
import cn.exsolo.console.item.ExUserStatusEnum;
import cn.exsolo.console.security.po.UserEncryptPO;
import cn.exsolo.console.security.po.UserPO;
import cn.exsolo.console.security.utils.PasswordCheckStrength;
import cn.exsolo.console.security.utils.PasswordHelper;
import cn.exsolo.kit.ex.ExAssert;
import cn.exsolo.kit.ex.ExErrorCodeException;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author prestolive
 * @date 2023/4/1
 **/
@Service
public class UserManageService {

    @Autowired
    private BaseDAO baseDAO;

    public PageObject<UserPO> page(String userCode, String userName, List<ExUserStatusEnum> enumStatus, Pagination pagination) {
        Condition cond = new Condition();
        cond.ifNotEmpty().lk("userCode", userCode);
        cond.ifNotEmpty().lk("userName", userName);
        if(ObjectUtils.isNotEmpty(enumStatus)){
            cond.in("status",enumStatus);
        }
        return baseDAO.queryBeanPageByCond(UserPO.class, cond, pagination);
    }

    public UserPO get(String userId){
        return baseDAO.queryBeanByID(UserPO.class,userId);
    }

    public void addNewUser(UserPO userPO, String password) {
        if (baseDAO.existsByCond(UserPO.class, new Condition().lower().eq("userCode", userPO.getUserCode()))) {
            throw new ExErrorCodeException(ExUserErrorCodeEnum.USER_ALREADY_EXISTS, userPO.getUserCode());
        }
        saveUser(userPO);
        updatePassword(userPO, password, true);
    }

    /**
     * 修改用户
     *
     * @param userPO
     * @param password
     */
    public void updateUser(UserPO userPO, String password) {
        ExAssert.isNull(userPO, userPO.getId());
        UserPO exist = baseDAO.queryBeanByID(UserPO.class, userPO.getId());
        //copy
        exist.setUserCode(userPO.getUserCode());
        exist.setUserName(userPO.getUserName());
        exist.setPhone(userPO.getPhone());
        exist.setEmail(userPO.getEmail());
        saveUser(userPO);
        if (StringUtils.isNotEmpty(password)) {
            updatePassword(userPO, password, true);
        }
    }

    private void saveUser(UserPO userPO) {
        ExAssert.isNull(userPO, userPO.getUserCode(), userPO.getUserName());
        if (userPO.getStatus() == null) {
            userPO.setStatus(ExUserStatusEnum.NORMAL);
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
        //已经存在的代码
        List<UserEncryptPO> exists = baseDAO.queryBeanByCond(UserEncryptPO.class,
                new Condition().eq("userId", userPO.getId()).orderBy("createTs"));
        //构建新密码
        String salt = GenerateID.generateShortUuid();
        UserEncryptPO cryptoPO = new UserEncryptPO();
        cryptoPO.setActive(true);
        cryptoPO.setUserId(userPO.getId());
        cryptoPO.setSalt(salt);
        cryptoPO.setEncrypt(PasswordHelper.encryptPassword(password, salt));
        cryptoPO.setLevel(PasswordCheckStrength.checkPasswordStrength(password));
        cryptoPO.setExpireTs(TsUtil.getDateTimeAcc2Min());
        cryptoPO.setExpireTs(TsUtil.getExpireTimestamp(Calendar.MONTH, 6));
        //
        if (exists.size() == 0) {
            baseDAO.insertOrUpdateValueObject(cryptoPO);
        } else {
            //最近一个密码一样则不让修改
            UserEncryptPO last = exists.stream().filter(row -> row.getActive()).findFirst().orElse(null);
            if (last != null) {
                if (!force && last.getEncrypt().equals(cryptoPO.getEncrypt())) {
                    new ExErrorCodeException(ExUserErrorCodeEnum.SAME_PASSWORD);
                }
            }
            //只保留使用超过3天的密码
            List<UserEncryptPO> checkList = new ArrayList<>();
            List<UserEncryptPO> removeList = new ArrayList<>();
            int count = 0;
            for (UserEncryptPO row : exists) {
                if (TsUtil.isTimeOverflow(row.getCreateTs(), Calendar.getInstance().getTime(), Calendar.DAY_OF_MONTH, 3)) {
                    removeList.add(row);
                    continue;
                }
                if (count < 5) {
                    checkList.add(row);
                    count++;
                } else {
                    removeList.add(row);
                }
            }
            //近期密码的字典重复判断//FIXME 这个要增加参数控制
            if (!force && checkList.size() > 0) {
                for (UserEncryptPO history : checkList) {
                    String historyEncrypt = PasswordHelper.encryptPassword(password, salt);
                    //如果用历史的盐和现在的密码算出来的哈希值等于历史的encrypt，则意味着密码一致
                    if (historyEncrypt.equals(cryptoPO.getEncrypt())) {
                        new ExErrorCodeException(ExUserErrorCodeEnum.RETRY_PASSWORD);
                    }
                }
            }
            //以上都通过
            baseDAO.insertOrUpdateValueObject(cryptoPO);
            for (UserEncryptPO row : checkList) {
                if (row.getActive()) {
                    row.setActive(false);
                    baseDAO.insertOrUpdateValueObject(row);
                }
            }
            for (UserEncryptPO row : removeList) {
                baseDAO.deleteByID(UserEncryptPO.class, row.getId());
            }
        }
    }

    /**
     * 修改状态
     *
     * @param userId
     * @param status
     */
    public void updateUserStatus(String userId, ExUserStatusEnum status) {
        ExAssert.isNull(userId, status);
        UserPO userPO = baseDAO.queryBeanByID(UserPO.class, userId);
        ExAssert.isNull(userPO);
        userPO.setStatus(status);
        baseDAO.insertOrUpdateValueObject(userPO);
    }

}
