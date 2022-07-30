package com.lt.codehelper.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @MethodName
 * @Description 博客类型关系持久层
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface BlogToTypeMapper {
    // 插入博客类型关系
    boolean insertBlogToType(Long blogId,Long typeId);

    // 删除博客类型关系
    boolean deleteBlogToType(Long blogId);

    // 按照博客id查询博客类型用户关系
    List<Long> selectAllBlogToTypeById(Long blogId);

    // 按照类型id查询博客数量
    int selectBlogSumByTypeId(Long typeId);
}
