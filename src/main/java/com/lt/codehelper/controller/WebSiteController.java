package com.lt.codehelper.controller;

import com.lt.codehelper.entity.User;
import com.lt.codehelper.entity.WebSiteBanner;
import com.lt.codehelper.entity.WebSiteGoodRecommend;
import com.lt.codehelper.service.WebSiteBannerService;
import com.lt.codehelper.service.WebSiteGoodRecommendService;
import com.lt.codehelper.util.OssUtils;
import com.lt.codehelper.util.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName WebSiteController
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/

@Controller
public class WebSiteController {

    // 注入 webSiteBannerService
    @Autowired
    WebSiteBannerService webSiteBannerService;

    // 注入 webSiteGoodRecommendService
    @Autowired
    WebSiteGoodRecommendService webSiteGoodRecommendService;

    /**
     * @MethodName actionAddWebSiteBanner
     * @Description 上传轮播图
     * @Author lt
     * @Param [webSiteBanner, bannerPicture, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/addWebSiteBanner.do")
    public String actionAddWebSiteBanner(WebSiteBanner webSiteBanner, MultipartFile bannerPicture, Model model, HttpSession session) throws Exception {
        // 判断数据不为空
        if (webSiteBanner.getLinkAddress() == null || webSiteBanner.getLinkAddress().length() == 0 ||
                bannerPicture.isEmpty()){
            model.addAttribute("msg","轮播图信息不能为空！");
        } else {
            // 获取当前登录用户
            User loginUser = (User) session.getAttribute("loginUser");
            Map bannerMap = new HashMap();
            webSiteBanner.setId(SnowflakeIdUtils.nextId());
            bannerMap.put("banner",webSiteBanner);
            bannerMap.put("userId",loginUser.getId());
            // 博客首图上传
            if(!bannerPicture.isEmpty()){
                String iconFileOriginalFilename = bannerPicture.getOriginalFilename();
                String objectName = "web-site/banner/" + webSiteBanner.getId() + iconFileOriginalFilename.substring(iconFileOriginalFilename.lastIndexOf("."));
                String ossFileUrl = OssUtils.upload(bannerPicture, objectName);
                webSiteBanner.setPictureAddress(ossFileUrl);
            }

            boolean flag = webSiteBannerService.addWebSiteBanner(bannerMap);

            if (!flag){
                model.addAttribute("msg","对不起，轮播图上传失败，请重试！");
            }
        }
        return "redirect:/adminWebSiteBanner";
    }

    /**
     * @MethodName actionRemoveWebSiteBanner
     * @Description 删除轮播图
     * @Author lt
     * @Param [webSiteBannerId, model]
     * @return java.lang.String
     **/
    @RequestMapping("/removeWebSiteBanner.do")
    public String actionRemoveWebSiteBanner(Long webSiteBannerId, Model model){
        // 暂时保存待删除的博客信息
        WebSiteBanner deletedWebSiteBanner = webSiteBannerService.findWebSiteBanner(webSiteBannerId);
        // 修改博客首图
        String[] strings = deletedWebSiteBanner.getPictureAddress().split("/");
        String str = strings[strings.length - 1];
        String objectName = "web-site/banner/" + str;
        OssUtils.delete(objectName);

        // 删除轮播图信息
        boolean deleteWebSiteBannerFlag = webSiteBannerService.removeWebSiteBanner(webSiteBannerId);
        if (deleteWebSiteBannerFlag){
            model.addAttribute("msg","删除成功！");
        } else {
            model.addAttribute("msg","删除失败！");
        }
        return "redirect:/adminWebSiteBanner";
    }

    /**
     * @MethodName actionListWebSiteBanner
     * @Description 查询所有轮播图
     * @Author lt
     * @Param [model]
     * @return java.lang.String
     **/
    @RequestMapping("/webSiteBannerList.do")
    public String actionWebSiteBannerList(Model model){
        List<WebSiteBanner> allWebSiteBanner = webSiteBannerService.findAllWebSiteBanner();
        model.addAttribute("allWebSiteBanners",allWebSiteBanner);
        return "admin/admin-web-site-banner";
    }

