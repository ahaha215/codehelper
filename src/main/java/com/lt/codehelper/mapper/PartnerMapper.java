package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.Partner;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @MethodName
 * @Description 找伙伴持久层接口
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface PartnerMapper {
    // 添加伙伴
    boolean insertPartner(Map partnerMap);

    // 删除伙伴
    boolean deletePartner(Long partnerId);

    // 查询所有的伙伴
    List<Partner> selectAllPartner();

    // 模糊查询伙伴
    List<Partner> selectPartnerByLike(String partnerContentLike);

    // 按照用户id查询伙伴
    List<Partner> selectAllPartnerByUserId(long loginUserId);

    // 查询所有待审核的伙伴
    List<Partner> selectAllPendingAuditPartner();

    // 查询所有审核通过的伙伴
    List<Partner> selectAllPassAuditPartner();

    // 按照id查询伙伴
    Partner selectPartnerById(Long partnerId);

    // 根据伙伴id查找用户id
    Long selectUserIdByPartnerId(Long partnerId);

    // 设置伙伴状态
    boolean updatePartnerAudit(Long partnerId, String audit);
}
