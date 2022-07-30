package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @ClassName Partner
 * @Description 寻求伙伴实体类
 * @Author lt
 * @Version 1.0
 **/
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Partner {
    // 找伙伴id
    private Long id;
    // 找伙伴内容
    private String content;
    // 找伙伴审核状态
    private String audit;
    // 找伙伴发布时间
    private Date createTime;
    // 找伙伴用户
    private User user;
    // 类型
    List<Type> partnerTypes;
}
