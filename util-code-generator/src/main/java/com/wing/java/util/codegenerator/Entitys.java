package com.wing.java.util.codegenerator;

public class Entitys {
    String fieldName;
    String fieldType;
    String columnName;
    String orgcolumnName;
    String dataType;
    String comment;
    boolean isnullable = true;
    boolean isPk = false;
    boolean isadd = false;
    Integer maxlength;

    public String getOrgcolumnName() {
        return orgcolumnName;
    }

    public void setOrgcolumnName(String orgcolumnName) {
        this.orgcolumnName = orgcolumnName;
    }

    public boolean isIsadd() {
        return isadd;
    }

    public void setIsadd(boolean isadd) {
        this.isadd = isadd;
    }

    public Integer getMaxlength() {
        return maxlength;
    }

    public void setMaxlength(Integer maxlength) {
        this.maxlength = maxlength;
    }

    public boolean isIsnullable() {
        return isnullable;
    }

    public void setIsnullable(boolean isnullable) {
        this.isnullable = isnullable;
    }

    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean isPk) {
        this.isPk = isPk;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    @Override
    public String toString() {
        return "Entitys [fieldName=" + fieldName + ", fieldType=" + fieldType + ", columnName=" + columnName
                + ", orgcolumnName=" + orgcolumnName + ", dataType=" + dataType + ", comment=" + comment
                + ", isnullable=" + isnullable + ", isPk=" + isPk + ", isadd=" + isadd + ", maxlength=" + maxlength
                + "]";
    }

}