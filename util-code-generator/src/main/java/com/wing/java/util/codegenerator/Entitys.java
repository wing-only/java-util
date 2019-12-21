package com.wing.java.util.codegenerator;

import lombok.Data;

@Data
public class Entitys {
    String fieldName;
    String fieldType;
    String columnName;
    String orgcolumnName;
    String dataType;
    Long maxlength;
    String comment;
    boolean nullAble = true;
    boolean pk = false;
    boolean add = false;
}