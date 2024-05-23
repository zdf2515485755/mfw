package com.zdf.internalcommon.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *@Description Register User Request Dto
 *@Author mrzhang
 *@Date 2024/5/21 19:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserRequestDto {

    @NotBlank(message = "nickname can not be empty")
    @Length(min = 3, max = 8, message ="The nickname length should be between 3 and 8" )
    private String nickname;

    @NotBlank(message = "phone number can not be empty")
    @Pattern(regexp = "^1[3-9]\\d{9}$")
    private String phone;

    @Email(message = "email format is error")
    private String email;

    @NotBlank(message = "password can not be empty")
    private String password;

    @NotNull(message = "verification code can not be null")
    private String verificationCode;

    @NotBlank(message = "uuid can not be empty")
    private String uuid;
}
