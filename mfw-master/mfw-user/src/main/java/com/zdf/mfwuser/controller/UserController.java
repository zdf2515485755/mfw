package com.zdf.mfwuser.controller;

import com.zdf.internalcommon.request.LogInRequestDto;
import com.zdf.internalcommon.request.RegisterUserRequestDto;
import com.zdf.internalcommon.response.VerificationCodeResponseDto;
import com.zdf.internalcommon.result.ResponseResult;
import com.zdf.mfwuser.annotation.PassTokenCheck;
import com.zdf.mfwuser.service.impl.UserinfoServiceImpl;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 *@Description api for operating Users
 *@Author mrzhang
 *@Date 2024/5/21 17:24
 */
@RestController
@RequestMapping("/user")
@PassTokenCheck
public class UserController {

    @Resource
    private UserinfoServiceImpl userinfoService;

    @GetMapping("/checkPhoneNumber/{phoneNumber}")
    public ResponseResult<Boolean> checkPhoneNumber(@Valid @PathVariable("phoneNumber")@NotBlank String phoneNumber){
        return userinfoService.checkPhoneNumber(phoneNumber);
    }

    @PostMapping("/registerUser")
    public ResponseResult<String> registerUser(@Validated @RequestBody RegisterUserRequestDto registerUserRequestDto){
        return userinfoService.registerUser(registerUserRequestDto);
    }

    @GetMapping("/getVerificationCode")
    public ResponseResult<VerificationCodeResponseDto> getVerificationCode(){
        return userinfoService.getVerificationCode();
    }

    @GetMapping("/login")
    public ResponseResult<String> login(@Validated @RequestBody LogInRequestDto logInRequestDto){
        return userinfoService.login(logInRequestDto);
    }
}
