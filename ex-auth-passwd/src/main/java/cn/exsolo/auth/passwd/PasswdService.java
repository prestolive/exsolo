package cn.exsolo.auth.passwd;

import cn.exsolo.auth.passwd.item.ExPasswdErrorCodeEnum;
import cn.exsolo.auth.passwd.po.UserEncryptPO;
import cn.exsolo.auth.passwd.utils.PasswordCheckStrength;
import cn.exsolo.auth.passwd.utils.PasswordHelper;
import cn.exsolo.batis.core.BaseDAO;
import cn.exsolo.batis.core.Condition;
import cn.exsolo.batis.core.utils.GenerateID;
import cn.exsolo.comm.ex.ExDeclaredException;
import cn.exsolo.comm.utils.TsUtil;
import cn.exsolo.kit.utils.ExAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 密码修改服务，所有密码参数在入参前，要求在前端默认加盐#@=yxy=@#，并做5次md5
 *
 * @author prestolive
 * @date 2024/2/19
 **/
@Service
public class PasswdService {

    @Autowired
    private BaseDAO baseDAO;

    /**
     *  修改密码
     * @param id
     * @param password
     * @param force
     */
    public void updatePassword(String id, String password, boolean force) {
        ExAssert.isNull(id, password);
        //已经存在的代码
        List<UserEncryptPO> exists = baseDAO.queryBeanByCond(UserEncryptPO.class,
                new Condition().eq("userId", id).orderBy("createTs"));
        //构建新密码
        String salt = GenerateID.generateShortUuid();
        UserEncryptPO cryptoPO = new UserEncryptPO();
        cryptoPO.setActive(true);
        cryptoPO.setUserId(id);
        cryptoPO.setSalt(salt);
        cryptoPO.setEncrypt(PasswordHelper.defaultPasswordEncrypt(password, salt));
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
                    new ExDeclaredException(ExPasswdErrorCodeEnum.SAME_PASSWORD);
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
                    String historyEncrypt = PasswordHelper.defaultPasswordEncrypt(password, salt);
                    //如果用历史的盐和现在的密码算出来的哈希值等于历史的encrypt，则意味着密码一致
                    if (historyEncrypt.equals(cryptoPO.getEncrypt())) {
                        new ExDeclaredException(ExPasswdErrorCodeEnum.RETRY_PASSWORD);
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
     * 修改密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     */
    public void changePassword(String userId,String oldPwd,String newPwd){
        UserEncryptPO passwd = baseDAO.queryOneBeanByCond(UserEncryptPO.class,new Condition().eq("userId",userId).eq("active",true));
        String oldPwdEncrypt = PasswordHelper.defaultPasswordEncrypt(oldPwd,passwd.getSalt());
        if(!oldPwdEncrypt.equals(passwd.getEncrypt())){
            //失败次数统计
            throw new ExDeclaredException(ExPasswdErrorCodeEnum.OLD_PASSWORD_NO_PASS);
        }
        updatePassword(userId,newPwd,false);
    }
}
