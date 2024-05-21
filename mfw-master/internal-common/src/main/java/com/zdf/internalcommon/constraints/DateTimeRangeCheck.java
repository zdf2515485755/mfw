package com.zdf.internalcommon.constraints;

import javax.validation.Payload;

/**
 * @Description Datetime range check
 * @Author mrzhang
 * @Date 2024/3/18 19:44
 */
public @interface DateTimeRangeCheck {
    String pattern() default "yyyy-MM-dd MM:mm:ss";

    String message() default "输入正确的日期";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}