package com.lt.codehelper.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Type;
import com.lt.codehelper.mapper.TypeMapper;
import com.lt.codehelper.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName TypeServiceImpl
 * @Description 类型服务层实现类
 * @Author lt
 * @Version 1.0
 **/
@Service
public class TypeServiceImpl implements TypeService{

    // 注入typeMapper
    @Autowired
    TypeMapper typeMapper;

    /**
     * @MethodName addType
     * @Description 新增类型
     * @Author lt
     * @Param [type]
     * @return boolean
     **/
    @Override
    public boolean addType(Type type) {
        boolean typeAddFlag = typeMapper.insertType(type);
        return typeAddFlag;
    }

    /**
     * @MethodName deleteType
     * @Description 删除类型
     * @Author lt
     * @Param [id]
     * @return boolean
     **/
    @Override
    public boolean deleteType(Long id) {
        boolean deleteTypeByIdFlag = typeMapper.deleteTypeById(id);
        return deleteTypeByIdFlag;
    }

    /**
     * @MethodName updateType
     * @Description 修好类型
     * @Author lt
     * @Param [type]
     * @return boolean
     **/
    @Override
    public boolean updateType(Type type) {
        boolean updateTypeFlag = typeMapper.updateType(type);
        return updateTypeFlag;
    }

    /**
     * @MethodName findAllType
     * @Description 查询所有的类型信息
     * @Author lt
     * @Param []
     * @return java.util.List<com.lt.codehelper.entity.Type>
     **/
    @Override
    public List<Type> findAllType() {
        List<Type> types = typeMapper.selectAllType();
        return types;
    }

    /**
     * @MethodName findTypeByPage
     * @Description 分页查询类型信息
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Type>
     **/
    @Override
    public PageInfo<Type> findTypeByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Type> list = typeMapper.selectAllType();
        // 使用PageInfo对结果进行包装
        PageInfo<Type> page = new PageInfo<Type>(list);
        return page;
    }

    /**
     * @MethodName findTypeById
     * @Description 按照id查找类型
     * @Author lt
     * @Param [id]
     * @return com.lt.codehelper.entity.Type
     **/
    @Override
    public Type findTypeById(Long id) {
        Type type = typeMapper.selectTypeById(id);
        return type;
    }
}
