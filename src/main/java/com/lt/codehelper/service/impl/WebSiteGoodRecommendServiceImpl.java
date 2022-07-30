package com.lt.codehelper.service.impl;

import com.lt.codehelper.entity.WebSiteGoodRecommend;
import com.lt.codehelper.mapper.WebSiteGoodRecommendMapper;
import com.lt.codehelper.service.WebSiteGoodRecommendService;
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
public class WebSiteGoodRecommendServiceImpl implements WebSiteGoodRecommendService {

    // 注入 webSiteGoodRecommendMapper
    @Autowired
    WebSiteGoodRecommendMapper webSiteGoodRecommendMapper;

    /**
     * @MethodName addWebSiteGoodRecommend
     * @Description 添加推荐
     * @Author lt
     * @Param [webSiteGoodRecommendMap]
     * @return boolean
     **/
    @Override
    public boolean addWebSiteGoodRecommend(Map webSiteGoodRecommendMap) {
        boolean flag = webSiteGoodRecommendMapper.insertWebSiteGoodRecommend(webSiteGoodRecommendMap);
        return flag;
    }

    /**
     * @MethodName removeWebSiteGoodRecommend
     * @Description 删除推荐
     * @Author lt
     * @Param [webSiteGoodRecommendId]
     * @return boolean
     **/
    @Override
    public boolean removeWebSiteGoodRecommend(Long webSiteGoodRecommendId) {
        boolean flag = webSiteGoodRecommendMapper.deleteWebSiteGoodRecommend(webSiteGoodRecommendId);
        return flag;
    }

    /**
     * @MethodName findAllWebSiteGoodRecommend
     * @Description 查询推荐
     * @Author lt
     * @Param []
     * @return boolean
     **/
    @Override
    public List<WebSiteGoodRecommend> findAllWebSiteGoodRecommend() {
        List<WebSiteGoodRecommend> webSiteGoodRecommends = webSiteGoodRecommendMapper.selectAllWebSiteGoodRecommend();
        return webSiteGoodRecommends;
    }

    /**
     * @MethodName findWebSiteGoodRecommend
     * @Description 按照id查询推荐
     * @Author lt
     * @Param [webSiteGoodRecommendId]
     * @return com.lt.codehelper.entity.WebSiteGoodRecommend
     **/
    @Override
    public WebSiteGoodRecommend findWebSiteGoodRecommend(Long webSiteGoodRecommendId) {
        WebSiteGoodRecommend webSiteGoodRecommend = webSiteGoodRecommendMapper.selectWebSiteGoodRecommend(webSiteGoodRecommendId);
        return webSiteGoodRecommend;
    }
}
