package com.zdf.internalcommon.util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

/**
 *@Description Jpa utils class,Implemented dynamic updates
 *@Author mrzhang
 *@Date 2024/3/17 18:48
 */
public class JpaUtil {
    public static void copyNotNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private JpaUtil() {}

    public static String[] getNullPropertyNames(Object object) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        return Stream.of(beanWrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propertyName ->
                        beanWrapper.getPropertyValue(propertyName) == "" || beanWrapper.getPropertyValue(propertyName) == null
                )
                .toArray(String[]::new);
    }

    public static String[] getNotNullPropertyNames(Object object) {
        final BeanWrapper beanWrapper = new BeanWrapperImpl(object);
        return Stream.of(beanWrapper.getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .filter(propertyName ->
                        beanWrapper.getPropertyValue(propertyName) != "" && beanWrapper.getPropertyValue(propertyName) != null)
                .toArray(String[]::new);
    }
}