package com.zdf.internalcommon.util;


/**
 *@Description ThreadLocalUtil
 *@Author mrzhang
 *@Date 2024/4/30 23:50
 */
public class ThreadLocalUtil{

    private static final ThreadLocal<Integer> THREAD_LOCAL = new ThreadLocal<Integer>() {};

    private ThreadLocalUtil(){}

    public static void set(Integer value) {
        THREAD_LOCAL.set(value);
    }

    public static Integer get() {
        return THREAD_LOCAL.get();
    }
    public static void clear() {
        THREAD_LOCAL.remove();
    }
}
