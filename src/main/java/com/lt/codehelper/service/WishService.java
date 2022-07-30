package com.lt.codehelper.service;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Wish;
import com.lt.codehelper.entity.WishHelper;

import java.util.List;
import java.util.Map;

/**
 * @MethodName
 * @Description 心愿服务层接口
 * @Author lt
 * @Param
 * @return
 **/
public interface WishService {
    // 添加心愿
    boolean addWish(Map wishMap);

    // 删除心愿
    boolean removeWish(Long wishId);

    // 按照id查找心愿
    Wish findWishById(Long wishId);

    // 分页查询心愿
    PageInfo<Wish> findWishByPage(int pageNum, int pageSize);

    // 模糊查询心愿
    PageInfo<Wish> findWishByContentLike(String wishContentLike, int pageNum, int pageSize);

    // 按照用户id查询心愿
    PageInfo<Wish> findAllWishByUserId(long loginUserId, int pageNum, int pageSize);

    // 分页查询待审核的心愿
    PageInfo<Wish> findPendingAuditWishByPage(int pageNum, int pageSize);

    // 分页查询审核通过的心愿
    PageInfo<Wish> findPassAuditWishByPage(int pageNum, int pageSize);

    // 资源审核
    boolean auditWish(Long wishId, String audit);

    // 添加心愿的的帮助
    boolean addWishHelper(Map wishHelperMap);

    // 删除心愿的帮助
    boolean removeWishHelper(Long wishHelperId);

    // 分页查询心愿帮助
    PageInfo<WishHelper> findWishHelperByPage(Long wishId ,int pageNum, int pageSize);
}
