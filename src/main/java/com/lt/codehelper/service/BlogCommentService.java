package com.lt.codehelper.service;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.BlogComment;

import java.util.List;

/**
 * @MethodName
 * @Description 博客评论服务层接口
 * @Author lt
 * @Param
 * @return
 **/
public interface BlogCommentService {
    boolean addBlogComment(BlogComment blogComment);
    boolean removeBlogComment(Long commentId);
    PageInfo<BlogComment> findAllBlogCommentByPage(Long blogId, int pageNum, int pageSize);
}
