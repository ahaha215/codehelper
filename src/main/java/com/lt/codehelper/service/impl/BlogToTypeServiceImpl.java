package com.lt.codehelper.service.impl;

import com.lt.codehelper.mapper.BlogToTypeMapper;
import com.lt.codehelper.service.BlogToTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName BlogToTypeServiceImpl
 * @Description 博客类型关系服务层
 * @Author lt
 * @Version 1.0
 **/
@Service
public class BlogToTypeServiceImpl implements BlogToTypeService {

    // 注入 BlogToTypeMapper
    @Autowired
    BlogToTypeMapper blogToTypeMapper;

    /**
     * @MethodName findBlogSumByTypeId
     * @Description 按照博客类型统计博客数量
     * @Author lt
     * @Param [typeId]
     * @return int
     **/
    @Override
    public int findBlogSumByTypeId(Long typeId) {
        return blogToTypeMapper.selectBlogSumByTypeId(typeId);
    }
}
