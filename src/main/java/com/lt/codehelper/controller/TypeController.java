package com.lt.codehelper.controller;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Type;
import com.lt.codehelper.service.TypeService;
import com.lt.codehelper.util.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @ClassName TypeController
 * @Description 类型控制层
 * @Author lt
 * @Version 1.0
 **/
@Controller
public class TypeController {

    // 注入 typeService
    @Autowired
    TypeService typeService;

    /**
     * @MethodName actionAddType
     * @Description 管理员添加类型
     * @Author lt
     * @Param []
     * @return java.lang.String
     **/
    @RequestMapping("/addType.do")
    public String actionAddType(Type type, Model model){
        // 数据校验
        if (type.getTypeName() == null || type.getTypeName().length() == 0){
            model.addAttribute("msg","类型信息不能为空！");
            return "admin/admin-type-add";
        }
        // 根据雪花算法获取id
        long id = SnowflakeIdUtils.nextId();
        type.setId(id);
        // 数据插入
        boolean addTypeFlag = typeService.addType(type);
        if (addTypeFlag){
            return "redirect:/adminType";
        } else {
            model.addAttribute("msg","对不起，添加失败，请重试！");
            return "admin/admin-type-add";
        }
    }

    /**
     * @MethodName actionDeleteTypeById
     * @Description TODO
     * @Author lt
     * @Param [id, model]
     * @return java.lang.String
     **/
    @RequestMapping("/deleteType.do")
    public String actionDeleteTypeById(Long id, RedirectAttributes redirectAttributes){
        boolean deleteTypeByIdFlag = typeService.deleteType(id);
        if (deleteTypeByIdFlag){
            redirectAttributes.addFlashAttribute("msg","删除成功！");
        } else {
            redirectAttributes.addFlashAttribute("msg","删除失败！");
        }
        return "redirect:/adminType";
    }

    /**
     * @MethodName actionUpdateType
     * @Description 更新类型
     * @Author lt
     * @Param [type]
     * @return java.lang.String
     **/
    @RequestMapping("/updateType.do")
    public String actionUpdateType(Type type, Model model){
        boolean updateTypeFlag = typeService.updateType(type);
        if (updateTypeFlag){
            return "redirect:/adminType";
        } else {
            model.addAttribute("msg","对不起，更新失败，请再次尝试！");
            return "admin/admin-type-update";
        }
    }

    /**
     * @MethodName actionTypeDataEcho
     * @Description 类型数据回显
     * @Author lt
     * @Param [id]
     * @return java.lang.String
     **/
    @RequestMapping("/typeDataEcho.do")
    public String actionTypeDataEcho(Long id, Model model){
        Type typeById = typeService.findTypeById(id);
        model.addAttribute("typeInfo",typeById);
        return "admin/admin-type-update";
    }

    /**
     * @MethodName actionTypeListByPage
     * @Description 分页查询类型
     * @Author lt
     * @Param [pageNum, pageSize, model]
     * @return java.lang.String
     **/
    @RequestMapping("/typeList.do")
    public String actionTypeListByPage(@RequestParam(value="pageNum",defaultValue="1")int pageNum, @RequestParam(value="pageSize",defaultValue="10")int pageSize, Model model){
        PageInfo<Type> page = typeService.findTypeByPage(pageNum,pageSize);
        model.addAttribute("typePageInfo", page);
        return "admin/admin-type";
    }
}
