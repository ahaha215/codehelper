package com.lt.codehelper.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Type;
import com.lt.codehelper.entity.User;
import com.lt.codehelper.entity.Wish;
import com.lt.codehelper.entity.WishHelper;
import com.lt.codehelper.mapper.*;
import com.lt.codehelper.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName WishServiceImpl
 * @Description 心愿服务层实现类
 * @Author lt
 * @Version 1.0
 **/
@Service
public class WishServiceImpl implements WishService {

    // 注入 wishMapper
    @Autowired
    WishMapper wishMapper;

    // 注入 wishToTypeMapper;
    @Autowired
    WishToTypeMapper wishToTypeMapper;

    // 注入 wishHelperMapper
    @Autowired
    WishHelperMapper wishHelperMapper;

    // 注入 typeMapper
    @Autowired
    TypeMapper typeMapper;

    // 注入 userMapper
    @Autowired
    UserMapper userMapper;

    /**
     * @MethodName addWish
     * @Description 添加心愿
     * @Author lt
     * @Param [wish, wishTypes]
     * @return boolean
     **/
    @Override
    public boolean addWish(Map wishMap) {
        // 插入基本数据
        boolean insertWishFlag = wishMapper.insertWish(wishMap);
        // 插入心愿类型基本关系
        boolean insertWishToTypeFlag = true;
        Wish wish = (Wish) wishMap.get("wish");
        Long wishId = wish.getId();
        String[] wishTypeNames = (String[]) wishMap.get("wishTypeNames");
        for (String wishTypeName : wishTypeNames) {
            Long typeId = typeMapper.selectTypeByName(wishTypeName).getId();
            boolean flag = wishToTypeMapper.insertWishToType(wishId, typeId);
            if (!flag){
                insertWishToTypeFlag = false;
            }
        }

        return insertWishFlag || insertWishToTypeFlag;
    }

    /**
     * @MethodName removeWish
     * @Description 删除心愿
     * @Author lt
     * @Param [wishId]
     * @return boolean
     **/
    @Override
    public boolean removeWish(Long wishId) {
        boolean flag = wishMapper.deleteWish(wishId);
        return flag;
    }

    /**
     * @MethodName findWishById
     * @Description 按照Id查找心愿
     * @Author lt
     * @Param [wishId]
     * @return com.lt.codehelper.entity.Wish
     **/
    @Override
    public Wish findWishById(Long wishId) {
        Wish wish = wishMapper.selectWishById(wishId);
        // 封装心愿的用户信息
        Long userId = wishMapper.selectUserIdByWishId(wishId);
        User user = userMapper.selectUserById(userId);
        wish.setUser(user);

        // 封装心愿的类型信息
        List<Long> typeIds = wishToTypeMapper.selectAllTypeIdByWishId(wishId);
        List<Type> wishTypes = new ArrayList<>();
        for (Long typeId : typeIds) {
            Type type = typeMapper.selectTypeById(typeId);
            wishTypes.add(type);
        }
        wish.setWishTypes(wishTypes);
        return wish;
    }

    /**
     * @MethodName findWishPage
     * @Description 分页查询心愿
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Wish>
     **/
    @Override
    public PageInfo<Wish> findWishByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Wish> wishes = wishMapper.selectAllWish();
        for (Wish wish : wishes) {
            // 封装User
            Long userId = wishMapper.selectUserIdByWishId(wish.getId());
            wish.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = wishToTypeMapper.selectAllTypeIdByWishId(wish.getId());
            List<Type> wishTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                wishTypes.add(type);
            }
            wish.setWishTypes(wishTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Wish> page = new PageInfo<Wish>(wishes);
        return page;
    }

