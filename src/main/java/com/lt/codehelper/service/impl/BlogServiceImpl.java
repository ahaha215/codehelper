package com.lt.codehelper.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Blog;
import com.lt.codehelper.entity.Type;
import com.lt.codehelper.mapper.BlogMapper;
import com.lt.codehelper.mapper.BlogToTypeMapper;
import com.lt.codehelper.mapper.TypeMapper;
import com.lt.codehelper.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BlogServiceImpl
 * @Description 博客服务层实现类
 * @Author lt
 * @Version 1.0
 **/
@Service
public class BlogServiceImpl implements BlogService {

    // 注入 BlogMapper
    @Autowired
    BlogMapper blogMapper;

    // 注入 TypeMapper
    @Autowired
    TypeMapper typeMapper;

    // 注入 BlogToTypeMapper
    @Autowired
    BlogToTypeMapper blogToTypeMapper;

    /**
     * @MethodName addBlog
     * @Description 添加博客
     * @Author lt
     * @Param [blog]
     * @return boolean
     **/
    @Override
    public boolean addBlog(Blog blog) {
        // 插入将博客的基本信息
        boolean insertBlogFlag = blogMapper.insertBlog(blog);
        // 插入博客类型关系
        List<String> blogTypes = blog.getBlogTypes();
        boolean insertBlogToTypeFlag = true;
        for (String blogType : blogTypes) {
            // 按照类型名称获取类型id
            Type type = typeMapper.selectTypeByName(blogType);
            // 插入将博客类型对应的关系
            boolean flag = blogToTypeMapper.insertBlogToType(blog.getId(),type.getId());
            if (!flag){
                insertBlogToTypeFlag = false;
                break;
            }
        }
        return insertBlogFlag || insertBlogToTypeFlag;
    }

    /**
     * @MethodName deleteBlogById
     * @Description 删除博客
     * @Author lt
     * @Param [id]
     * @return boolean
     **/
    @Override
    public boolean deleteBlog(Long id) {
        // 删除博客信息
        boolean deleteBlogFlag = blogMapper.deleteBlogById(id);
        return  deleteBlogFlag;
    }

    /**
     * @MethodName updateBlog
     * @Description 修改博客信息
     * @Author lt
     * @Param [blog]
     * @return boolean
     **/
    @Override
    public boolean updateBlog(Blog blog) {
        // 修改博客基本信息
        boolean updateBlogFlag = blogMapper.updateBlog(blog);
        // 修改博客类型信息
        List<String> blogTypes = blog.getBlogTypes();
        boolean insertBlogTypeFlag = true;
        // 删除博客原有的类型信息
        boolean deleteBlogTypeFlag = blogToTypeMapper.deleteBlogToType(blog.getId());
        // 添加博客类型信息
        for (String blogType : blogTypes) {
            Long typeId = typeMapper.selectTypeByName(blogType).getId();
            boolean flag = blogToTypeMapper.insertBlogToType(blog.getId(), typeId);
            if (!flag){
                insertBlogTypeFlag = false;
                break;
            }
        }
        return updateBlogFlag || deleteBlogTypeFlag || insertBlogTypeFlag;
    }

    /**
     * @MethodName addBlogViewSum
     * @Description 增加博客浏览量
     * @Author lt
     * @Param [id, viewSum]
     * @return boolean
     **/
    @Override
    public boolean addBlogViewSum(Long id, Integer viewSum) {
        boolean addBlogViewSumFlag = blogMapper.updateBlogViewSum(id, viewSum);
        return addBlogViewSumFlag;
    }

