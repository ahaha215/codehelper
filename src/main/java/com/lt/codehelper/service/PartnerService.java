package com.lt.codehelper.service;

import com.github.pagehelper.PageInfo;
import com.lt.codehelper.entity.Partner;

import java.util.Map;

/**
 * @MethodName
 * @Description 心愿服务层接口
 * @Author lt
 * @Param
 * @return
 **/
public interface PartnerService {
    // 添加找寻伙伴
    boolean addPartner(Map partnerMap);

    // 删除找寻伙伴
    boolean removePartner(Long partnerId);

    // 按照id查找找寻伙伴
    Partner findPartnerById(Long partnerId);

    // 分页查询找寻伙伴
    PageInfo<Partner> findPartnerByPage(int pageNum, int pageSize);

    // 迷糊查询伙伴
    PageInfo<Partner> findPartnerByContentLike(String partnerContentLike, int pageNum, int pageSize);

    // 按照用户id查询伙伴
    PageInfo<Partner> findAllPartnerByUserId(long loginUserId, int pageNum, int pageSize);

    // 分页查询待审核的找寻伙伴
    PageInfo<Partner> findPendingAuditPartnerByPage(int pageNum, int pageSize);

    // 分页查询审核通过的找寻伙伴
    PageInfo<Partner> findPassAuditPartnerByPage(int pageNum, int pageSize);

    // 找寻伙伴审核
    boolean auditPartner(Long partnerId, String audit);
}
