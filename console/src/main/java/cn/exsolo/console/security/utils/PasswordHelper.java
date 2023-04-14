package cn.exsolo.console.security.utils;

import cn.hutool.crypto.SecureUtil;

/**
 *
 * @author prestolive
 */
public class PasswordHelper {

    private static String algorithmName = "md5";

    private static final int hashIterations = 10;

    public static String encryptPassword(String password, String salt) {
        String encrypt = SecureUtil.md5(password+salt);
        for(int i=0;i<10;i++){
            encrypt = SecureUtil.md5(encrypt);
        }
        return  encrypt;
    }
}