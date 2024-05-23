package com.zdf.mfwuser.exception;

import lombok.NoArgsConstructor;

/**
 *@Description TokenVerifyException
 *@Author mrzhang
 *@Date 2024/5/22 21:32
 */
@NoArgsConstructor
public class TokenVerifyException extends RuntimeException{
    public TokenVerifyException(String message){
        super(message);
    }
}
