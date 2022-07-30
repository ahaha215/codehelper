package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.WebSiteBanner;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WebSiteBannerMapper {
    // 插入轮播图
    boolean insertWebSiteBanner(Map webSiteBannerMap);
    // 删除轮播图
    boolean deleteWebSiteBanner(Long webSiteBannerId);
    // 查询所有轮播图
    List<WebSiteBanner> selectAllWebSiteBanner();
    // 按照id查询
    WebSiteBanner selectWebSiteBanner(Long webSiteBannerId);
}
