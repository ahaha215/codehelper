package com.lt.codehelper.controller;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Blog;
import com.lt.codehelper.entity.BlogComment;
import com.lt.codehelper.entity.Type;
import com.lt.codehelper.entity.User;
import com.lt.codehelper.service.*;
import com.lt.codehelper.util.FormatUtils;
import com.lt.codehelper.util.MarkdownUtils;
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
 * @ClassName BlogController
 * @Description 博客控制层
 * @Author lt
 * @Version 1.0
 **/
@Controller
public class BlogController {

    // 注入BlogService
    @Autowired
    BlogService blogService;

    // 注入TypeService
    @Autowired
    TypeService typeService;

    // 注入UserService
    @Autowired
    UserService userService;

    // 注入BlogCommentService
    @Autowired
    BlogCommentService blogCommentService;

    // 注入BlogToTypeService
    @Autowired
    BlogToTypeService blogToTypeService;

    /**
     * @MethodName actionAddBlog
     * @Description 新增博客
     * @Author lt
     * @Param [blog, firstPictureFile, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/addBlog.do")
    public String actionAddBlog(Blog blog, MultipartFile firstPictureFile, Model model, HttpSession session) throws Exception {
        // 判断数据不为空
        if (blog.getTitle() == null || blog.getTitle().length() == 0 ||
                blog.getDescription() == null || blog.getDescription().length() == 0 ||
                blog.getContent() == null || blog.getContent().length() == 0 ||
                blog.getFlag() == null || blog.getFlag().length() == 0 ||
                blog.getPublished() == null || blog.getPublished().length() == 0 ||
                firstPictureFile.isEmpty()){
            model.addAttribute("msg","博客信息不能为空！");
            return "admin/admin-blog-add";
        } else {
            // 利用雪花算法生成博客id
            long id = SnowflakeIdUtils.nextId();
            blog.setId(id);
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViewSum(0);
            // 获取登录用户的id信息
            User loginUser = (User) session.getAttribute("loginUser");
            blog.setUserId(loginUser.getId());

            // 博客首图上传
            if(!firstPictureFile.isEmpty()){
                String firstPictureFileOriginalFilename = firstPictureFile.getOriginalFilename();
                String objectName = "blog/first-picture/" + blog.getId() + firstPictureFileOriginalFilename.substring(firstPictureFileOriginalFilename.lastIndexOf("."));
                String ossFileUrl = OssUtils.upload(firstPictureFile, objectName);
                blog.setFirstPicture(ossFileUrl);
            }

            // 博客信息插入
            boolean addBlogFlag = blogService.addBlog(blog);
            if (!addBlogFlag){
                model.addAttribute("msg","对不起，博客添加失败，请重试！");
                return "admin/admin-blog-add";
            }
        }
        return "redirect:/adminBlog";
    }

    /**
     * @MethodName actionAddBlogTypeDataRender
     * @Description 新增博客类型数据的渲染
     * @Author lt
     * @Param [model]
     * @return java.lang.String
     **/
    @RequestMapping("/addBlogTypeDataRender.do")
    public String actionAddBlogTypeDataRender(Model model){
        List<Type> allType = typeService.findAllType();
        model.addAttribute("allType",allType);
        return "admin/admin-blog-add";
    }

