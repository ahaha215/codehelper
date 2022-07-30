package com.lt.codehelper.service;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Blog;

import java.util.List;

/**
 * @MethodName
 * @Description 博客服务层接口
 * @Author lt
 * @Param
 * @return
 **/
public interface BlogService {
    // 添加博客
    boolean addBlog(Blog blog);

    // 按照id删除博客
    boolean deleteBlog(Long id);

    // 修改博客信息
    boolean updateBlog(Blog blog);

    // 添加博客浏览量
    boolean addBlogViewSum(Long id, Integer viewSum);

    // 按照id查找博客
    Blog findBlogById(Long id);

    // 查找最新的博客信息
    List<Blog> findNewBlog(int size);

    // 分页查找博客信息
    PageInfo<Blog> findBlogByPage(int pageNum, int pageSize);

    // 分页查询已经发布的博客信息
    PageInfo<Blog> findBlogOfPublished(int pageNum, int pageSize);

    // 按照博客名称进行模糊查询
    PageInfo<Blog> findBlogByTitleLike(String blogTitleLike, int pageNum, int pageSize);
}