    /**
     * @MethodName findWishByContentLike
     * @Description 模糊查询心愿
     * @Author lt
     * @Param [wishContentLike, pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Wish>
     **/
    @Override
    public PageInfo<Wish> findWishByContentLike(String wishContentLike, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Wish> wishes = wishMapper.selectWishByContentLike("%"+wishContentLike+"%");
        for (Wish wish : wishes) {
            // 封装User
            Long userId = wishMapper.selectUserIdByWishId(wish.getId());
            wish.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = wishToTypeMapper.selectAllTypeIdByWishId(wish.getId());
            List<Type> wishTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                wishTypes.add(type);
            }
            wish.setWishTypes(wishTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Wish> page = new PageInfo<Wish>(wishes);
        return page;
    }

    /**
     * @MethodName findAllWishByUserId
     * @Description 按照当前用户id查询心愿
     * @Author lt
     * @Param [loginUserId, pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Wish>
     **/
    @Override
    public PageInfo<Wish> findAllWishByUserId(long loginUserId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Wish> wishes = wishMapper.selectAllWishByUserId(loginUserId);
        for (Wish wish : wishes) {
            // 封装User
            Long userId = wishMapper.selectUserIdByWishId(wish.getId());
            wish.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = wishToTypeMapper.selectAllTypeIdByWishId(wish.getId());
            List<Type> wishTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                wishTypes.add(type);
            }
            wish.setWishTypes(wishTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Wish> page = new PageInfo<Wish>(wishes);
        return page;
    }

    /**
     * @MethodName findPendingAuditWishByPage
     * @Description 分页查询待审核的心愿
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Wish>
     **/
    @Override
    public PageInfo<Wish> findPendingAuditWishByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Wish> wishes = wishMapper.selectAllPendingAuditWish();
        for (Wish wish : wishes) {
            // 封装User
            Long userId = wishMapper.selectUserIdByWishId(wish.getId());
            wish.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = wishToTypeMapper.selectAllTypeIdByWishId(wish.getId());
            List<Type> wishTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                wishTypes.add(type);
            }
            wish.setWishTypes(wishTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Wish> page = new PageInfo<Wish>(wishes);
        return page;
    }

    /**
     * @MethodName findPassAuditWishByPage
     * @Description 分页查询审核通过的心愿
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Wish>
     **/
    @Override
    public PageInfo<Wish> findPassAuditWishByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Wish> wishes = wishMapper.selectAllPassAuditWish();
        for (Wish wish : wishes) {
            // 封装User
            Long userId = wishMapper.selectUserIdByWishId(wish.getId());
            wish.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = wishToTypeMapper.selectAllTypeIdByWishId(wish.getId());
            List<Type> wishTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                wishTypes.add(type);
            }
            wish.setWishTypes(wishTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Wish> page = new PageInfo<Wish>(wishes);
        return page;
    }

    /**
     * @MethodName auditWish
     * @Description 审核心愿
     * @Author lt
     * @Param [wishId, audit]
     * @return boolean
     **/
    @Override
    public boolean auditWish(Long wishId, String audit) {
        boolean flag = wishMapper.updateWishAudit(wishId, audit);
        return flag;
    }

    /**
     * @MethodName addWishHelper
     * @Description 添加心愿的帮助
     * @Author lt
     * @Param [wishHelperMap]
     * @return boolean
     **/
    @Override
    public boolean addWishHelper(Map wishHelperMap) {
        boolean flag = wishHelperMapper.insertWishHelper(wishHelperMap);
        return flag;
    }

    /**
     * @MethodName removeWishHelper
     * @Description 删除用户心愿帮助
     * @Author lt
     * @Param [wishHelperId]
     * @return boolean
     **/
    @Override
    public boolean removeWishHelper(Long wishHelperId) {
        boolean flag = wishHelperMapper.deleteWishHelper(wishHelperId);
        return flag;
    }

    /**
     * @MethodName findWishHelperByPage
     * @Description 分页查询心愿的回答
     * @Author lt
     * @Param [wishId, pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.WishHelper>
     **/
    @Override
    public PageInfo<WishHelper> findWishHelperByPage(Long wishId ,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<WishHelper> wishHelpers = wishHelperMapper.selectAllWishHelper(wishId);
        for (WishHelper wishHelper : wishHelpers) {
            // 封装User
            Long userId = wishHelperMapper.selectUserIdByWishHelperId(wishHelper.getId());
            wishHelper.setUser(userMapper.selectUserById(userId));
            // 封装 Wish
            Wish wish = wishMapper.selectWishById(wishId);
            wishHelper.setWish(wish);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<WishHelper> page = new PageInfo<WishHelper>(wishHelpers);
        return page;
    }
}