    /**
     * @MethodName actionDeleteBlog
     * @Description 删除博客
     * @Author lt
     * @Param [id, redirectAttributes]
     * @return java.lang.String
     **/
    @RequestMapping("/deleteBlog.do")
    public String actionDeleteBlog(Long id, RedirectAttributes redirectAttributes){
        // 暂时保存待删除的博客信息
        Blog deletedBlog = blogService.findBlogById(id);
        // 修改博客首图
        String[] strings = deletedBlog.getFirstPicture().split("/");
        String str = strings[strings.length - 1];
        String objectName = "blog/first-picture/" + str;
        OssUtils.delete(objectName);

        // 删除博客信息
        boolean deleteBlogFlag = blogService.deleteBlog(id);

        if (deleteBlogFlag){
            redirectAttributes.addFlashAttribute("msg","删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("msg","删除失败！");
        }
        return "redirect:/adminBlog";
    }

    /**
     * @MethodName actionBlogDataEcho
     * @Description 博客数据回显
     * @Author lt
     * @Param [id, model]
     * @return java.lang.String
     **/
    @RequestMapping("/blogDataEcho.do")
    public String actionBlogDataEcho(Long id, Model model){
        // 按照博客id查找博客
        Blog blogById = blogService.findBlogById(id);
        // 查询类型信息
        List<Type> allType = typeService.findAllType();
        // 进行数据回显
        model.addAttribute("blogInfo",blogById);
        model.addAttribute("allType",allType);
        return "admin/admin-blog-update";
    }

    /**
     * @MethodName actionUpdateBlog
     * @Description 博客信息修改
     * @Author lt
     * @Param [blog, firstPictureFile, model]
     * @return java.lang.String
     **/
    @RequestMapping("/updateBlog.do")
    public String actionUpdateBlog(Blog blog, MultipartFile firstPictureFile, Model model) throws Exception {
        // 原本博客信息
        Blog blogById = blogService.findBlogById(blog.getId());
        // 判断博客首图信息
        if (firstPictureFile.isEmpty()){
            // 表明用户不想博客首图信息
            blog.setFirstPicture(blogById.getFirstPicture());
        } else {
            String firstPicture = blogById.getFirstPicture();
            String[] strings = firstPicture.split("/");
            String str = strings[strings.length - 1];
            String objectName = "blog/first-picture/" + str;
            String ossFileUrl = OssUtils.upload(firstPictureFile, objectName);
            blog.setFirstPicture(ossFileUrl);
        }

        // 更新博客更新时间
        blog.setUpdateTime(new Date());

        // 博客信息修改
        boolean updateBlogFlag = blogService.updateBlog(blog);

        if (updateBlogFlag){
            // 修改成功，返回博客数据列表
            return "redirect:/adminBlog";
        } else {
            // 修改失败，设置提示信息，任跳转到博客信息修改页面
            model.addAttribute("msg","对不起，修改失败，请重试！");
            return "redirect:/adminBlogUpdate";
        }
    }

    /**
     * @MethodName actionAddComment
     * @Description 添加博客评论
     * @Author lt
     * @Param [blogComment, session]
     * @return java.lang.String
     **/
    @RequestMapping("/addComment.do")
    public String actionAddComment(BlogComment blogComment,HttpSession session){
        // 根据雪花算法获取设置博客评论的id
        blogComment.setId(SnowflakeIdUtils.nextId());
        // 设置博客的评论时间
        blogComment.setCreateTime(new Date());
        // 获取评论用户
        User loginUser = (User) session.getAttribute("loginUser");
        blogComment.setUserId(loginUser.getId());
        blogCommentService.addBlogComment(blogComment);

        return "redirect:/blogDetail?blogId="+blogComment.getBlogId();
    }

    /**
     * @MethodName actionDelBlogComment
     * @Description 删除评论
     * @Author lt
     * @Param [commentId, blogId]
     * @return java.lang.String
     **/
    @RequestMapping("/delBlogComment.do")
    public String actionDelBlogComment(Long commentId, Long blogId){
        blogCommentService.removeBlogComment(commentId);
        return "redirect:/blogDetail?blogId="+blogId;
    }

    /**
     * @MethodName actionBlogList
     * @Description 分页查询博客信息
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/blogList.do")
    public String actionBlogList(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model){
        PageInfo<Blog> page = blogService.findBlogByPage(pageNum,pageSize);
        model.addAttribute("blogPageInfo", page);
        return "admin/admin-blog";
    }

    /**
     * @MethodName actionBlogDetail
     * @Description 展示博客详情
     * @Author lt
     * @Param [id, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/blogDetail.do")
    public String actionBlogDetail(Long blogId, @RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="15")int pageSize, Model model, HttpSession session){
        Blog blogById = blogService.findBlogById(blogId);
        // 博客访问时，增加博客的访问量
        blogService.addBlogViewSum(blogId,blogById.getViewSum()+1);
        // 转换博客内容
        String blogContentHtml = MarkdownUtils.markdownToHtmlExtensions(blogById.getContent());
        blogById.setContent(blogContentHtml);
        model.addAttribute("blog",blogById);
        // 格式化博客添加时间
        String createTimeStr = FormatUtils.formatTime(blogById.getCreateTime());
        model.addAttribute("createTimeStr",createTimeStr);
        // 格式化博客更新时间
        String updateTimeStr = FormatUtils.formatTime(blogById.getUpdateTime());
        model.addAttribute("updateTimeStr",updateTimeStr);
        // 查询博客作者信息
        User author = userService.findUserById(blogById.getUserId());
        model.addAttribute("author",author);
        // 查询博客的评论信息
        List<Map> comments = new ArrayList<>();
        PageInfo<BlogComment> allBlogComment = blogCommentService.findAllBlogCommentByPage(blogId,pageNum,pageSize);
        for (BlogComment blogComment : allBlogComment.getList()) {
            Map map = new HashMap();
            // 添加评论信息
            map.put("blogComment",blogComment);
            // 格式化评论的时间
            String commentTimeStr = FormatUtils.formatTime(blogComment.getCreateTime());
            map.put("commentTimeStr",commentTimeStr);
            // 添加评论用户信息
            map.put("commentUser",userService.findUserById(blogComment.getUserId()));
            comments.add(map);
        }
        model.addAttribute("pageBlogComment",allBlogComment);
        model.addAttribute("comments",comments);

        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);

        return "blog-detail";
    }

    /**
     * @MethodName actionBlog
     * @Description 前台博客页面信息展示
     * @Author lt
     * @Param [model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/blogPage.do")
    public String actionBlog(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model, HttpSession session){
        // 分页展示博客信息
        PageInfo<Blog> page = blogService.findBlogOfPublished(pageNum,pageSize);
        model.addAttribute("blogPageInfo", page);

        // 修改返回的博客信息，添加作者信息以及格式化时间
        List<Map> blogList = new ArrayList<>();
        for (Blog blog : page.getList()) {
            Map map = new HashMap();
            map.put("blog",blog);
            String updateTimeStr = FormatUtils.formatTime(blog.getUpdateTime());
            map.put("updateTimeStr",updateTimeStr);
            User user = userService.findUserById(blog.getUserId());
            map.put("user",user);
            blogList.add(map);
        }
        model.addAttribute("blogList",blogList);

        // 展示类型信息
        List<Map<String, Integer>> blogSumByTypeId = new ArrayList<>();

        // 获取类型所对应的博客数量
        List<Type> allType = typeService.findAllType();
        for (Type type : allType) {
            Map map = new HashMap();
            int sum = blogToTypeService.findBlogSumByTypeId(type.getId());
            map.put("type",type);
            map.put("sum",sum);
            blogSumByTypeId.add(map);
        }

        // 按照类型所对应的博客数量进行排序(降序)
        Collections.sort(blogSumByTypeId, new Comparator<Map<String, Integer>>() {
            public int compare(Map<String, Integer> o1, Map<String, Integer> o2) {
                Integer name1 = o1.get("sum");
                Integer name2 = o2.get("sum");
                return name2.compareTo(name1);
            }
        });

        // 获取类型对应博客数量前5的类型
        List<Map> blogSumByTypeIdTop = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i++){
            blogSumByTypeIdTop.add(blogSumByTypeId.get(i));
        }

        model.addAttribute("blogSumByTypeIdTop",blogSumByTypeIdTop);

        // 获取最新博客信息(10条)
        List<Blog> newBlogs = blogService.findNewBlog(10);
        model.addAttribute("newBlogs",newBlogs);

        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);

        return "blog";
    }

    /**
     * @MethodName actionSearchBlogPage
     * @Description 前台搜索博客页面信息展示
     * @Author lt
     * @Param [blogTitleLike, pageNum, pageSize, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/searchBlogPage.do")
    public String actionSearchBlogPage(String blogTitleLike,@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model, HttpSession session){
        // 分页展示博客信息
        PageInfo<Blog> page = blogService.findBlogByTitleLike(blogTitleLike,pageNum,pageSize);
        model.addAttribute("blogPageInfo", page);

        // 修改返回的博客信息，添加作者信息以及格式化时间
        List<Map> blogList = new ArrayList<>();
        for (Blog blog : page.getList()) {
            Map map = new HashMap();
            map.put("blog",blog);
            String updateTimeStr = FormatUtils.formatTime(blog.getUpdateTime());
            map.put("updateTimeStr",updateTimeStr);
            User user = userService.findUserById(blog.getUserId());
            map.put("user",user);
            blogList.add(map);
        }
        model.addAttribute("blogList",blogList);

        // 展示类型信息
        List<Map<String, Integer>> blogSumByTypeId = new ArrayList<>();

        // 获取类型所对应的博客数量
        List<Type> allType = typeService.findAllType();
        for (Type type : allType) {
            Map map = new HashMap();
            int sum = blogToTypeService.findBlogSumByTypeId(type.getId());
            map.put("type",type);
            map.put("sum",sum);
            blogSumByTypeId.add(map);
        }

        // 按照类型所对应的博客数量进行排序(降序)
        Collections.sort(blogSumByTypeId, new Comparator<Map<String, Integer>>() {
            public int compare(Map<String, Integer> o1, Map<String, Integer> o2) {
                Integer name1 = o1.get("sum");
                Integer name2 = o2.get("sum");
                return name2.compareTo(name1);
            }
        });

        // 获取类型对应博客数量前5的类型
        List<Map> blogSumByTypeIdTop = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i++){
            blogSumByTypeIdTop.add(blogSumByTypeId.get(i));
        }

        model.addAttribute("blogSumByTypeIdTop",blogSumByTypeIdTop);

        // 获取最新博客信息(10条)
        List<Blog> newBlogs = blogService.findNewBlog(10);
        model.addAttribute("newBlogs",newBlogs);

        // 展示登录用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);

        return "blog";
    }
}