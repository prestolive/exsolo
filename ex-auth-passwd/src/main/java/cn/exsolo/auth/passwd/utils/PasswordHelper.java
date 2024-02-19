package cn.exsolo.auth.passwd.utils;

import cn.hutool.crypto.SecureUtil;

/**
 *
 * @author prestolive
 */
public class PasswordHelper {

    private static String algorithmName = "md5";

    private static final int hashIterations = 10;

    public static String defaultPasswordEncrypt(String password, String salt) {
        return passwordEncrypt(password,salt,10);
    }

    public static String passwordEncrypt(String password, String salt, int counts) {
        String encrypt = password+salt;
        for(int i=0;i<counts;i++){
            encrypt = SecureUtil.md5(encrypt);
        }
        return  encrypt;
    }
}