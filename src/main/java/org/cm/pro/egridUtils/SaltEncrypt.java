package org.cm.pro.egridUtils;

import org.cm.pro.utils.ZTWMath;
import org.cm.pro.utils.ZTWString;

public class SaltEncrypt {
    private String encrytPassword;
    private String salt;

    public String getEncrytPassword(){
        return this.encrytPassword;
    }

    public String getSalt(){
        return this.salt;
    }

    /**
     * 需与Shiro的密码加密方式保持一致
     * @param password
     */

    public SaltEncrypt(String password) {
        this(password, ZTWMath.randomId(), null);
    }

    public SaltEncrypt(String password, String privateSalt) {
        this(password, ZTWMath.randomId(), privateSalt);
    }

    /**
     * 需与Shiro的密码加密方式保持一致
     * @param password
     * @param hexString
     * @param string
     */
    public SaltEncrypt(String password, String salt, String privateSalt) {
        this(password, salt, privateSalt, PSWD_HASH, HASH_ITERATIONS);
    }

    /**
     * 需与Shiro的密码加密方式保持一致
     * @param password  待加密密码
     * @param salt  salt
     * @param privateSalt  私有Salt
     * @param hashAlgorithm  Hash算法
     * @param iterations  迭代次数
     */
    public SaltEncrypt(String password, String salt, String privateSalt, String hashAlgorithm, Integer iterations) {
        this.salt = salt;
        String _salt = salt;
        if (!ZTWString.isEmpty(privateSalt)) {
            _salt = privateSalt + salt;
        }

        byte[] hash = HashUtil.hash(hashAlgorithm, password, _salt, iterations);
        this.encrytPassword = HexUtil.encodeToString(hash);
    }

    public static String getEncrytPassword(String password, String salt) {
        return HexUtil.encodeToString(HashUtil.hash(PSWD_HASH, password, salt, HASH_ITERATIONS));
    }

    public final static String PSWD_HASH = "MD5";
    public final static int HASH_ITERATIONS = 2;

}
