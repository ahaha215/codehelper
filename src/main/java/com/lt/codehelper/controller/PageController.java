package com.lt.codehelper.controller;

import com.lt.codehelper.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


/**
 * @ClassName PageController
 * @Description 负责控制页面的跳转
 * @Author lt
 * @Version 1.0
 **/
@Controller
public class PageController {

    /*--------------------------------------- 前台用户页面跳转 -----------------------------------------*/

    /**
     * @MethodName gotoWelcome
     * @Description 页面跳转到欢迎页面
     * @Author lt
     * @Param [model, session]
     * @return java.lang.String
     **/
    @RequestMapping({"/","/welcome"})
    public String gotoWelcome(HttpSession session, Model model){
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "welcome";
    }

    /**
     * @MethodName gotoLogin
     * @Description 页面跳转到登录页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/login")
    public String gotoLogin(){
        return "login";
    }

    /**
     * @MethodName gotoPrivacy 
     * @Description 页面跳转到隐私政策页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/privacy")
    public String gotoPrivacy(){
        return "privacy";
    }

    /**
     * @MethodName gotoUserAgreement
     * @Description 页面跳转到用户协议页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/userAgreement")
    public String gotoUserAgreement(){
        return "user-agreement";
    }


    /**
     * @MethodName gotoRegister
     * @Description 页面跳转到注册页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/register")
    public String gotoRegister(){
        return "register";
    }

    /**
     * @MethodName gotoResetPassword
     * @Description 页面跳转到密码重置页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/resetPassword")
    public String gotoResetPassword(){
        return "reset-password";
    }

    /**
     * @MethodName gotoIndex
     * @Description 页面跳转到首页
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/index")
    public String gotoIndex(){
        return "forward:/indexPage.do";
    }

    /**
     * @MethodName gotoBlog
     * @Description 页面跳转到博客
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/blog")
    public String gotoBlog(){
        return "forward:/blogPage.do";
    }

    /**
     * @MethodName gotoBlogDetail
     * @Description 查看博客详情
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/blogDetail")
    public String gotoBlogDetail(){
        return "forward:/blogDetail.do";
    }

    /**
     * @MethodName gotoResource
     * @Description 页面跳转到前台资源页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/resource")
    public String gotoResource(){
        return "forward:/resourcePage.do";
    }

    /**
     * @MethodName gotoResourceRecommend
     * @Description 页面跳转资源推荐
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/resourceRecommend")
    public String gotoResourceRecommend(){
        return "forward:/recommendResourceTypeDataRender.do";
    }

    /**
     * @MethodName resourceDetail
     * @Description 查看资源详情
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/resourceDetail")
    public String gotoResourceDetail(){
        return "forward:/resourceDetail.do";
    }

    /**
     * @MethodName gotoWish
     * @Description 页面跳转到心愿树页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/wish")
    public String gotoWish(){
        return "forward:/wishPage.do";
    }

    /**
     * @MethodName gotoWishDetail
     * @Description 页面跳转到心愿详情页
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/wishDetail")
    public String gotoWishDetail(){
        return "forward:/wishDetail.do";
    }

    /**
     * @MethodName gotoMakeWish
     * @Description TODO
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/makeWish")
    public String gotoMakeWish(){
        return "forward:/makeWishTypeDataRender.do";
    }

    /**
     * @MethodName gotoPartner
     * @Description 页面跳转到找伙伴页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/partner")
    public String gotoPartner(){
        return "forward:/partnerPage.do";
    }

    /**
     * @MethodName gotoFindPartner
     * @Description 页面跳转到寻找伙伴页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/findPartner")
    public String gotoFindPartner(){
        return "forward:/findPartnerTypeDataRender.do";
    }

    /**
     * @MethodName gotoAboutWebMaster
     * @Description 页面跳转到关于站长页面
     * @Author lt
     * @Param [model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/aboutWebMaster")
    public String gotoAboutWebMaster(Model model, HttpSession session){
        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "about-web-master";
    }

    /*--------------------------------------- 后台管理页面跳转 -----------------------------------------*/

