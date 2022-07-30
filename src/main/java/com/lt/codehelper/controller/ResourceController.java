package com.lt.codehelper.controller;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.*;
import com.lt.codehelper.service.ResourceService;
import com.lt.codehelper.service.TypeService;
import com.lt.codehelper.util.FormatUtils;
import com.lt.codehelper.util.OssUtils;
import com.lt.codehelper.util.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @ClassName ResourceController
 * @Description 资源控制层
 * @Author lt
 * @Version 1.0
 **/
@Controller
public class ResourceController {

    // 注入 ResourceService
    @Autowired
    ResourceService resourceService;

    // 注入 TypeService
    @Autowired
    TypeService typeService;

    /**
     * @MethodName actionAddBlogTypeDataRender
     * @Description 资源类型数据的渲染
     * @Author lt
     * @Param [model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/recommendResourceTypeDataRender.do")
    public String actionRecommendResourceTypeDataRender(Model model, HttpSession session){
        List<Type> allType = typeService.findAllType();
        model.addAttribute("allType",allType);
        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "resource-recommend";
    }

    /**
     * @MethodName actionAddBlogTypeDataRender
     * @Description 后台资源类型数据的渲染
     * @Author lt
     * @Param [model]
     * @return java.lang.String
     **/
    @RequestMapping("/releaseResourceTypeDataRender.do")
    public String actionReleaseResourceTypeDataRender(Model model){
        List<Type> allType = typeService.findAllType();
        model.addAttribute("allType",allType);
        return "admin/admin-resource-release";
    }

    /**
     * @MethodName actionAddResource
     * @Description 用户推荐资源
     * @Author lt
     * @Param [resource]
     * @return java.lang.String
     **/
    @RequestMapping("/recommendResource.do")
    public String actionAddResource(Resource resource, MultipartFile iconFile, HttpSession session, Model model) throws Exception {
        // 判断数据不为空
        if (resource.getResourceName() == null || resource.getResourceName().length() == 0 ||
                resource.getDescription() == null || resource.getDescription().length() == 0 ||
                resource.getLinkAddress() == null || resource.getLinkAddress().length() == 0 ||
                resource.getDetail() == null || resource.getDetail().length() == 0 ||
                iconFile.isEmpty()){
            model.addAttribute("msg","资源信息不能为空！");
            return "resource-recommend";
        } else {
            Map map = new HashMap();
            resource.setId(SnowflakeIdUtils.nextId());
            resource.setCreateTime(new Date());
            resource.setUpdateTime(new Date());
            resource.setAudit("待审核");
            resource.setViewSum(0);
            map.put("resource",resource);
            // 获取当前登录的用户
            User loginUser = (User) session.getAttribute("loginUser");
            map.put("userId",loginUser.getId());
            // 博客首图上传
            if(!iconFile.isEmpty()){
                String iconFileOriginalFilename = iconFile.getOriginalFilename();
                String objectName = "resource/icon/" + resource.getId() + iconFileOriginalFilename.substring(iconFileOriginalFilename.lastIndexOf("."));
                String ossFileUrl = OssUtils.upload(iconFile, objectName);
                resource.setIcon(ossFileUrl);
            }

            // 展示登录用户信息
            model.addAttribute("loginUser",loginUser);

            boolean flag = resourceService.addResource(map);
            if (flag){
                return "redirect:/resource";
            } else {
                model.addAttribute("msg","对不起，资源推荐失败，请再次重试！");
                return "redirect:/resourceRecommend";
            }
        }
    }

