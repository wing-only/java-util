package com.wing.java.util.codegenerator;

import lombok.Data;

@Data
public class Entitys {
    String fieldName;   //java 属性名，驼峰
    String fieldType;
    String columnName;  //数据库字段名， 下划线
    String orgcolumnName;
    String dataType;
    Long maxlength;
    String comment;
    boolean nullAble = true;
    boolean pk = false;
    boolean add = false;
}