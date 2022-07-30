package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.BlogComment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @MethodName
 * @Description 博客评论持久层接口
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface BlogCommentMapper {
    // 插入博客评论
    boolean insertBlogComment(BlogComment blogComment);
    // 删除博客评论
    boolean deleteBlogComment(Long commentId);
    // 查询博客信息
    List<BlogComment> selectAllBlogComment(Long blogId);
}
