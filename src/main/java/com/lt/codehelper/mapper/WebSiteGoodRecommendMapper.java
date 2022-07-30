package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.WebSiteGoodRecommend;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WebSiteGoodRecommendMapper {
    // 插入轮播图
    boolean insertWebSiteGoodRecommend(Map webSiteGoodRecommendMap);
    // 删除轮播图
    boolean deleteWebSiteGoodRecommend(Long webSiteGoodRecommendId);
    // 查询所有轮播图
    List<WebSiteGoodRecommend> selectAllWebSiteGoodRecommend();
    // 按照id查询
    WebSiteGoodRecommend selectWebSiteGoodRecommend(Long webSiteGoodRecommendId);
}
