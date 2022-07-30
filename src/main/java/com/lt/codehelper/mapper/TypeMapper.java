package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.Type;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @MethodName
 * @Description 类型持久层接口
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface TypeMapper {
    // 插入类型
    boolean insertType(Type type);

    // 按照id删除类型
    boolean deleteTypeById(Long id);

    // 修改类型信息
    boolean updateType(Type type);

    // 查询所有类型信息
    List<Type> selectAllType();

    // 按照id查找
    Type selectTypeById(Long id);

    // 按照名称查找
    Type selectTypeByName(String typeName);
}
