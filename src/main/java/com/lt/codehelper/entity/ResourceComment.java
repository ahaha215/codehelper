package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName ResourceComment
 * @Description 资源评价实体类
 * @Author lt
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceComment {
    // 资源评论id
    private Long id;
    // 资源评论内同
    private String content;
    // 资源评论时间
    private Date createTime;
    // 评论的资源
    private Resource resource;
    // 评论用户
    private User user;
}
