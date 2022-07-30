package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName BlogComment
 * @Description 博客评论的实体类
 * @Author lt
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BlogComment {
    // 评论的id
    private Long id;
    // 评论内容
    private String content;
    // 评论创建时间
    private Date createTime;
    // 评论的博客id
    private Long blogId;
    // 评论的用户id
    private Long userId;
}
