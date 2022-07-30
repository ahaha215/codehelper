package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName WebSiteBanner
 * @Description 站点推荐实体类
 * @Author lt
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WebSiteGoodRecommend {
    // 站点推荐id
    private Long id;
    // 站点推荐链接地址
    private String  linkAddress;
    // 站点推荐图片资源
    private String  pictureAddress;
    // 上传用户
    private User user;
}
