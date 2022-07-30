package com.lt.codehelper.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @MethodName
 * @Description 心愿类型对应关系持久层接口
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface PartnerToTypeMapper {
    // 添加心愿类型对应关系
    boolean insertPartnerToType(Long partnerId, Long typeId);

    // 按照心愿id查询类型id
    List<Long> selectAllTypeIdByPartnerId(Long partnerId);
}
