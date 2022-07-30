package com.lt.codehelper.service;


import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Resource;
import com.lt.codehelper.entity.ResourceComment;

import java.util.List;
import java.util.Map;

/**
 * @MethodName
 * @Description 资源服务层接口
 * @Author lt
 * @Param
 * @return
 **/
public interface ResourceService {
    // 添加资源
    boolean addResource(Map resourceMap);

    // 审核资源
    boolean auditResource(Long resourceId, String audit);

    // 删除资源
    boolean removeResource(Long resourceId);

    // 按照id查找资源
    Resource findResourceById(Long resourceId);

    // 查找最新资源
    List<Resource> findNewResource(int size);

    // 按照分页查找资源信息
    PageInfo<Resource> findResourceByPage(int pageNum, int pageSize);

    // 分页查找审核通过的资源信息
    PageInfo<Resource> findAllPassAuditResourceByPage(int pageNum, int pageSize);

    // 分页查找未审核的资源信息
    PageInfo<Resource> findAllPendingAuditResourceByPage(int pageNum, int pageSize);

    // 按照资源名称进行模糊查询
    PageInfo<Resource> findResourceByResourceNameLike(String resourceNameLike, int pageNum, int pageSize);

    // 按照资源名称进行模糊查询
    PageInfo<Resource> findAllResourceByUserId(long loginUserId, int pageNum, int pageSize);

    // 增加资源的浏览量
    boolean addResourceViewSum(Long resourceId, int viewSum);

    // 添加评论
    boolean addResourceComment(Map resourceCommentMap);

    // 删除评论
    boolean removeResourceComment(Long commentId);

    // 分页查询评论
    PageInfo<ResourceComment> findAllResourceCommentByPage(Long resourceId, int pageNum, int pageSize);
}
