package com.lt.codehelper.service.impl;

import com.lt.codehelper.entity.WebSiteBanner;
import com.lt.codehelper.mapper.WebSiteBannerMapper;
import com.lt.codehelper.service.WebSiteBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName WebSiteBannerServiceImpl
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@Service
public class WebSiteBannerServiceImpl implements WebSiteBannerService {

    // 注入 webSiteBannerMapper
    @Autowired
    WebSiteBannerMapper webSiteBannerMapper;

    /**
     * @MethodName addWebSiteBanner
     * @Description 添加轮播图
     * @Author lt
     * @Param [webSiteBannerMap]
     * @return boolean
     **/
    @Override
    public boolean addWebSiteBanner(Map webSiteBannerMap) {
        boolean flag = webSiteBannerMapper.insertWebSiteBanner(webSiteBannerMap);
        return flag;
    }

    /**
     * @MethodName addWebSiteBanner
     * @Description 删除轮播图
     * @Author lt
     * @Param [webSiteBannerId]
     * @return boolean
     **/
    @Override
    public boolean removeWebSiteBanner(Long webSiteBannerId) {
        boolean flag = webSiteBannerMapper.deleteWebSiteBanner(webSiteBannerId);
        return flag;
    }

    /**
     * @MethodName addWebSiteBanner
     * @Description 查询轮播图
     * @Author lt
     * @Param []
     * @return boolean
     **/
    @Override
    public List<WebSiteBanner> findAllWebSiteBanner() {
        List<WebSiteBanner> webSiteBanners = webSiteBannerMapper.selectAllWebSiteBanner();
        return webSiteBanners;
    }

    /**
     * @MethodName findWebSiteBanner
     * @Description 按照id查询轮播图
     * @Author lt
     * @Param [webSiteBannerId]
     * @return com.lt.codehelper.entity.WebSiteBanner
     **/
    @Override
    public WebSiteBanner findWebSiteBanner(Long webSiteBannerId) {
        WebSiteBanner webSiteBanner = webSiteBannerMapper.selectWebSiteBanner(webSiteBannerId);
        return webSiteBanner;
    }
}
