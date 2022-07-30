package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @MethodName
 * @Description 编程资源持久层接口
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface ResourceMapper {
    // 新增资源
    boolean insertResource(Map resourceMap);

    // 查询所有资源信息
    List<Resource> selectAllResource();

    // 查询所有审核通过的资源信息
    List<Resource> selectAllPassAuditResource();

    // 查询所有未审核的资源信息
    List<Resource> selectAllPendingAuditResource();

    // 查询资源的用户id
    Long selectUserIdByResourceId(Long id);

    // 查询资源
    Resource selectResourceById(Long resourceId);

    // 按照时间查询资源
    List<Resource> selectNewResource(int size);

    // 模糊查询资源
    List<Resource> selectResourceByResourceNameLike(String resourceNameLike);

    // 按照用户id查询资源
    List<Resource> selectAllResourceByUserId(long loginUserId);

    // 审核资源
    boolean updateResourceAudit(Long resourceId, String audit);

    // 删除资源
    boolean deleteResource(Long resourceId);

    // 增加资源的浏览量
    boolean updateResourceSum(Long resourceId, int viewSum);
}
