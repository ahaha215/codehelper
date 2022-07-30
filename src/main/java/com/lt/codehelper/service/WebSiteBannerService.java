package com.lt.codehelper.service;

import com.lt.codehelper.entity.WebSiteBanner;

import java.util.List;
import java.util.Map;

public interface WebSiteBannerService {
    boolean addWebSiteBanner(Map webSiteBannerMap);
    boolean removeWebSiteBanner(Long webSiteBannerId);
    List<WebSiteBanner> findAllWebSiteBanner();
    WebSiteBanner findWebSiteBanner(Long webSiteBannerId);
}