    /**
     * @MethodName actionAddResource
     * @Description 管理员上传资源
     * @Author lt
     * @Param [resource]
     * @return java.lang.String
     **/
    @RequestMapping("/releaseResource.do")
    public String actionReleaseResource(Resource resource, MultipartFile iconFile, HttpSession session, Model model) throws Exception {
        // 判断数据不为空
        if (resource.getResourceName() == null || resource.getResourceName().length() == 0 ||
                resource.getDescription() == null || resource.getDescription().length() == 0 ||
                resource.getLinkAddress() == null || resource.getLinkAddress().length() == 0 ||
                resource.getDetail() == null || resource.getDetail().length() == 0 ||
                iconFile.isEmpty()){
            model.addAttribute("msg","资源信息不能为空！");
            return "resource-recommend";
        } else {
            Map map = new HashMap();
            resource.setId(SnowflakeIdUtils.nextId());
            resource.setCreateTime(new Date());
            resource.setUpdateTime(new Date());
            resource.setAudit("通过");
            resource.setViewSum(0);
            map.put("resource",resource);
            // 获取当前登录的用户
            User loginUser = (User) session.getAttribute("loginUser");
            map.put("userId",loginUser.getId());
            // 博客首图上传
            if(!iconFile.isEmpty()){
                String iconFileOriginalFilename = iconFile.getOriginalFilename();
                String objectName = "resource/icon/" + resource.getId() + iconFileOriginalFilename.substring(iconFileOriginalFilename.lastIndexOf("."));
                String ossFileUrl = OssUtils.upload(iconFile, objectName);
                resource.setIcon(ossFileUrl);
            }
            boolean flag = resourceService.addResource(map);
            if (flag){
                return "redirect:/adminResource";
            } else {
                model.addAttribute("msg","对不起，资源推荐失败，请再次重试！");
                return "redirect:/adminReleaseResource";
            }
        }
    }

