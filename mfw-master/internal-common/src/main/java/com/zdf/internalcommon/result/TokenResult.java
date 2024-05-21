package com.zdf.internalcommon.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *@Description Packaging token result
 *@Author mrzhang
 *@Date 2024/3/25 02:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenResult {
    private String userName;
    private String userPassword;
}