package com.lt.codehelper.controller;

import com.lt.codehelper.entity.*;
import com.lt.codehelper.service.*;
import com.lt.codehelper.util.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.*;

/**
 * @ClassName IndexController
 * @Description 首页控制器
 * @Author lt
 * @Version 1.0
 **/
@Controller
public class IndexController {

    // 注入blogService
    @Autowired
    BlogService blogService;

    // 注入userService
    @Autowired
    UserService userService;

    // 注入 resourceService
    @Autowired
    ResourceService resourceService;

    // 注入 webSiteBannerService
    @Autowired
    WebSiteBannerService webSiteBannerService;

    // 注入 webSiteGoodRecommendService
    @Autowired
    WebSiteGoodRecommendService webSiteGoodRecommendService;

    /**
     * @MethodName actionIndexList
     * @Description 展示首页数据
     * @Author lt
     * @Param [size, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/indexPage.do")
    public String actionIndexList(@RequestParam(value="size",defaultValue="10")int size, Model model, HttpSession session) throws ParseException {

        List<Map> newBlogList = new ArrayList<>();
        // 最新博客信息
        List<Blog> newBlogs = blogService.findNewBlog(size);
        // 存放博客信息以及作者信息
        for (Blog newBlog : newBlogs) {
            Map blogInfo = new HashMap();
            blogInfo.put("blog",newBlog);
            // 转换时间格式
            String updateTimeStr = FormatUtils.formatTime(newBlog.getUpdateTime());
            blogInfo.put("updateTimeStr",updateTimeStr);
            String author = userService.findUserById(newBlog.getUserId()).getUsername();
            blogInfo.put("author",author);
            String headPortrait = userService.findUserById(newBlog.getUserId()).getHeadPortrait();
            blogInfo.put("headPortrait",headPortrait);
            newBlogList.add(blogInfo);
        }
        model.addAttribute("newBlogList",newBlogList);

        // 资源信息
        List<Map> newResourceList = new ArrayList<>();
        List<Resource> newResources = resourceService.findNewResource(size);
        for (Resource newResource : newResources) {
            Map resourceInfo = new HashMap();
            resourceInfo.put("resource",newResource);
            // 格式化时间
            resourceInfo.put("createTimStr",FormatUtils.formatTime(newResource.getCreateTime()));
            newResourceList.add(resourceInfo);
        }
        model.addAttribute("newResourceList",newResourceList);

        // 轮播图信息
        List<WebSiteBanner> allWebSiteBanner = webSiteBannerService.findAllWebSiteBanner();
        model.addAttribute("allWebSiteBanner",allWebSiteBanner);

        // 好的推荐信息
        List<WebSiteGoodRecommend> allWebSiteGoodRecommend = webSiteGoodRecommendService.findAllWebSiteGoodRecommend();
        model.addAttribute("allWebSiteGoodRecommend",allWebSiteGoodRecommend);

        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);

        return "index";
    }

    /**
     * @MethodName actionAdminIndex
     * @Description 后台管理首页数据渲染
     * @Author lt
     * @Param [session, model]
     * @return java.lang.String
     **/
    @RequestMapping("/adminIndex.do")
    public String actionAdminIndex(HttpSession session, Model model){
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("username",loginUser.getUsername());
        return "admin/admin-index";
    }
}