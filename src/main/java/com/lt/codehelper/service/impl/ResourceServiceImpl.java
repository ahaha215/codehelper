package com.lt.codehelper.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Resource;
import com.lt.codehelper.entity.ResourceComment;
import com.lt.codehelper.entity.Type;
import com.lt.codehelper.mapper.*;
import com.lt.codehelper.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName ResourceServiceImpl
 * @Description 资源服务层实现类
 * @Author lt
 * @Version 1.0
 **/
@Service
public class ResourceServiceImpl implements ResourceService {

    // 注入 ResourceMapper
    @Autowired
    ResourceMapper resourceMapper;

    // 注入 TypeMapper
    @Autowired
    TypeMapper typeMapper;

    // 注入 ResourceToTypeMapper
    @Autowired
    ResourceToTypeMapper resourceToTypeMapper;

    // 注入 UserMapper
    @Autowired
    UserMapper userMapper;

    // 注入 ResourceCommentMapper
    @Autowired
    ResourceCommentMapper resourceCommentMapper;


    /**
     * @MethodName addResource
     * @Description 添加资源
     * @Author lt
     * @Param [resource]
     * @return boolean
     **/
    @Override
    public boolean addResource(Map resourceMap) {
        // 插入资源基本信息
        boolean insertResourceFlag = resourceMapper.insertResource(resourceMap);
        // 插入资源类型关系
        Resource resource = (Resource) resourceMap.get("resource");
        List<String> resourceTypes = resource.getResourceTypes();
        boolean insertResourceToTypeFlag = true;
        for (String resourceType : resourceTypes) {
            // 按照类型名称获取类型id
            Type type = typeMapper.selectTypeByName(resourceType);
            // 插入将博客类型对应的关系
            boolean flag = resourceToTypeMapper.insertResourceToTypeMapper(resource.getId(),type.getId());
            if (!flag){
                insertResourceToTypeFlag = false;
                break;
            }
        }
        return insertResourceFlag || insertResourceToTypeFlag;
    }

    /**
     * @MethodName auditResource
     * @Description 资源审核
     * @Author lt
     * @Param [resourceId, audit]
     * @return boolean
     **/
    @Override
    public boolean auditResource(Long resourceId, String audit) {
        boolean flag = resourceMapper.updateResourceAudit(resourceId, audit);
        return flag;
    }

    /**
     * @MethodName removeResource
     * @Description 删除资源
     * @Author lt
     * @Param [resourceId]
     * @return boolean
     **/
    @Override
    public boolean removeResource(Long resourceId) {
        boolean flag = resourceMapper.deleteResource(resourceId);
        return flag;
    }

    /**
     * @MethodName findResourceById
     * @Description 按照id查找资源
     * @Author lt
     * @Param [resourceId]
     * @return com.lt.codehelper.entity.Resource
     **/
    @Override
    public Resource findResourceById(Long resourceId) {
        Resource resource = resourceMapper.selectResourceById(resourceId);
        // 封装资源用户
        Long userId = resourceMapper.selectUserIdByResourceId(resourceId);
        resource.setUser(userMapper.selectUserById(userId));
        // 封装资源类型
        List<Long> typeIds = resourceToTypeMapper.selectAllTypeIdByResourceId(resourceId);
        List<String> typeNames = new ArrayList<>();
        for (Long typeId : typeIds) {
            Type type = typeMapper.selectTypeById(typeId);
            typeNames.add(type.getTypeName());
        }
        resource.setResourceTypes(typeNames);
        return resource;
    }

