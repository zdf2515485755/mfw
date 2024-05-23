package com.zdf.internalcommon.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@Description VerificationCode Response Dto
 *@Author mrzhang
 *@Date 2024/5/21 21:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerificationCodeResponseDto {
    private String uuid;
    private String image;
}
