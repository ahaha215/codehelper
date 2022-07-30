package com.lt.codehelper.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Type;
import com.lt.codehelper.entity.User;
import com.lt.codehelper.entity.Partner;
import com.lt.codehelper.mapper.*;
import com.lt.codehelper.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PartnerServiceImpl
 * @Description 心愿服务层实现类
 * @Author lt
 * @Version 1.0
 **/
@Service
public class PartnerServiceImpl implements PartnerService {

    // 注入 partnerMapper
    @Autowired
    PartnerMapper partnerMapper;

    // 注入 partnerToTypeMapper;
    @Autowired
    PartnerToTypeMapper partnerToTypeMapper;

    // 注入 typeMapper
    @Autowired
    TypeMapper typeMapper;

    // 注入 userMapper
    @Autowired
    UserMapper userMapper;

    /**
     * @MethodName addPartner
     * @Description 添加伙伴
     * @Author lt
     * @Param [Partner, PartnerTypes]
     * @return boolean
     **/
    @Override
    public boolean addPartner(Map partnerMap) {
        // 插入基本数据
        boolean insertPartnerFlag = partnerMapper.insertPartner(partnerMap);
        // 插入心愿类型基本关系
        boolean insertPartnerToTypeFlag = true;
        Partner partner = (Partner) partnerMap.get("partner");
        Long partnerId = partner.getId();
        String[] partnerTypeNames = (String[]) partnerMap.get("partnerTypeNames");
        for (String partnerTypeName : partnerTypeNames) {
            Long typeId = typeMapper.selectTypeByName(partnerTypeName).getId();
            boolean flag = partnerToTypeMapper.insertPartnerToType(partnerId, typeId);
            if (!flag){
                insertPartnerToTypeFlag = false;
            }
        }

        return insertPartnerFlag || insertPartnerToTypeFlag;
    }

    /**
     * @MethodName removePartner
     * @Description 删除伙伴
     * @Author lt
     * @Param [PartnerId]
     * @return boolean
     **/
    @Override
    public boolean removePartner(Long partnerId) {
        boolean flag = partnerMapper.deletePartner(partnerId);
        return flag;
    }

    /**
     * @MethodName findPartnerById
     * @Description 按照Id查找伙伴
     * @Author lt
     * @Param [PartnerId]
     * @return com.lt.codehelper.entity.Partner
     **/
    @Override
    public Partner findPartnerById(Long partnerId) {
        Partner partner = partnerMapper.selectPartnerById(partnerId);
        // 封装心愿的用户信息
        Long userId = partnerMapper.selectUserIdByPartnerId(partnerId);
        User user = userMapper.selectUserById(userId);
        partner.setUser(user);

        // 封装心愿的类型信息
        List<Long> typeIds = partnerToTypeMapper.selectAllTypeIdByPartnerId(partnerId);
        List<Type> partnerTypes = new ArrayList<>();
        for (Long typeId : typeIds) {
            Type type = typeMapper.selectTypeById(typeId);
            partnerTypes.add(type);
        }
        partner.setPartnerTypes(partnerTypes);
        return partner;
    }

    /**
     * @MethodName findPartnerPage
     * @Description 分页查询伙伴
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Partner>
     **/
    @Override
    public PageInfo<Partner> findPartnerByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Partner> partners = partnerMapper.selectAllPartner();
        for (Partner partner : partners) {
            // 封装User
            Long userId = partnerMapper.selectUserIdByPartnerId(partner.getId());
            partner.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = partnerToTypeMapper.selectAllTypeIdByPartnerId(partner.getId());
            List<Type> partnerTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                partnerTypes.add(type);
            }
            partner.setPartnerTypes(partnerTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Partner> page = new PageInfo<Partner>(partners);
        return page;
    }

    /**
     * @MethodName findPartnerByContentLike
     * @Description 模糊查询伙伴
     * @Author lt
     * @Param [partnerContentLike, pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Partner>
     **/
    @Override
    public PageInfo<Partner> findPartnerByContentLike(String partnerContentLike, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Partner> partners = partnerMapper.selectPartnerByLike("%"+partnerContentLike+"%");
        for (Partner partner : partners) {
            // 封装User
            Long userId = partnerMapper.selectUserIdByPartnerId(partner.getId());
            partner.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = partnerToTypeMapper.selectAllTypeIdByPartnerId(partner.getId());
            List<Type> partnerTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                partnerTypes.add(type);
            }
            partner.setPartnerTypes(partnerTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Partner> page = new PageInfo<Partner>(partners);
        return page;
    }

    /**
     * @MethodName findAllPartnerByUserId
     * @Description 按照用户id查询伙伴
     * @Author lt
     * @Param [loginUserId, pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Partner>
     **/
    @Override
    public PageInfo<Partner> findAllPartnerByUserId(long loginUserId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Partner> partners = partnerMapper.selectAllPartnerByUserId(loginUserId);
        for (Partner partner : partners) {
            // 封装User
            Long userId = partnerMapper.selectUserIdByPartnerId(partner.getId());
            partner.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = partnerToTypeMapper.selectAllTypeIdByPartnerId(partner.getId());
            List<Type> partnerTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                partnerTypes.add(type);
            }
            partner.setPartnerTypes(partnerTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Partner> page = new PageInfo<Partner>(partners);
        return page;
    }

    /**
     * @MethodName findPendingAuditPartnerByPage
     * @Description 分页查询待审核的伙伴
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Partner>
     **/
    @Override
    public PageInfo<Partner> findPendingAuditPartnerByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Partner> partners = partnerMapper.selectAllPendingAuditPartner();
        for (Partner partner : partners) {
            // 封装User
            Long userId = partnerMapper.selectUserIdByPartnerId(partner.getId());
            partner.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = partnerToTypeMapper.selectAllTypeIdByPartnerId(partner.getId());
            List<Type> partnerTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                partnerTypes.add(type);
            }
            partner.setPartnerTypes(partnerTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Partner> page = new PageInfo<Partner>(partners);
        return page;
    }

    /**
     * @MethodName findPassAuditPartnerByPage
     * @Description 分页查询审核通过的伙伴
     * @Author lt
     * @Param [pageNum, pageSize]
     * @return com.github.pagehelper.PageInfo<com.lt.codehelper.entity.Partner>
     **/
    @Override
    public PageInfo<Partner> findPassAuditPartnerByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Partner> partners = partnerMapper.selectAllPassAuditPartner();
        for (Partner partner : partners) {
            // 封装User
            Long userId = partnerMapper.selectUserIdByPartnerId(partner.getId());
            partner.setUser(userMapper.selectUserById(userId));
            // 封装 ResourceTypes
            List<Long> typeIds = partnerToTypeMapper.selectAllTypeIdByPartnerId(partner.getId());
            List<Type> partnerTypes = new ArrayList<>();
            for (Long typeId : typeIds) {
                Type type = typeMapper.selectTypeById(typeId);
                partnerTypes.add(type);
            }
            partner.setPartnerTypes(partnerTypes);
        }
        // 使用PageInfo对结果进行包装
        PageInfo<Partner> page = new PageInfo<Partner>(partners);
        return page;
    }

    /**
     * @MethodName auditPartner
     * @Description 审核伙伴
     * @Author lt
     * @Param [PartnerId, audit]
     * @return boolean
     **/
    @Override
    public boolean auditPartner(Long partnerId, String audit) {
        boolean flag = partnerMapper.updatePartnerAudit(partnerId, audit);
        return flag;
    }

}
