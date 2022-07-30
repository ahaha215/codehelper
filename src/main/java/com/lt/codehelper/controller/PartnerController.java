package com.lt.codehelper.controller;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Type;
import com.lt.codehelper.entity.User;
import com.lt.codehelper.entity.Partner;
import com.lt.codehelper.service.TypeService;
import com.lt.codehelper.service.PartnerService;
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
 * @ClassName PartnerController
 * @Description 心愿控制层
 * @Author lt
 * @Version 1.0
 **/
@Controller
public class PartnerController {

    // 注入 partnerService
    @Autowired
    PartnerService partnerService;

    // 注入 TypeService
    @Autowired
    TypeService typeService;

    /**
     * @MethodName actionMakePartnerTypeDataRender
     * @Description 找伙伴类型数据回显
     * @Author lt
     * @Param [model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/findPartnerTypeDataRender.do")
    public String actionMakePartnerTypeDataRender(Model model, HttpSession session){
        List<Type> allType = typeService.findAllType();
        model.addAttribute("allType",allType);
        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "partner-find";
    }

    /**
     * @MethodName actionMakePartner
     * @Description 找伙伴
     * @Author lt
     * @Param [Partner, PartnerTypeNames, session, model]
     * @return java.lang.String
     **/
    @RequestMapping("/findPartner.do")
    public String actionMakePartner(Partner partner, String[] partnerTypeNames, HttpSession session, Model model){
        // 校验前端数据
        if (partner.getContent() == null || partner.getContent().length() == 0 ||
                partnerTypeNames == null || partnerTypeNames.length == 0){
            model.addAttribute("msg","对不起，所填信息不能为空！");
            return "forward:/findPartner";
        }
        // 封装 Partner
        partner.setId(SnowflakeIdUtils.nextId());
        partner.setAudit("待审核");
        partner.setCreateTime(new Date());

        Map partnerMap = new HashMap();
        partnerMap.put("partner",partner);
        // 获取当前用户
        User loginUser = (User) session.getAttribute("loginUser");
        partnerMap.put("userId",loginUser.getId());
        partnerMap.put("partnerTypeNames",partnerTypeNames);

        partnerService.addPartner(partnerMap);
        return "redirect:/partner";
    }

    /**
     * @MethodName actionRemovePartner
     * @Description 删除找伙伴
     * @Author lt
     * @Param [PartnerId, redirectAttributes]
     * @return java.lang.String
     **/
    @RequestMapping("/removePartner.do")
    public String actionRemovePartner(Long partnerId, RedirectAttributes redirectAttributes){
        boolean flag = partnerService.removePartner(partnerId);
        if (flag){
            redirectAttributes.addFlashAttribute("msg","删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("msg","删除失败！");
        }
        return "redirect:/adminPartner";
    }

    /**
     * @MethodName actionPartnerList
     * @Description 分页查询找伙伴信息
     * @Author lt
     * @Param [pageNum, pageSize, model]
     * @return java.lang.String
     **/
    @RequestMapping("/partnerList.do")
    public String actionPartnerList(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model){
        // 分页查询资源信息
        PageInfo<Partner> partnerByPage = partnerService.findPartnerByPage(pageNum,pageSize);
        model.addAttribute("pagePartner",partnerByPage);
        return "admin/admin-partner";
    }

    /**
     * @MethodName actionPartnerPendingAuditList
     * @Description 分页查询待审核找伙伴信息
     * @Author lt
     * @Param [pageNum, pageSize, model]
     * @return java.lang.String
     **/
    @RequestMapping("/partnerPendingAuditList.do")
    public String actionPartnerPendingAuditList(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model){
        // 分页查询资源信息
        PageInfo<Partner> partnerByPage = partnerService.findPendingAuditPartnerByPage(pageNum,pageSize);
        model.addAttribute("pagePartner",partnerByPage);
        return "admin/admin-partner-pending-audit";
    }

    /**
     * @MethodName actionAuditPartnerDetail
     * @Description 找伙伴详情
     * @Author lt
     * @Param [PartnerId, model]
     * @return java.lang.String
     **/
    @RequestMapping("/auditPartnerDetail.do")
    public String actionAuditPartnerDetail(Long partnerId, Model model){
        Partner partnerById = partnerService.findPartnerById(partnerId);
        model.addAttribute("partnerInfo",partnerById);
        // 格式化时间
        Date createTime = partnerById.getCreateTime();
        String formatTimeStr = FormatUtils.formatTime(createTime);
        model.addAttribute("formatTimeStr",formatTimeStr);
        return "admin/admin-partner-audit";
    }

    /**
     * @MethodName actionAuditPartner
     * @Description 审核找伙伴
     * @Author lt
     * @Param [PartnerId, audit]
     * @return java.lang.String
     **/
    @RequestMapping("auditPartner.do")
    public String actionAuditPartner(Long partnerId, String audit){
        partnerService.auditPartner(partnerId, audit);
        return "redirect:/adminPartner";
    }

    /**
     * @MethodName actionPartnerPage
     * @Description 分页查询审核通过找伙伴信息
     * @Author lt
     * @Param [pageNum, pageSize, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/partnerPage.do")
    public String actionPartnerPage(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="15")int pageSize, Model model, HttpSession session){
        // 分页查询资源信息
        PageInfo<Partner> partnerByPage = partnerService.findPassAuditPartnerByPage(pageNum, pageSize);
        model.addAttribute("pagePartner",partnerByPage);

        // 封装格式化时间
        List<Partner> list = partnerByPage.getList();
        List<Map> partners = new ArrayList<>();
        for (Partner partner : list) {
            HashMap map = new HashMap();
            map.put("partner",partner);
            String formatTimeStr = FormatUtils.formatTime(partner.getCreateTime());
            map.put("createTimeStr",formatTimeStr);
            partners.add(map);
        }
        model.addAttribute("partners",partners);
        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "partner";
    }

    /**
     * @MethodName actionSearchPartnerPage
     * @Description 搜索找伙伴信息
     * @Author lt
     * @Param [partnerContentLike, pageNum, pageSize, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/searchPartnerPage.do")
    public String actionSearchPartnerPage(String partnerContentLike, @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="15")int pageSize, Model model, HttpSession session){
        // 分页查询资源信息
        PageInfo<Partner> partnerByPage = partnerService.findPartnerByContentLike(partnerContentLike,pageNum,pageSize);
        model.addAttribute("pagePartner",partnerByPage);

        // 封装格式化时间
        List<Partner> list = partnerByPage.getList();
        List<Map> partners = new ArrayList<>();
        for (Partner partner : list) {
            HashMap map = new HashMap();
            map.put("partner",partner);
            String formatTimeStr = FormatUtils.formatTime(partner.getCreateTime());
            map.put("createTimeStr",formatTimeStr);
            partners.add(map);
        }
        model.addAttribute("partners",partners);
        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "partner";
    }

}
