package com.zdf.internalcommon.constant;

import lombok.Data;

/**
 *@Description User Constant
 *@Author mrzhang
 *@Date 2024/5/23 02:33
 */
@Data
public class UserConstant {
    public static final String PHONE_PATTERN = "^1((3\\d)|(4[5-9])|(5[0-3,5-9])|(6[5,6])|(7\\d)|(8\\d)|(9[189]))\\d{8}$";

    private UserConstant(){}
}