    /**
     * @MethodName findNewResource
     * @Description 按照发布的时间查询最新的资源
     * @Author lt
     * @Param [size]
     * @return java.util.List<com.lt.codehelper.entity.Resource>
     **/
    @Override
    public List<Resource> findNewResource(int size) {
        List<Resource> resources = resourceMapper.selectNewResource(size);
        for (Resource resource : resources) {
            // 封装User
            Long userId = resourceMapper.selectUserIdByResourceId(resource.getId());
            resource.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = resourceToTypeMapper.selectAllTypeIdByResourceId(resource.getId());
            List<String> resourceTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                resourceTypes.add(type.getTypeName());
            }
            resource.setResourceTypes(resourceTypes);
        }
        return resources;
    }

    /**
     * @MethodName findResourceByPage
     * @Description 分页查询资源信息
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Resource>
     **/
    @Override
    public PageInfo<Resource> findResourceByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Resource> resources = resourceMapper.selectAllResource();
        for (Resource resource : resources) {
            // 封装User
            Long userId = resourceMapper.selectUserIdByResourceId(resource.getId());
            resource.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = resourceToTypeMapper.selectAllTypeIdByResourceId(resource.getId());
            List<String> resourceTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                resourceTypes.add(type.getTypeName());
            }
            resource.setResourceTypes(resourceTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Resource> page = new PageInfo<Resource>(resources);
        return page;
    }

    /**
     * @MethodName findAllPassAuditResourceByPage
     * @Description 分页查找审核通过的资源信息
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Resource>
     **/
    @Override
    public PageInfo<Resource> findAllPassAuditResourceByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Resource> resources = resourceMapper.selectAllPassAuditResource();
        for (Resource resource : resources) {
            // 封装User
            Long userId = resourceMapper.selectUserIdByResourceId(resource.getId());
            resource.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = resourceToTypeMapper.selectAllTypeIdByResourceId(resource.getId());
            List<String> resourceTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                resourceTypes.add(type.getTypeName());
            }
            resource.setResourceTypes(resourceTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Resource> page = new PageInfo<Resource>(resources);
        return page;
    }

    /**
     * @MethodName findAllPendingAuditResourceByPage
     * @Description 分页查询待审核的资源
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Resource>
     **/
    @Override
    public PageInfo<Resource> findAllPendingAuditResourceByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Resource> resources = resourceMapper.selectAllPendingAuditResource();
        for (Resource resource : resources) {
            // 封装User
            Long userId = resourceMapper.selectUserIdByResourceId(resource.getId());
            resource.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = resourceToTypeMapper.selectAllTypeIdByResourceId(resource.getId());
            List<String> resourceTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                resourceTypes.add(type.getTypeName());
            }
            resource.setResourceTypes(resourceTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Resource> page = new PageInfo<Resource>(resources);
        return page;
    }

    /**
     * @MethodName findResourceByResourceNameLike
     * @Description 按照资源名称进行模糊查询
     * @Author lt
     * @Param [resourceNameLike, pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Resource>
     **/
    @Override
    public PageInfo<Resource> findResourceByResourceNameLike(String resourceNameLike, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Resource> resources = resourceMapper.selectResourceByResourceNameLike("%"+resourceNameLike+"%");
        for (Resource resource : resources) {
            // 封装User
            Long userId = resourceMapper.selectUserIdByResourceId(resource.getId());
            resource.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = resourceToTypeMapper.selectAllTypeIdByResourceId(resource.getId());
            List<String> resourceTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                resourceTypes.add(type.getTypeName());
            }
            resource.setResourceTypes(resourceTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Resource> page = new PageInfo<Resource>(resources);
        return page;
    }

    /**
     * @MethodName findAllResourceByUserId
     * @Description 按照当用户的id查询资源
     * @Author lt
     * @Param [loginUserId, pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Resource>
     **/
    @Override
    public PageInfo<Resource> findAllResourceByUserId(long loginUserId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Resource> resources = resourceMapper.selectAllResourceByUserId(loginUserId);
        for (Resource resource : resources) {
            // 封装User
            Long userId = resourceMapper.selectUserIdByResourceId(resource.getId());
            resource.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = resourceToTypeMapper.selectAllTypeIdByResourceId(resource.getId());
            List<String> resourceTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                resourceTypes.add(type.getTypeName());
            }
            resource.setResourceTypes(resourceTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Resource> page = new PageInfo<Resource>(resources);
        return page;
    }

    /**
     * @MethodName addResourceViewSum
     * @Description 增加资源的浏览量
     * @Author lt
     * @Param [resourceId, viewSum]
     * @return boolean
     **/
    @Override
    public boolean addResourceViewSum(Long resourceId, int viewSum) {
        boolean flag = resourceMapper.updateResourceSum(resourceId, viewSum);
        return flag;
    }

    /**
     * @MethodName addResourceComment
     * @Description 添加对资源的评论
     * @Author lt
     * @Param [resourceCommentMap]
     * @return boolean
     **/
    @Override
    public boolean addResourceComment(Map resourceCommentMap) {
        boolean flag = resourceCommentMapper.insertResourceComment(resourceCommentMap);
        return flag;
    }

    /**
     * @MethodName removeResourceComment
     * @Description 删除评论
     * @Author lt
     * @Param [commentId]
     * @return boolean
     **/
    @Override
    public boolean removeResourceComment(Long commentId) {
        boolean flag = resourceCommentMapper.deleteResourceComment(commentId);
        return flag;
    }

    /**
     * @MethodName findAllResourceCommentByPage
     * @Description 分页查询资源评论信息
     * @Author lt
     * @Param [resourceId, pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.ResourceComment>
     **/
    @Override
    public PageInfo<ResourceComment> findAllResourceCommentByPage(Long resourceId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<ResourceComment> resourceComments = resourceCommentMapper.selectAllResourceComment(resourceId);
        for (ResourceComment resourceComment : resourceComments) {
            // 封装User
            Long userId = resourceCommentMapper.selectUserIdByResourceCommentId(resourceComment.getId());
            resourceComment.setUser(userMapper.selectUserById(userId));
            // 封装 Resource
            Resource resource = resourceMapper.selectResourceById(resourceId);
            resourceComment.setResource(resource);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<ResourceComment> page = new PageInfo<ResourceComment>(resourceComments);
        return page;
    }
}
