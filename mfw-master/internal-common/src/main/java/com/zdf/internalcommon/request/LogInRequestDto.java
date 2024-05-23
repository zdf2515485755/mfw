package com.zdf.internalcommon.request;

import com.zdf.internalcommon.constant.UserConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 *@Description LogIn Request Dto
 *@Author mrzhang
 *@Date 2024/5/22 20:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequestDto {

    @NotBlank(message = "phone number can not be empty")
    @Pattern(regexp = UserConstant.PHONE_PATTERN)
    @Length(max = 11)
    private String phone;

    @NotBlank(message = "password can not be empty")
    private String password;
}
