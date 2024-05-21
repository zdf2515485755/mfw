package com.zdf.internalcommon.constant;
/**
 *@Description Redis constant
 *@Author mrzhang
 *@Date 2024/4/20 22:44
 */
public class RedisConstant {
    public static final String VERIFY_CODE_KEY_PREFIX = "verify_code:";
    public static final String TOKEN_KEY_PREFIX = "token:";
    public static final String USER_KEY_PREFIX = "user:";
    public static final Integer VERIFY_CODE_EXPIRE_TIME = 120;
    public static final Integer TOKEN_EXPIRE_TIME = 30 * 60;
    public static final Integer USER_EXPIRE_TIME = 15 * 24 * 60 * 60;

    private RedisConstant(){}
}
