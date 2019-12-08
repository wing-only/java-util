package com.wing.java.util.param.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wing
 * @create 2018-09-01 19:14
 * 自定义日期转换器
 */
public class DateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(source);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
