package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @ClassName Resource
 * @Description 编程资源实体类
 * @Author lt
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Resource {
    // 资源id
    private Long id;
    // 资源名称
    private String resourceName;
    // 资源描述
    private String description;
    // 资源链接地址
    private String linkAddress;
    // 资源详情
    private String detail;
    // 资源图标
    private String icon;
    // 资源审核标识
    private String audit;
    // 资源创建时间
    private Date createTime;
    // 资源更新时间
    private Date updateTime;
    // 资源浏览次数
    private Integer viewSum;
    // 资源发布用户
    private User user;
    // 资源类型
    private List<String> resourceTypes;
}
