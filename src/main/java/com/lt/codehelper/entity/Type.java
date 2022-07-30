package com.lt.codehelper.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName Type
 * @Description 类型实体类
 * @Author lt
 * @Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Type {
    // 类型id
    private Long id;
    // 类型名称
    private String typeName;
}
