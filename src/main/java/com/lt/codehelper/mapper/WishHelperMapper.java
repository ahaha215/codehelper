package com.lt.codehelper.mapper;

import com.lt.codehelper.entity.WishHelper;
import org.apache.logging.log4j.message.LoggerNameAwareMessage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @MethodName
 * @Description 心愿帮助持久层接口
 * @Author lt
 * @Param
 * @return
 **/
@Repository
public interface WishHelperMapper {
    // 添加心愿帮助
    boolean insertWishHelper(Map wishHelperMap);

    // 删除心愿
    boolean deleteWishHelper(Long wishHelperId);

    // 查询心愿的所有帮助
    List<WishHelper> selectAllWishHelper(Long wishId);

    // 查询帮忙实现愿望的用户
    Long selectUserIdByWishHelperId(Long wishHelperId);
}
