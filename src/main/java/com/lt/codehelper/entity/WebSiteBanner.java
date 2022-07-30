package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName WebSiteBanner
 * @Description 站点轮播图实体类
 * @Author lt
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WebSiteBanner {
    // 轮播图id
    private Long id;
    // 轮播图链接地址
    private String  linkAddress;
    // 轮播图图片资源
    private String  pictureAddress;
    // 上传用户
    private User user;
}
