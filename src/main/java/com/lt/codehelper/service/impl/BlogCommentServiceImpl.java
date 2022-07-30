package com.lt.codehelper.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.BlogComment;
import com.lt.codehelper.mapper.BlogCommentMapper;
import com.lt.codehelper.service.BlogCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName BlogCommentServiceImpl
 * @Description TODO
 * @Author lt
 * @Version 1.0
 **/
@Service
public class BlogCommentServiceImpl implements BlogCommentService {

    // 注入 blogCommentMapper
    @Autowired
    BlogCommentMapper blogCommentMapper;

    /**
     * @MethodName addBlogComment
     * @Description 添加博客评论
     * @Author lt
     * @Param [blogComment]
     * @return boolean
     **/
    @Override
    public boolean addBlogComment(BlogComment blogComment) {
        boolean insertBlogCommentFlag = blogCommentMapper.insertBlogComment(blogComment);
        return insertBlogCommentFlag;
    }

    /**
     * @MethodName removeBlogComment
     * @Description 删除博客评论
     * @Author lt
     * @Param [id, blogId, userId]
     * @return boolean
     **/
    @Override
    public boolean removeBlogComment(Long commentId) {
        boolean deleteBlogCommentFlag = blogCommentMapper.deleteBlogComment(commentId);
        return deleteBlogCommentFlag;
    }

    /**
     * @MethodName findAllBlogComment
     * @Description 分页查询博客评论
     * @Author lt
     * @Param [blogId, pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.BlogComment>
     **/
    @Override
    public PageInfo<BlogComment> findAllBlogCommentByPage(Long blogId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<BlogComment> blogComments = blogCommentMapper.selectAllBlogComment(blogId);
        // 使用PageInfo对结果进行包装
        PageInfo<BlogComment> page = new PageInfo<BlogComment>(blogComments);
        return page;
    }
}
