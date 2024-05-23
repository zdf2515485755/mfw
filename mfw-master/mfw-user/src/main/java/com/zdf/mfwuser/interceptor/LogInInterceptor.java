package com.zdf.mfwuser.interceptor;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.zdf.internalcommon.constant.JwtConstant;
import com.zdf.internalcommon.constant.RedisConstant;
import com.zdf.internalcommon.constant.StatusCode;
import com.zdf.mfwuser.annotation.PassTokenCheck;
import com.zdf.mfwuser.exception.TokenVerifyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *@Description LogInInterceptor
 *@Author mrzhang
 *@Date 2024/5/22 21:05
 */
public class LogInInterceptor implements HandlerInterceptor {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            return Boolean.TRUE;
        }
        //获取controller上的注解和方法伤的注解
        HandlerMethod handlerMethod= (HandlerMethod) handler;
        Class<?> beanType = handlerMethod.getBeanType();
        Annotation annotation = beanType.getAnnotation(PassTokenCheck.class);
        PassTokenCheck methodAnnotation = handlerMethod.getMethodAnnotation(PassTokenCheck.class);
        if (!Objects.isNull(annotation) || !Objects.isNull(methodAnnotation)){
            return Boolean.TRUE;
        }
        String token = request.getHeader("Authorization");
        if (Objects.isNull(token)){
            throw new TokenVerifyException(StatusCode.TOKEN_IS_EMPTY.getMessage());
        }
        //检查token是否过期
        JWT jwt = JWTUtil.parseToken(token);
        String phone = (String)jwt.getPayload(JwtConstant.JWT_TOKEN_NAME);
        String tokenKey = RedisConstant.TOKEN_KEY_PREFIX + phone;
        String redisToken = (String)redisTemplate.opsForValue().get(tokenKey);
        if (Objects.isNull(redisToken)){
            throw new TokenVerifyException(StatusCode.TOKEN_HAS_EXPIRED.getMessage());
        }
        //检查token是否正确
        boolean verifyResult = JWTUtil.verify(token, JwtConstant.SIGN.getBytes());
        if (!verifyResult){
            throw new TokenVerifyException(StatusCode.TOKEN_IS_ERROR.getMessage());
        }
        //token续期
        redisTemplate.opsForValue().set(tokenKey, token, RedisConstant.TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        return Boolean.TRUE;
    }
}
