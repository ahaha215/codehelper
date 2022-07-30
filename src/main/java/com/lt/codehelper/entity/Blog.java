package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @ClassName Blog
 * @Description 博客实体类
 * @Author lt
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Blog {
    // 博客id
    private Long id;
    // 博客名称
    private String title;
    // 博客描述
    private String description;
    // 博客内容
    private String content;
    // 博客首图
    private String firstPicture;
    // 博客标识
    private String flag;
    // 博客查看人数
    private Integer viewSum;
    // 博客发布标识
    private String published;
    // 博客创建时间
    private Date createTime;
    // 博客更新时间
    private Date updateTime;
    // 博客用户id
    private Long userId;
    // 博客类型
    private List<String> blogTypes;
}
