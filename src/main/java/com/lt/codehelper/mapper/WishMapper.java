package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.Wish;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @MethodName
 * @Description 心愿持久层接口
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface WishMapper {
    // 添加心愿
    boolean insertWish(Map wishMap);

    // 删除心愿
    boolean deleteWish(Long wishId);

    // 查询所有的心愿
    List<Wish> selectAllWish();

    // 查询所有待审核的心愿
    List<Wish> selectAllPendingAuditWish();

    // 查询所有审核通过的心愿
    List<Wish> selectAllPassAuditWish();

    // 模糊查询心愿
    List<Wish> selectWishByContentLike(String wishContentLike);

    // 按照用户id查询心愿
    List<Wish> selectAllWishByUserId(long loginUserId);

    // 按照id查询心愿
    Wish selectWishById(Long wishId);

    // 根据资源id查找用户id
    Long selectUserIdByWishId(Long wishId);

    // 设置心愿状态
    boolean updateWishAudit(Long wishId, String audit);
}
