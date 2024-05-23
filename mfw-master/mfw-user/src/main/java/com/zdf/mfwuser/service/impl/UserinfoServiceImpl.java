package com.zdf.mfwuser.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.zdf.internalcommon.constant.*;
import com.zdf.internalcommon.entity.Userinfo;
import com.zdf.internalcommon.request.LogInRequestDto;
import com.zdf.internalcommon.request.RegisterUserRequestDto;
import com.zdf.internalcommon.response.VerificationCodeResponseDto;
import com.zdf.internalcommon.result.ResponseResult;
import com.zdf.mfwuser.mapper.UserinfoMapper;
import com.zdf.mfwuser.service.UserinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
* @author mrzhang
* @description 针对表【userinfo】的数据库操作Service实现
* @createDate 2024-05-21 17:20:03
*/
@Service
@Slf4j
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo>
    implements UserinfoService {

    @Resource
    private UserinfoMapper userinfoMapper;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public ResponseResult<Boolean> checkPhoneNumber(String phoneNumber) {
        if (!ReUtil.isMatch(UserConstant.PHONE_PATTERN, phoneNumber)){
            return ResponseResult.fail(StatusCode.PHONE_NUMBER_FORMAT_IS_ERROR.getCode(), StatusCode.PHONE_NUMBER_FORMAT_IS_ERROR.getMessage());
        }
        QueryWrapper<Userinfo> userinfoQueryWrapper = new QueryWrapper<>();
        userinfoQueryWrapper.eq("phone", phoneNumber);
        Userinfo userinfo = userinfoMapper.selectOne(userinfoQueryWrapper);
        if (Objects.isNull(userinfo)){
            return ResponseResult.success(Boolean.TRUE);
        }
        return ResponseResult.fail(Boolean.FALSE);
    }

    @Override
    public ResponseResult<String> registerUser(RegisterUserRequestDto registerUserRequestDto) {
        //验证验证码
        String uuid = registerUserRequestDto.getUuid();
        String verificationCodeKey = RedisConstant.VERIFY_CODE_KEY_PREFIX + uuid;
        String verificationCode = (String) redisTemplate.opsForValue().get(verificationCodeKey);
        if (Objects.isNull(verificationCode)){
            return ResponseResult.fail(StatusCode.VERIFY_CODE_HAS_EXPIRED.getCode(), StatusCode.VERIFY_CODE_HAS_EXPIRED.getMessage());
        }
        if (!verificationCode.equals(registerUserRequestDto.getVerificationCode())){
            return ResponseResult.fail(StatusCode.VERIFY_CODE_IS_ERROR.getCode(), StatusCode.VERIFY_CODE_IS_ERROR.getMessage());
        }
        redisTemplate.delete(verificationCodeKey);
        Userinfo userinfo = new Userinfo();
        String password = registerUserRequestDto.getPassword();
        String sha256 = SecureUtil.sha256(password);
        BeanUtils.copyProperties(registerUserRequestDto, userinfo);
        userinfo.setPassword(sha256);
        int count = userinfoMapper.insert(userinfo);
        if (count <= 0){
            return ResponseResult.fail(StatusCode.INSERT_USER_ERROR.getCode(), StatusCode.INSERT_USER_ERROR.getMessage());
        }
        return ResponseResult.success("success");
    }

    @Override
    public ResponseResult<VerificationCodeResponseDto> getVerificationCode() {
        //生成验证码
        Captcha specCaptcha = new SpecCaptcha(ImageConstant.DEFAULT_WIDTH, ImageConstant.DEFAULT_HEIGHT, ImageConstant.DEFAULT_LENGTH);
        String uuid = IdUtil.simpleUUID();
        String verificationCodeKey = RedisConstant.VERIFY_CODE_KEY_PREFIX + uuid;
        String verificationCode = specCaptcha.text().toLowerCase();
        log.info("uuid: {}", uuid);
        log.info("code: {}", verificationCode);
        // 存入redis
        redisTemplate.opsForValue().set(verificationCodeKey, verificationCode, 120, TimeUnit.SECONDS);

        VerificationCodeResponseDto verificationCodeResponseDto = VerificationCodeResponseDto.builder().uuid(uuid)
                .image(specCaptcha.toBase64())
                .build();

        return ResponseResult.success(verificationCodeResponseDto);
    }

    @Override
    public ResponseResult<String> login(LogInRequestDto logInRequestDto) {
        QueryWrapper<Userinfo> userinfoQueryWrapper = new QueryWrapper<>();
        userinfoQueryWrapper.eq("phone", logInRequestDto.getPhone());
        Userinfo userinfo = userinfoMapper.selectOne(userinfoQueryWrapper);
        if (Objects.isNull(userinfo)){
            return ResponseResult.fail(StatusCode.USER_IS_NOT_EXIT.getCode(), StatusCode.USER_IS_NOT_EXIT.getMessage());
        }
        if (!SecureUtil.sha256(logInRequestDto.getPassword()).equals(userinfo.getPassword())){
            return ResponseResult.fail(StatusCode.PASSWORD_IS_ERROR.getCode(), StatusCode.PASSWORD_IS_ERROR.getMessage());
        }
        HashMap<String, Object> playLoad = new HashMap<>(1);
        playLoad.put(JwtConstant.JWT_TOKEN_PHONE, logInRequestDto.getPhone());
        String token = JWTUtil.createToken(playLoad, JwtConstant.SIGN.getBytes());
        String tokenKey = RedisConstant.TOKEN_KEY_PREFIX + logInRequestDto.getPhone();
        redisTemplate.opsForValue().set(tokenKey, token, RedisConstant.TOKEN_EXPIRE_TIME, TimeUnit.SECONDS);
        return ResponseResult.success(token);
    }
}