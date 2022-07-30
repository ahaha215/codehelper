package com.lt.codehelper.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ClassName FormatUtils
 * @Description 格式化工具类
 * @Author lt
 * @Version 1.0
 **/
public class FormatUtils {

    /**
     * @MethodName formatTime
     * @Description 格式化时间 ： yyyy-MM-dd HH:mm:ss
     * @Author lt
     * @Param [date]
     * @return java.lang.String
     **/
    public static String formatTime(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timeStr = simpleDateFormat.format(date);
        return timeStr;
    }
}
