package com.lt.codehelper.service;

/**
 * @MethodName
 * @Description 博客类型关系服务层接口
 * @Author lt
 * @Param
 * @return
 **/
public interface BlogToTypeService {

    // 按照类型统计博客数量
    int findBlogSumByTypeId(Long typeId);
}
