package com.zdf.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.zdf.internalcommon.constant.JwtConstant;
import com.zdf.internalcommon.result.TokenResult;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 *@Description Generate jwt tokens and parse tokens
 *@Author mrzhang
 *@Date 2024/3/25 02:25
 */
public class JwtUtil {

    private JwtUtil(){
    }
    /**
     * @param userName:
     * @param userPassword:
     * @return String
     * @author mrzhang
     * @description generator Token
     * @date 2024/3/25 02:52
     */
    public static String generatorToken(String userName, String userPassword)
    {
        Map<String, String> map = new HashMap<>(3);
        map.put(JwtConstant.JWT_TOKEN_NAME, userName);
        map.put(JwtConstant.JWT_TOKEN_PASSWORD, userPassword);
        //Prevent generating the same token every time
        map.put(JwtConstant.JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());

        JWTCreator.Builder builder = JWT.create();

        map.forEach(
                builder::withClaim
        );
        return builder.sign(Algorithm.HMAC256(JwtConstant.SIGN));
    }
    /**
     * @param token:
     * @return TokenResult
     * @author mrzhang
     * @description parse token
     * @date 2024/3/25 03:15
     */
    public static TokenResult parseToken(String token)
    {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(JwtConstant.SIGN)).build().verify(token);
        String userName = verify.getClaim(JwtConstant.JWT_TOKEN_NAME).asString();
        String userPassword = verify.getClaim(JwtConstant.JWT_TOKEN_PASSWORD).asString();

        return TokenResult.builder()
                .userName(userName)
                .userPassword(userPassword)
                .build();
    }
    /**
     * @param token:
     * @return TokenResult
     * @author mrzhang
     * @description check token
     * @date 2024/3/25 03:16
     */
    public static TokenResult checkToken(String token)
    {
        TokenResult tokenResult;
        try {
            tokenResult = JwtUtil.parseToken(token);
        }catch (IllegalArgumentException | IllegalStateException var4){
            tokenResult = null;
        }
        return  tokenResult;
    }
}