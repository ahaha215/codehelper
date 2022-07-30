package com.lt.codehelper.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @MethodName
 * @Description 资源类型关系持久层接口
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface ResourceToTypeMapper {
    // 插入资源类型关系
    boolean insertResourceToTypeMapper(Long resourceId, Long typeId);

    // 根据 resourceId 查询 typeId
    List<Long> selectAllTypeIdByResourceId(Long resourceId);
}
