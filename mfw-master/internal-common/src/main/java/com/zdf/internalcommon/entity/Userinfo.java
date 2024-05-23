package com.zdf.internalcommon.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @author mrzhang
 * @TableName userinfo
 */
@TableName(value ="userinfo")
@Data
public class Userinfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String nickname;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private Integer level;

    /**
     * 
     */
    private String city;

    /**
     * 
     */
    private String headImgUrl;

    /**
     * 
     */
    private String info;

    /**
     * 
     */
    private Integer state;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}