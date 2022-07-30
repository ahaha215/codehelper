package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.ResourceComment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @MethodName
 * @Description 资源评论持久层接口
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface ResourceCommentMapper {
    // 添加评论
    boolean insertResourceComment(Map resourceCommentMap);

    // 删除评论
    boolean deleteResourceComment(Long commentId);

    // 查询对应资源的评论
    List<ResourceComment> selectAllResourceComment(Long resourceId);

    // 查看相应的评论用户
    Long selectUserIdByResourceCommentId(Long resourceCommentId);
}