    /**
     * @MethodName actionDeleteBlog
     * @Description 删除资源
     * @Author lt
     * @Param [id, redirectAttributes]
     * @return java.lang.String
     **/
    @RequestMapping("/deleteResource.do")
    public String actionDeleteResource(Long resourceId, RedirectAttributes redirectAttributes){
        // 暂时保存待删除的博客信息
        Resource deletedResource = resourceService.findResourceById(resourceId);
        // 修改博客首图
        String[] strings = deletedResource.getIcon().split("/");
        String str = strings[strings.length - 1];
        String objectName = "resource/icon/" + str;
        OssUtils.delete(objectName);

        // 删除博客信息
        boolean deleteResourceFlag = resourceService.removeResource(resourceId);
        if (deleteResourceFlag){
            redirectAttributes.addFlashAttribute("msg","删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("msg","删除失败！");
        }
        return "redirect:/adminResource";
    }

    /**
     * @MethodName actionAuditResourceDetail
     * @Description 待审核资源的详情
     * @Author lt
     * @Param [resourceId, model]
     * @return java.lang.String
     **/
    @RequestMapping("/auditResourceDetail.do")
    public String actionAuditResourceDetail(Long resourceId, Model model){
        Resource resourceById = resourceService.findResourceById(resourceId);
        model.addAttribute("resourceInfo",resourceById);
        return "admin/admin-resource-audit";
    }

    /**
     * @MethodName actionAuditResource
     * @Description 资源审核
     * @Author lt
     * @Param [resourceId, audit]
     * @return java.lang.String
     **/
    @RequestMapping("/auditResource.do")
    public String actionAuditResource(Long resourceId, String audit){
        resourceService.auditResource(resourceId, audit);
        return "redirect:/adminResource";
    }

    /**
     * @MethodName actionResourceList
     * @Description 资源列表
     * @Author lt
     * @Param [pageNum, pageSize, model]
     * @return java.lang.String
     **/
    @RequestMapping("resourceList.do")
    public String actionResourceList(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model){
        PageInfo<Resource> resourceByPage = resourceService.findResourceByPage(pageNum, pageSize);
        model.addAttribute("resourcePageInfo",resourceByPage);
        return "admin/admin-resource";
    }

    /**
     * @MethodName actionResourceList
     * @Description 待审核资源列表
     * @Author lt
     * @Param [pageNum, pageSize, model]
     * @return java.lang.String
     **/
    @RequestMapping("pendingAuditResourceList.do")
    public String actionPendingAuditResourceList(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model){
        PageInfo<Resource> resourceByPage = resourceService.findAllPendingAuditResourceByPage(pageNum,pageSize);
        model.addAttribute("resourcePageInfo",resourceByPage);
        return "admin/admin-resource-pending-audit";
    }

    /**
     * @MethodName actionResourcePage
     * @Description 前台资源页面数据渲染
     * @Author lt
     * @Param [pageNum, pageSize, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/resourcePage.do")
    public String actionResourcePage(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model, HttpSession session){
        // 分页查询资源信息
        PageInfo<Resource> resourceByPage = resourceService.findAllPassAuditResourceByPage(pageNum, pageSize);
        model.addAttribute("pageResources",resourceByPage);

        // 查找最新的资源
        List<Resource> newResource = resourceService.findNewResource(10);
        model.addAttribute("newResources",newResource);

        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "resource";
    }

    /**
     * @MethodName actionResourcePage
     * @Description 前台资源页面数据渲染
     * @Author lt
     * @Param [pageNum, pageSize, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/searchResourcePage.do")
    public String actionSearchResourcePage(String resourceNameLike, @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model, HttpSession session){
        // 分页查询资源信息
        PageInfo<Resource> resourceByPage = resourceService.findResourceByResourceNameLike(resourceNameLike,pageNum,pageSize);
        model.addAttribute("pageResources",resourceByPage);

        // 查找最新的资源
        List<Resource> newResource = resourceService.findNewResource(10);
        model.addAttribute("newResources",newResource);

        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        return "resource";
    }

    /**
     * @MethodName actionResourceDetail
     * @Description 查看资源详情
     * @Author lt
     * @Param [resourceId, pageNum, pageSize, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("resourceDetail.do")
    public String actionResourceDetail(Long resourceId,@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="15")int pageSize, Model model, HttpSession session){
        Resource resourceById = resourceService.findResourceById(resourceId);
        // 浏览次数加一
        resourceService.addResourceViewSum(resourceId,resourceById.getViewSum()+1);
        // 格式化时间
        String createTimeStr = FormatUtils.formatTime(resourceById.getCreateTime());
        model.addAttribute("resource",resourceById);
        model.addAttribute("createTimeStr",createTimeStr);

        // 分页展示评论信息
        PageInfo<ResourceComment> resourceCommentByPage = resourceService.findAllResourceCommentByPage(resourceId, pageNum, pageSize);
        model.addAttribute("pageResourceComment",resourceCommentByPage);

        List<ResourceComment> list = resourceCommentByPage.getList();
        List<Map> resourceCommentList = new ArrayList<>();
        for (ResourceComment resourceComment : list) {
            Map map = new HashMap();
            map.put("resourceComment",resourceComment);
            Date time = resourceComment.getCreateTime();
            String timeStr = FormatUtils.formatTime(time);
            map.put("createTimeStr",timeStr);
            resourceCommentList.add(map);
        }
        model.addAttribute("resourceCommentList",resourceCommentList);

        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);

        return "resource-detail";
    }

    /**
     * @MethodName actionAddResourceComment
     * @Description 添加用户评论资源
     * @Author lt
     * @Param [resourceComment, resourceId, session]
     * @return java.lang.String
     **/
    @RequestMapping("/addResourceComment.do")
    public String actionAddResourceComment(ResourceComment resourceComment, Long resourceId, HttpSession session){
        // 将数据封装成map
        Map resourceCommentMap = new HashMap();

        // 利用雪花算法生成id
        resourceComment.setId(SnowflakeIdUtils.nextId());
        resourceComment.setCreateTime(new Date());
        resourceCommentMap.put("resourceComment",resourceComment);
        // 获取当前登录的用户
        User loginUser = (User) session.getAttribute("loginUser");
        resourceCommentMap.put("userId",loginUser.getId());
        // 用户评论的资源
        resourceCommentMap.put("resourceId",resourceId);

        resourceService.addResourceComment(resourceCommentMap);

        return "redirect:/resourceDetail?resourceId=" + resourceId;
    }

    /**
     * @MethodName actionRemoveResourceComment
     * @Description 删除评论
     * @Author lt
     * @Param [commentId, resourceId]
     * @return java.lang.String
     **/
    @RequestMapping("removeResourceComment.do")
    public String actionRemoveResourceComment(Long commentId,Long resourceId){
        resourceService.removeResourceComment(commentId);
        return "redirect:/resourceDetail?resourceId=" + resourceId;
    }
}
