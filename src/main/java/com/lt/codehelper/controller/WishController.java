package com.lt.codehelper.controller;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Type;
import com.lt.codehelper.entity.User;
import com.lt.codehelper.entity.Wish;
import com.lt.codehelper.entity.WishHelper;
import com.lt.codehelper.service.TypeService;
import com.lt.codehelper.service.WishService;
import com.lt.codehelper.util.FormatUtils;
import com.lt.codehelper.util.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ClassName WishController
 * @Description 心愿控制层
 * @Author lt
 * @Version 1.0
 **/
@Controller
public class WishController {

    // 注入 wishService
    @Autowired
    WishService wishService;

    // 注入 TypeService
    @Autowired
    TypeService typeService;

    /**
     * @MethodName actionMakeWishTypeDataRender
     * @Description 心愿类型数据回显
     * @Author lt
     * @Param [model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/makeWishTypeDataRender.do")
    public String actionMakeWishTypeDataRender(Model model, HttpSession session){
        List<Type> allType = typeService.findAllType();
        model.addAttribute("allType",allType);
        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "wish-make";
    }

    /**
     * @MethodName actionMakeWish
     * @Description 许愿
     * @Author lt
     * @Param [wish, wishTypeNames, session, model]
     * @return java.lang.String
     **/
    @RequestMapping("/makeWish.do")
    public String actionMakeWish(Wish wish, String[] wishTypeNames, HttpSession session, Model model){
        // 校验前端数据
        if (wish.getContent() == null || wish.getContent().length() == 0 ||
                wishTypeNames == null || wishTypeNames.length == 0){
            model.addAttribute("msg","对不起，所填信息不能为空！");
            return "forward:/makeWish";
        }
        // 封装 wish
        wish.setId(SnowflakeIdUtils.nextId());
        wish.setAudit("待审核");
        wish.setCreateTime(new Date());

        Map wishMap = new HashMap();
        wishMap.put("wish",wish);
        // 获取当前用户
        User loginUser = (User) session.getAttribute("loginUser");
        wishMap.put("userId",loginUser.getId());
        wishMap.put("wishTypeNames",wishTypeNames);

        wishService.addWish(wishMap);

        return "redirect:/wish";
    }

    /**
     * @MethodName actionRemoveWish
     * @Description 删除心愿
     * @Author lt
     * @Param [wishId, model]
     * @return java.lang.String
     **/
    @RequestMapping("/removeWish.do")
    public String actionRemoveWish(Long wishId, Model model){
        boolean flag = wishService.removeWish(wishId);
        if (flag){
            model.addAttribute("msg","删除成功！");
        } else {
            model.addAttribute("msg","删除失败！");
        }
        return "forward:/adminWish";
    }

    /**
     * @MethodName actionWishList
     * @Description 分页查询心愿信息
     * @Author lt
     * @Param [pageNum, pageSize, model]
     * @return java.lang.String
     **/
    @RequestMapping("/wishList.do")
    public String actionWishList(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model){
        // 分页查询资源信息
        PageInfo<Wish> wishByPage = wishService.findWishByPage(pageNum,pageSize);
        model.addAttribute("pageWish",wishByPage);
        return "admin/admin-wish";
    }

    /**
     * @MethodName actionWishPendingAuditList
     * @Description 分页查询待审核心愿信息
     * @Author lt
     * @Param [pageNum, pageSize, model]
     * @return java.lang.String
     **/
    @RequestMapping("/wishPendingAuditList.do")
    public String actionWishPendingAuditList(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model){
        // 分页查询资源信息
        PageInfo<Wish> wishByPage = wishService.findPendingAuditWishByPage(pageNum,pageSize);
        model.addAttribute("pageWish",wishByPage);
        return "admin/admin-wish-pending-audit";
    }

    /**
     * @MethodName actionAuditWishDetail
     * @Description 资源审核详情
     * @Author lt
     * @Param [wishId, model]
     * @return java.lang.String
     **/
    @RequestMapping("/auditWishDetail.do")
    public String actionAuditWishDetail(Long wishId, Model model){
        Wish wishById = wishService.findWishById(wishId);
        model.addAttribute("wishInfo",wishById);
        // 格式化时间
        Date createTime = wishById.getCreateTime();
        String formatTimeStr = FormatUtils.formatTime(createTime);
        model.addAttribute("formatTimeStr",formatTimeStr);
        return "admin/admin-wish-audit";
    }