    /**
     * @MethodName actionAddWebSiteGoodRecommend
     * @Description 上传推荐
     * @Author lt
     * @Param [webSiteBanner, bannerPicture, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/addWebSiteGoodRecommend.do")
    public String actionAddWebSiteGoodRecommend(WebSiteGoodRecommend webSiteBGoodRecommend, MultipartFile goodRecommendPicture, Model model, HttpSession session) throws Exception {
        // 判断数据不为空
        if (webSiteBGoodRecommend.getLinkAddress() == null || webSiteBGoodRecommend.getLinkAddress().length() == 0 ||
                goodRecommendPicture.isEmpty()){
            model.addAttribute("msg","推荐信息不能为空！");
        } else {
            // 获取当前登录用户
            User loginUser = (User) session.getAttribute("loginUser");
            Map goodRecommendMap = new HashMap();
            webSiteBGoodRecommend.setId(SnowflakeIdUtils.nextId());
            goodRecommendMap.put("goodRecommend",webSiteBGoodRecommend);
            goodRecommendMap.put("userId",loginUser.getId());
            // 博客首图上传
            if(!goodRecommendPicture.isEmpty()){
                String iconFileOriginalFilename = goodRecommendPicture.getOriginalFilename();
                String objectName = "web-site/good-recommend/" + webSiteBGoodRecommend.getId() + iconFileOriginalFilename.substring(iconFileOriginalFilename.lastIndexOf("."));
                String ossFileUrl = OssUtils.upload(goodRecommendPicture, objectName);
                webSiteBGoodRecommend.setPictureAddress(ossFileUrl);
            }

            boolean flag = webSiteGoodRecommendService.addWebSiteGoodRecommend(goodRecommendMap);

            if (!flag){
                model.addAttribute("msg","对不起，推荐上传失败，请重试！");
            }
        }
        return "redirect:/adminWebSiteGoodRecommend";
    }

    /**
     * @MethodName actionRemoveWebSiteGoodRecommend
     * @Description 删除推荐
     * @Author lt
     * @Param [webSiteBannerId, model]
     * @return java.lang.String
     **/
    @RequestMapping("/removeWebSiteGoodRecommend.do")
    public String actionRemoveWebSiteGoodRecommend(Long webSiteGoodRecommendId, Model model){
        // 暂时保存待删除的博客信息
        WebSiteGoodRecommend deletedWebSiteGoodRecommend = webSiteGoodRecommendService.findWebSiteGoodRecommend(webSiteGoodRecommendId);
        // 修改博客首图
        String[] strings = deletedWebSiteGoodRecommend.getPictureAddress().split("/");
        String str = strings[strings.length - 1];
        String objectName = "web-site/good-recommend/" + str;
        OssUtils.delete(objectName);

        // 删除轮播图信息
        boolean deleteWebSiteGoodRecommendFlag = webSiteGoodRecommendService.removeWebSiteGoodRecommend(webSiteGoodRecommendId);
        if (deleteWebSiteGoodRecommendFlag){
            model.addAttribute("msg","删除成功！");
        } else {
            model.addAttribute("msg","删除失败！");
        }
        return "redirect:/adminWebSiteGoodRecommend";
    }

    /**
     * @MethodName actionWebSiteGoodRecommendList
     * @Description 查询所有推荐
     * @Author lt
     * @Param [model]
     * @return java.lang.String
     **/
    @RequestMapping("/webSiteGoodRecommendList.do")
    public String actionWebSiteGoodRecommendList(Model model){
        List<WebSiteGoodRecommend> allWebSiteGoodRecommend = webSiteGoodRecommendService.findAllWebSiteGoodRecommend();
        model.addAttribute("allWebSiteGoodRecommends",allWebSiteGoodRecommend);
        return "admin/admin-web-site-good-recommend";
    }
}
