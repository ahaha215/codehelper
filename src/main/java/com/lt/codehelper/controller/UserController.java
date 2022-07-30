package com.lt.codehelper.controller;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Partner;
import com.lt.codehelper.entity.Resource;
import com.lt.codehelper.entity.User;
import com.lt.codehelper.entity.Wish;
import com.lt.codehelper.service.PartnerService;
import com.lt.codehelper.service.ResourceService;
import com.lt.codehelper.service.UserService;
import com.lt.codehelper.service.WishService;
import com.lt.codehelper.util.FormatUtils;
import com.lt.codehelper.util.MailUtils;
import com.lt.codehelper.util.OssUtils;
import com.lt.codehelper.util.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Random;

/**
 * @ClassName UserController
 * @Description 用户控制层
 * @Author lt
 * @Version 1.0
 **/
@Controller
public class UserController {

    // 注入用户服务层
    @Autowired
    UserService userService;

    // 注入资源服务层
    @Autowired
    ResourceService resourceService;

    // 注入心愿服务层
    @Autowired
    WishService wishService;

    // 注入伙伴服务层
    @Autowired
    PartnerService partnerService;

    /**
     * @MethodName actionRegister
     * @Description 注册
     * @Author lt
     * @Param [user, headPortraitFile, model]
     * @return java.lang.String
     **/
    @RequestMapping("/register.do")
    public String actionRegister(User user, MultipartFile headPortraitFile, Model model) throws Exception {
        // 判断提交数据不为空
        if (user.getUsername() == null || user.getUsername().length() == 0 ||
                user.getPassword() == null || user.getPassword().length() == 0 ||
                user.getEmail() == null || user.getEmail().length() == 0 ||
                headPortraitFile.isEmpty()){
            model.addAttribute("msg","个人信息不能为空！");
            return "register";
        } else {
            // 利用雪花算法获取用户id
            long id = SnowflakeIdUtils.nextId();
            user.setId(id);
            user.setUserType("普通用户");
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());

            // 用户头像上传
            if(!headPortraitFile.isEmpty()){
                String originalFilename = headPortraitFile.getOriginalFilename();
                String objectName = "user/head-portrait/" + user.getId() + originalFilename.substring(originalFilename.lastIndexOf("."));
                String ossFileUrl = OssUtils.upload(headPortraitFile, objectName);
                user.setHeadPortrait(ossFileUrl);
            }

            // 用户信息插入
            boolean registerFlag = userService.register(user);

            if (registerFlag == false){
                model.addAttribute("msg","对不起，注册失败，请重试！");
                return "register";
            }
        }
        return "login";
    }

    /**
     * @MethodName actionLogin
     * @Description 登录
     * @Author lt
     * @Param [username, password, userType, session, model]
     * @return java.lang.String
     **/
    @RequestMapping("/login.do")
    public String actionLogin(String username, String password, String userType, HttpSession session ,Model model){
        User loginUser = userService.login(username, password, userType);
        if (loginUser != null){
            // 用户信息放入Session
            session.setAttribute("loginUser",loginUser);
            // 进行用户角色判断
            if ("普通用户".equals(userType)){
                return "redirect:/index";
            } else {
                return "redirect:/adminIndex";
            }
        } else {
            model.addAttribute("msg","对不起，用户名或密码错误！");
            return "login";
        }
    }

    /**
     * @MethodName actionLogOut
     * @Description 退出登录
     * @Author lt
     * @Param [session]
     * @return java.lang.String
     **/
    @RequestMapping("/logout.do")
    public String actionLogOut(HttpSession session){
        session.removeAttribute("loginUser");
        return "login";
    }

    /**
     * @MethodName actionSendVerificationCode
     * @Description 发送验证码
     * @Author lt
     * @Param [email, session]
     * @return void
     **/
    @RequestMapping("/sendVerificationCode.do")
    @ResponseBody
    public void actionSendVerificationCode(String email, HttpSession session){
        // 产生验证码
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random random = new Random();
        StringBuffer code = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = random.nextInt(size);
            //在base字符串中获取下标为index的字符
            char charAt = base.charAt(index);
            //将c放入到StringBuffer中去
            code.append(charAt);
        }

        String verificationCode = code.toString();

        // 发送验证邮件
        String text = "您好，您的验证码为："+verificationCode+"，为了您的账号安全，请您不要轻易将验证码告知他人。";
        String title = "CodeHelper-找回密码";

        boolean mailFlag = MailUtils.sendMail(email, text, title);

        // 将验证码存入 Session
        if (mailFlag){
            session.setAttribute("verificationCode",verificationCode);
        }
    }

    /**
     * @MethodName actionResetPassword
     * @Description 密码重置
     * @Author lt
     * @Param [email, verificationCode, newPassword, model, session]
     * @return java.lang.String
     **/
    @RequestMapping("/resetPassword.do")
    public String actionResetPassword(String email, String verificationCode, String newPassword, Model model, HttpSession session){
        // 获取 Session 中验证码
        String sessionCode = (String) session.getAttribute("verificationCode");
        // 移出 Session 中的验证码 , 保证验证码只能用一次
        session.removeAttribute("verificationCode");
        // 比对验证码
        if (verificationCode.equals(sessionCode)){
            // 重置密码
            boolean resetPasswordFlag = userService.resetPassword(email, newPassword);
            if (resetPasswordFlag){
                return "login";
            } else {
                model.addAttribute("msg","对不起，密码重置失败！");
                return "reset-password";
            }
        } else {
            model.addAttribute("msg","对不起，邮箱或验证码填写错误！");
            return "reset-password";
        }
    }

    /**
     * @MethodName actionAddUser
     * @Description 注册
     * @Author lt
     * @Param [user, headPortraitFile, model]
     * @return java.lang.String
     **/
    @RequestMapping("/addUser.do")
    public String actionAddUser(User user, MultipartFile headPortraitFile, Model model) throws Exception {
        // 判断提交数据不为空
        if (user.getUsername() == null || user.getUsername().length() == 0 ||
                user.getPassword() == null || user.getPassword().length() == 0 ||
                user.getEmail() == null || user.getEmail().length() == 0 ||
                user.getUserType() == null || user.getUserType().length() == 0 ||
                headPortraitFile.isEmpty()){
            model.addAttribute("msg","个人信息不能为空！");
            return "/admin/admin-user-add";
        } else {
            // 利用雪花算法获取用户id
            long id = SnowflakeIdUtils.nextId();
            user.setId(id);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());

            // 用户头像上传
            if(!headPortraitFile.isEmpty()){
                // 获取原始文件名
                String originalFilename = headPortraitFile.getOriginalFilename();
                String objectName = "user/head-portrait/" + user.getId() + originalFilename.substring(originalFilename.lastIndexOf("."));
                String ossFileUrl = OssUtils.upload(headPortraitFile, objectName);
                user.setHeadPortrait(ossFileUrl);
            }

            // 用户信息插入
            boolean addUserFlag = userService.addUser(user);

            if (addUserFlag == false){
                model.addAttribute("msg","对不起，添加用户失败，请重试！");
                return "admin/admin-user-add";
            }
        }
        return "redirect:/adminUser";
    }

    /**
     * @MethodName actonDeleteUser
     * @Description 删除用户
     * @Author lt
     * @Param [id, redirectAttributes]
     * @return java.lang.String
     **/
    @RequestMapping("/deleteUser.do")
    public String actonDeleteUser(Long id, RedirectAttributes redirectAttributes){
        User userById = userService.findUserById(id);
        String[] strings = userById.getHeadPortrait().split("/");
        String str = strings[strings.length - 1];
        String objectName = "user/head-portrait/" + str;
        OssUtils.delete(objectName);
        boolean deleteUserFlag = userService.deleteUser(id);
        if (!deleteUserFlag){
            // 删除失败
            redirectAttributes.addFlashAttribute("msg","对不起，删除失败！");
        } else {
            redirectAttributes.addFlashAttribute("msg","删除成功！");
        }
        return "redirect:/adminUser";
    }

    /**
     * @MethodName actionUpdateUser
     * @Description 用户信息修改
     * @Author lt
     * @Param [user]
     * @return java.lang.String
     **/
    @RequestMapping("/updateUser.do")
    public String actionUpdateUser(User user, MultipartFile headPortraitFile, Model model) throws Exception {
        // 原本的用户信息
        User userById = userService.findUserById(user.getId());
        // 头像信息为空
        if (headPortraitFile.isEmpty()){
            // 表明用户不想修改头像则将之前的用户头像信息传入
            user.setHeadPortrait(userById.getHeadPortrait());
        } else {
            String headPortrait = userById.getHeadPortrait();
            String[] strings = headPortrait.split("/");
            String str = strings[strings.length - 1];
            String objectName = "user/head-portrait/" + str;
            String ossFileUrl = OssUtils.upload(headPortraitFile, objectName);
            user.setHeadPortrait(ossFileUrl);
        }

        // 更新用户更新时间
        user.setUpdateTime(new Date());

        // 用户信息修改
        boolean updateUserByIdFlag = userService.updateUserById(user);

        if (updateUserByIdFlag){
            // 修改成功，返回用户数据列表
            return "redirect:/adminUser";
        } else {
            // 修改失败，设置提示信息，任跳转到用户信息修改页面
            model.addAttribute("msg","对不起，修改失败，请重试！");
            return "redirect:/adminUserUpdate";
        }
    }

    /**
     * @MethodName actionUserDataEcho
     * @Description 用户数据回显
     * @Author lt
     * @Param [id, model]
     * @return java.lang.String
     **/
    @RequestMapping("/userDataEcho.do")
    public String actionUserDataEcho(Long id,Model model){
        User userById = userService.findUserById(id);
        if (userById != null){
            model.addAttribute("userInfo",userById);
        }
        return "admin/admin-user-update";
    }

    /**
     * @MethodName actionUserListByPage
     * @Description 分页查询用户信息
     * @Author lt
     * @Param [pageNumber, pageSize, model]
     * @return java.lang.String
     **/
    @RequestMapping("/userList.do")
    public String actionUserListByPage(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model){
        PageInfo<User> page = userService.findUserByPage(pageNum,pageSize);
        model.addAttribute("userPageInfo", page);
        return "admin/admin-user";
    }

    /**
     * @MethodName actionAboutUserResource
     * @Description 当前用户的推荐资源
     * @Author lt
     * @Param [pageNum, pageSize, session, model]
     * @return java.lang.String
     **/
    @RequestMapping("/aboutUserResource.do")
    public String actionAboutUserResource(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="5")int pageSize, HttpSession session,Model model){
        // 获取当前登录用户
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        model.addAttribute("createTimeStr", FormatUtils.formatTime(loginUser.getCreateTime()));

        // 当前用户推荐的资源
        PageInfo<Resource> allResourceByUserId = resourceService.findAllResourceByUserId(loginUser.getId(), pageNum, pageSize);
        model.addAttribute("allResourceByUserId",allResourceByUserId);
        return "about-me-resource";
    }

    /**
     * @MethodName actionDeleteBlog
     * @Description 用户删除资源
     * @Author lt
     * @Param [id, model]
     * @return java.lang.String
     **/
    @RequestMapping("/deleteUserResource.do")
    public String actionDeleteUserResource(Long resourceId, Model model){
        // 暂时保存待删除的博客信息
        Resource deletedResource = resourceService.findResourceById(resourceId);
        // 修改博客首图
        String[] strings = deletedResource.getIcon().split("/");
        String str = strings[strings.length - 1];
        String objectName = "resource/icon/" + str;
        OssUtils.delete(objectName);

        // 删除博客信息
        resourceService.removeResource(resourceId);
        return "redirect:/aboutUserResource";
    }

    /**
     * @MethodName actionAboutUserWishPage
     * @Description 当前用户心愿
     * @Author lt
     * @Param [pageNum, pageSize, session, model]
     * @return java.lang.String
     **/
    @RequestMapping("/aboutUserWishPage.do")
    public String actionAboutUserWishPage(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="15")int pageSize,HttpSession session ,Model model){
        // 获取当前登录用户
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        model.addAttribute("createTimeStr", FormatUtils.formatTime(loginUser.getCreateTime()));

        // 当前用户的心愿
        PageInfo<Wish> allWishByUserId = wishService.findAllWishByUserId(loginUser.getId(), pageNum, pageSize);
        model.addAttribute("allWishByUserId",allWishByUserId);
        return "about-me-wish";
    }

    /**
     * @MethodName actionDeleteUserWish
     * @Description 用户删除心愿
     * @Author lt
     * @Param [wishId]
     * @return java.lang.String
     **/
    @RequestMapping("/deleteUserWish.do")
    public String actionDeleteUserWish(Long wishId){
        wishService.removeWish(wishId);
        return "redirect:/aboutUserWish";
    }

    /**
     * @MethodName actionAboutUserPartnerPage
     * @Description 当前用户伙伴
     * @Author lt
     * @Param [pageNum, pageSize, session, model]
     * @return java.lang.String
     **/
    @RequestMapping("/aboutUserPartnerPage.do")
    public String actionAboutUserPartnerPage(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="15")int pageSize,HttpSession session ,Model model){
        // 获取当前登录用户
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        model.addAttribute("createTimeStr", FormatUtils.formatTime(loginUser.getCreateTime()));

        // 当前用户的心愿
        PageInfo<Partner> allPartnerByUserId = partnerService.findAllPartnerByUserId(loginUser.getId(), pageNum, pageSize);
        model.addAttribute("allPartnerByUserId",allPartnerByUserId);
        return "about-me-partner";
    }

    /**
     * @MethodName actionDeleteUserPartner
     * @Description 用户删除伙伴
     * @Author lt
     * @Param [partnerId]
     * @return java.lang.String
     **/
    @RequestMapping("/deleteUserPartner.do")
    public String actionDeleteUserPartner(Long partnerId){
        partnerService.removePartner(partnerId);
        return "redirect:/aboutUserPartner";
    }

    /**
     * @MethodName actionAboutUser
     * @Description 用户个人信息
     * @Author lt
     * @Param [session, model]
     * @return java.lang.String
     **/
    @RequestMapping("/aboutUserInfo.do")
    public String actionAboutUserInfo(HttpSession session, Model model){
        // 获取当前登录用户
        User loginUser = (User) session.getAttribute("loginUser");
        model.addAttribute("loginUser",loginUser);
        model.addAttribute("createTimeStr", FormatUtils.formatTime(loginUser.getCreateTime()));

        return "about-me-info";
    }

    /**
     * @MethodName actionUpdateUserInfo
     * @Description 用户更新用户信息
     * @Author lt
     * @Param [user, headPortraitFile, session, model]
     * @return java.lang.String
     **/
    @RequestMapping("/updateUserInfo.do")
    public String actionUpdateUserInfo(User user, MultipartFile headPortraitFile,HttpSession session, Model model) throws Exception {
        // 原本的用户信息
        User loginUser = (User) session.getAttribute("loginUser");
        User userById = userService.findUserById(loginUser.getId());
        // 头像信息为空
        if (headPortraitFile.isEmpty()){
            // 表明用户不想修改头像则将之前的用户头像信息传入
            user.setHeadPortrait(userById.getHeadPortrait());
        } else {
            String headPortrait = userById.getHeadPortrait();
            String[] strings = headPortrait.split("/");
            String str = strings[strings.length - 1];
            String objectName = "user/head-portrait/" + str;
            String ossFileUrl = OssUtils.upload(headPortraitFile, objectName);
            user.setHeadPortrait(ossFileUrl);
        }

        // 更新用户更新时间
        user.setUpdateTime(new Date());

        // 用户id，类型，注册时间不能更改，所有将用户注册时的基本信息填入
        user.setId(userById.getId());
        user.setUserType(userById.getUserType());
        user.setCreateTime(userById.getCreateTime());

        // 用户信息修改
        userService.updateUserById(user);

        // 用户信息修改之后，需重新登录
        session.removeAttribute("loginUser");
        return "login";
    }
}