    /**
     * @MethodName actionAuditWish
     * @Description 审核心愿
     * @Author lt
     * @Param [wishId, audit]
     * @return java.lang.String
     **/
    @RequestMapping("auditWish.do")
    public String actionAuditWish(Long wishId, String audit){
        wishService.auditWish(wishId, audit);
        return "redirect:/adminWish";
    }

    /**
     * @MethodName actionWishPage
     * @Description 分页查询审核通过心愿信息
     * @Author lt
     * @Param [pageNum, pageSize, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/wishPage.do")
    public String actionWishPage(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="15")int pageSize, Model model, HttpSession session){
        // 分页查询资源信息
        PageInfo<Wish> wishByPage = wishService.findPassAuditWishByPage(pageNum, pageSize);
        model.addAttribute("pageWish",wishByPage);
        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "wish";
    }

    /**
     * @MethodName actionSearchWishPage
     * @Description 搜索心愿
     * @Author lt
     * @Param [wishContentLike, pageNum, pageSize, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/searchWishPage.do")
    public String actionSearchWishPage(String wishContentLike, @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model, HttpSession session){
        // 分页查询资源信息
        PageInfo<Wish> wishByPage = wishService.findWishByContentLike(wishContentLike,pageNum,pageSize);
        model.addAttribute("pageWish",wishByPage);
        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "wish";
    }

    /**
     * @MethodName actionAuditWishDetail
     * @Description 资源详情
     * @Author lt
     * @Param [wishId, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/wishDetail.do")
    public String actionWishDetail(Long wishId,@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="15")int pageSize, Model model, HttpSession session){
        Wish wishById = wishService.findWishById(wishId);
        model.addAttribute("wishInfo",wishById);
        // 格式化时间
        Date createTime = wishById.getCreateTime();
        String formatTimeStr = FormatUtils.formatTime(createTime);
        model.addAttribute("formatTimeStr",formatTimeStr);

        // 分页展示帮助信息
        PageInfo<WishHelper> wishHelperByPage = wishService.findWishHelperByPage(wishId, pageNum, pageSize);
        model.addAttribute("pageWishHelper",wishHelperByPage);

        List<WishHelper> list = wishHelperByPage.getList();
        List<Map> wishHelperList = new ArrayList<>();
        for (WishHelper wishHelper : list) {
            Map map = new HashMap();
            map.put("wishHelper",wishHelper);
            Date time = wishHelper.getCreateTime();
            String timeStr = FormatUtils.formatTime(time);
            map.put("createTimeStr",timeStr);
            wishHelperList.add(map);
        }
        model.addAttribute("wishHelperList",wishHelperList);
        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "wish-detail";
    }

    /**
     * @MethodName actionAddWishHelper
     * @Description 帮助满足心愿
     * @Author lt
     * @Param [wishHelper, wishId, session]
     * @return java.lang.String
     **/
    @RequestMapping("/addWishHelper.do")
    public String actionAddWishHelper(WishHelper wishHelper, Long wishId, HttpSession session){
        // 将数据封装成map
        Map wishHelperMap = new HashMap();

        // 利用雪花算法生成id
        wishHelper.setId(SnowflakeIdUtils.nextId());
        wishHelper.setCreateTime(new Date());
        wishHelperMap.put("wishHelper",wishHelper);
        // 获取当前登录的用户
        User loginUser = (User) session.getAttribute("loginUser");
        wishHelperMap.put("userId",loginUser.getId());
        // 用户评论的心愿
        wishHelperMap.put("wishId",wishId);

        wishService.addWishHelper(wishHelperMap);

        return "redirect:/wishDetail?wishId="+wishId;
    }

    /**
     * @MethodName actionDelWishHelper
     * @Description 删除心愿帮助
     * @Author lt
     * @Param [wishHelperId, wishId]
     * @return java.lang.String
     **/
    @RequestMapping("/delWishHelper.do")
    public String actionDelWishHelper(Long wishHelperId, Long wishId){
        wishService.removeWishHelper(wishHelperId);
        return "redirect:/wishDetail?wishId="+wishId;
    }
}
