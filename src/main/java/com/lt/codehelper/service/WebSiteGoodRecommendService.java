package com.lt.codehelper.service;

import com.lt.codehelper.entity.WebSiteGoodRecommend;

import java.util.List;
import java.util.Map;

public interface WebSiteGoodRecommendService {
    boolean addWebSiteGoodRecommend(Map webSiteGoodRecommendMap);
    boolean removeWebSiteGoodRecommend(Long webSiteGoodRecommendId);
    List<WebSiteGoodRecommend> findAllWebSiteGoodRecommend();
    WebSiteGoodRecommend findWebSiteGoodRecommend(Long webSiteGoodRecommendId);
}
