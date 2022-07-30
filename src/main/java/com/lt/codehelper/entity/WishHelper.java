package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName WishHelper
 * @Description 心愿满足实现类
 * @Author lt
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WishHelper {
    // 满足心愿id
    private Long id;
    // 满足资源的名称
    private String name;
    // 满足心愿内容
    private String content;
    // 满足心愿创建时间
    private Date createTime;
    // 满足的心愿
    private Wish wish;
    // 满足心愿用户
    private User user;
}
