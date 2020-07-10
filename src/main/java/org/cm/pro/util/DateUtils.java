package org.cm.pro.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hht
 * @description 时间工具
 * @date 2019/12/10
 */
public class DateUtils {

    /**
     * 时间转字符串
     * @param date 时间
     * @param pattern 时间格式
     * @return 结果
     */
    public static String forMatDate(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 字符串转日期
     * @param date 字符串
     * @param pattern 时间格式
     * @return 结果
     * @throws ParseException exception
     */
    public static Date forMatDate(String date, String pattern) throws ParseException {
        return new SimpleDateFormat(pattern).parse(date);
    }
}


