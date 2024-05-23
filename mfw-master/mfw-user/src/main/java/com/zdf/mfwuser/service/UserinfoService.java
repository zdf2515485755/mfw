package com.zdf.mfwuser.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zdf.internalcommon.entity.Userinfo;
import com.zdf.internalcommon.request.LogInRequestDto;
import com.zdf.internalcommon.request.RegisterUserRequestDto;
import com.zdf.internalcommon.response.VerificationCodeResponseDto;
import com.zdf.internalcommon.result.ResponseResult;

/**
* @author mrzhang
* @description 针对表【userinfo】的数据库操作Service
* @createDate 2024-05-21 17:20:03
*/
public interface UserinfoService extends IService<Userinfo> {
    ResponseResult<Boolean> checkPhoneNumber(String phoneNumber);
    ResponseResult<String> registerUser(RegisterUserRequestDto registerUserRequestDto);
    ResponseResult<VerificationCodeResponseDto> getVerificationCode();
    ResponseResult<String> login(LogInRequestDto logInRequestDto);
}
