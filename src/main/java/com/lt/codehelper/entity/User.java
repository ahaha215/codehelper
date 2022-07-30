package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName User
 * @Description 用户实体类
 * @Author lt
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    // 用户id
    private Long id;
    // 用户名
    private String username;
    // 密码
    private String password;
    // QQ邮箱
    private String email;
    // 用户角色
    private String userType;
    // 用户头像
    private String headPortrait;
    // 用户创建时间
    private Date createTime;
    // 用户信息更新时间
    private Date updateTime;
}
