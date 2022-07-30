package com.lt.codehelper.service;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Type;

import java.util.List;

/**
 * @MethodName
 * @Description 类型服务层接口
 * @Author lt
 * @Param
 * @return
 **/
public interface TypeService {
    // 新增一个类型
    boolean addType(Type type);

    // 按照id删除类型
    boolean deleteType(Long id);

    // 修好用户信息
    boolean updateType(Type type);

    // 查询所有类型信息
    List<Type> findAllType();

    // 分页查询类型信息
    PageInfo<Type> findTypeByPage(int pageNum, int pageSize);

    // 按照id查找类型
    Type findTypeById(Long id);
}