    /**
     * @MethodName gotoAdminIndex
     * @Description 页面跳转到管理员首页
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminIndex")
    public String gotoAdminIndex(){
        return "forward:/adminIndex.do";
    }

    /**
     * @MethodName gotoAdminUser
     * @Description 页面跳转到管理员用户列表页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminUser")
    public String gotoAdminUser(){
        return "forward:/userList.do";
    }

    /**
     * @MethodName gotoAdminUserAdd
     * @Description 页面跳转到管理员用户添加页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminUserAdd")
    public String gotoAdminUserAdd(){
        return "admin/admin-user-add";
    }

    /**
     * @MethodName gotoAdminUserUpdate
     * @Description 页面跳转到管理员用户信息修改页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminUserUpdate")
    public String gotoAdminUserUpdate(){
        return "forward:/userDataEcho.do";
    }

    /**
     * @MethodName gotoAdminType
     * @Description 页面跳转到管理员类型列表页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminType")
    public String gotoAdminType(){
        return "forward:/typeList.do";
    }

    /**
     * @MethodName gotoAdminTypeAdd
     * @Description 页面跳转到管理员类型添加页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminTypeAdd")
    public String gotoAdminTypeAdd(){
        return "admin/admin-type-add";
    }

    /**
     * @MethodName gotoAdminBlog
     * @Description 页面跳转到管理员博客列表页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminBlog")
    public String gotoAdminBlog(){
        return "forward:/blogList.do";
    }

    /**
     * @MethodName gotoAdminBlogAdd
     * @Description 页面跳转到管理员博客添加页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminBlogAdd")
    public String gotoAdminBlogAdd(){
        return "forward:/addBlogTypeDataRender.do";
    }

    /**
     * @MethodName gotoAdminBlogUpdate
     * @Description 页面跳转到管理员博客修改页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminBlogUpdate")
    public String gotoAdminBlogUpdate(){
        return "forward:/blogDataEcho.do";
    }

    /**
     * @MethodName gotoResource
     * @Description 页面跳转到资源
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminResource")
    public String gotoAdminResource(){
        return "forward:/resourceList.do";
    }

    /**
     * @MethodName gotoResource
     * @Description 页面跳转到待审核资源列表
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminPendingAuditResource")
    public String gotoAdminPendingAuditResource(){
        return "forward:/pendingAuditResourceList.do";
    }

    /**
     * @MethodName gotoResourceRecommend
     * @Description 页面跳转资源上传
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminReleaseResource")
    public String gotoAdminReleaseRecommend(){
        return "forward:/releaseResourceTypeDataRender.do";
    }

    /**
     * @MethodName gotoAdminWish
     * @Description 页面跳转到心愿页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminWish")
    public String gotoAdminWish(){
        return "forward:/wishList.do";
    }

    /**
     * @MethodName gotoAdminWish
     * @Description 页面跳转到未审核心愿页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminPendingAuditWish")
    public String gotoAdminPendingWish(){
        return "forward:/wishPendingAuditList.do";
    }

    /**
     * @MethodName gotoAdminPartner
     * @Description 页面跳转伙伴页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminPartner")
    public String gotoAdminPartner(){
        return "forward:/partnerList.do";
    }

    /**
     * @MethodName gotoAdminPendingPartner
     * @Description 页面跳转到未审核伙伴页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminPendingAuditPartner")
    public String gotoAdminPendingPartner(){
        return "forward:/partnerPendingAuditList.do";
    }

    /**
     * @MethodName gotoAdminWebSiteBanner
     * @Description 页面跳转到轮播图页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminWebSiteBanner")
    public String gotoAdminWebSiteBanner(){
        return "forward:/webSiteBannerList.do";
    }

    /**
     * @MethodName gotoAdminWebSiteGoodRecommend
     * @Description 页面跳转到推荐页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/adminWebSiteGoodRecommend")
    public String gotoAdminWebSiteGoodRecommend(){
        return "forward:/webSiteGoodRecommendList.do";
    }

    /**
     * @MethodName gotoAboutUserResource
     * @Description 页面跳转到用户资源页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/aboutUserResource")
    public String gotoAboutUserResource(){
        return "forward:/aboutUserResource.do";
    }

    /**
     * @MethodName gotoAboutUserResource
     * @Description 页面跳转到用户心愿页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/aboutUserWish")
    public String gotoAboutUserWish(){
        return "forward:/aboutUserWishPage.do";
    }

    /**
     * @MethodName gotoAboutUserResource
     * @Description 页面跳转到用户伙伴页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/aboutUserPartner")
    public String gotoAboutUserPartner(){
        return "forward:/aboutUserPartnerPage.do";
    }

    /**
     * @MethodName gotoAboutUserInfo
     * @Description 页面跳转到用户个人信息页面
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/aboutUserInfo")
    public String gotoAboutUserInfo(){
        return "forward:/aboutUserInfo.do";
    }
}
