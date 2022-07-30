package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @ClassName Wish
 * @Description 心愿实体类
 * @Author lt
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Wish {
    // 心愿id
    Long id;
    // 心愿内容
    String content;
    // 心愿审核状态
    String audit;
    // 心愿发布时间
    Date createTime;
    // 心愿发布用户
    User user;
    // 心愿类型
    List<Type> wishTypes;
}