    /**
     * @MethodName findBlogById
     * @Description 按照id查找博客
     * @Author lt
     * @Param [id]
     * @return com.lt.codehelper.entity.Blog
     **/
    @Override
    public Blog findBlogById(Long id) {
        // 按照博客id查询博客信息
        Blog blog = blogMapper.selectBlogById(id);
        // 按照博客id查询博客对应的类型
        List<Long> allTypeId = blogToTypeMapper.selectAllBlogToTypeById(id);
        // 将类型信息封装到博客中
        List<String> allTypeName = new ArrayList<>();
        for (Long typeId : allTypeId) {
            // 根据类型id查询类型名称
            Type type = typeMapper.selectTypeById(typeId);
            allTypeName.add(type.getTypeName());
        }
        blog.setBlogTypes(allTypeName);
        return blog;
    }

    /**
     * @MethodName findNewBlog
     * @Description 查询最新的博客信息
     * @Author lt
     * @Param [size]
     * @return java.util.List<com.lt.codehelper.entity.Blog>
     **/
    @Override
    public List<Blog> findNewBlog(int size) {
        // 查询博客信息
        List<Blog> newBlogs = blogMapper.selectNewBlog(size);
        // 获取博客对应的类型
        for (Blog newBlog : newBlogs) {
            List<String> blogToTypes = new ArrayList<>();
            List<Long> blogToTypesId = blogToTypeMapper.selectAllBlogToTypeById(newBlog.getId());
            for (Long id : blogToTypesId) {
                String typeName = typeMapper.selectTypeById(id).getTypeName();
                blogToTypes.add(typeName);
            }
            newBlog.setBlogTypes(blogToTypes);
        }
        return newBlogs;
    }

    /**
     * @MethodName findBlogByPage
     * @Description 分页查询博客信息
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Blog>
     **/
    @Override
    public PageInfo<Blog> findBlogByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list = blogMapper.selectAllBlog();
        for (Blog blog : list) {
            // 按照博客id查询博客对应的类型
            List<Long> allTypeId = blogToTypeMapper.selectAllBlogToTypeById(blog.getId());
            // 将类型信息封装到博客中
            List<String> allTypeName = new ArrayList<>();
            for (Long typeId : allTypeId) {
                // 根据类型id查询类型名称
                Type type = typeMapper.selectTypeById(typeId);
                allTypeName.add(type.getTypeName());
            }
            blog.setBlogTypes(allTypeName);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Blog> page = new PageInfo<Blog>(list);
        return page;
    }

    /**
     * @MethodName findBlogOfPublished
     * @Description 分页查询发布的博客信息
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Blog>
     **/
    @Override
    public PageInfo<Blog> findBlogOfPublished(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list = blogMapper.selectAllBlogOfPublished();
        for (Blog blog : list) {
            // 按照博客id查询博客对应的类型
            List<Long> allTypeId = blogToTypeMapper.selectAllBlogToTypeById(blog.getId());
            // 将类型信息封装到博客中
            List<String> allTypeName = new ArrayList<>();
            for (Long typeId : allTypeId) {
                // 根据类型id查询类型名称
                Type type = typeMapper.selectTypeById(typeId);
                allTypeName.add(type.getTypeName());
            }
            blog.setBlogTypes(allTypeName);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Blog> page = new PageInfo<Blog>(list);
        return page;
    }

    /**
     * @MethodName findBlogByTitleLike
     * @Description 按照博客的标题进行模糊查询
     * @Author lt
     * @Param [blogTitleLike, pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Blog>
     **/
    @Override
    public PageInfo<Blog> findBlogByTitleLike(String blogTitleLike, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> list = blogMapper.selectBlogByTitleLike("%"+blogTitleLike+"%");
        for (Blog blog : list) {
            // 按照博客id查询博客对应的类型
            List<Long> allTypeId = blogToTypeMapper.selectAllBlogToTypeById(blog.getId());
            // 将类型信息封装到博客中
            List<String> allTypeName = new ArrayList<>();
            for (Long typeId : allTypeId) {
                // 根据类型id查询类型名称
                Type type = typeMapper.selectTypeById(typeId);
                allTypeName.add(type.getTypeName());
            }
            blog.setBlogTypes(allTypeName);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Blog> page = new PageInfo<Blog>(list);
        return page;
    }
}
