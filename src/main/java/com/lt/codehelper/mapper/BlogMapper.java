package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.Blog;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @MethodName
 * @Description 博客持久层接口
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface BlogMapper {
    // 插入博客
    boolean insertBlog(Blog blog);

    // 删除博客
    boolean deleteBlogById(Long id);

    // 修改博客
    boolean updateBlog(Blog blog);

    // 更新博客浏览量
    boolean updateBlogViewSum(Long id, Integer viewSum);

    // 按照id查找博客
    Blog selectBlogById(Long id);

    // 查询最新的博客信息
    List<Blog> selectNewBlog(int size);

    // 查询所有博客信息
    List<Blog> selectAllBlog();

    // 查询所有发布的博客
    List<Blog> selectAllBlogOfPublished();

    // 模糊查询博客信息
    List<Blog> selectBlogByTitleLike(String blogTitleLike);
}
