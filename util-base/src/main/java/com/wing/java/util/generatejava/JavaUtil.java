package com.wing.java.util.generatejava;

import com.wing.java.util.file.FileUtil;
import com.wing.java.util.SqlUtil;
import com.wing.java.util.StringUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;


/**
 * 根据TableInfo对象 生成Java源文件
 */
public class JavaUtil {

    /**
     * 根据表字段生成Java类
     */
    public static void tableToEntity(TableInfo tableInfo) {
        String path = GenerateJavaConstant.getEntityJavaDir(tableInfo.getTableName());
        FileUtil.mkDir(path);

        PrintWriter pw = null;
        try {
            String content = getStringCode(tableInfo);
            pw = new PrintWriter(new FileWriter(path + File.separator + StringUtil.transCamel(tableInfo.getTableName()) + ".java"));
            pw.println(content);
            pw.flush();
            pw.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            if (pw != null) {
                try {
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param tableInfo
     * @return
     */
    private static String getStringCode(TableInfo tableInfo) {
        processPackageImport(tableInfo);
        return parse(tableInfo);
    }

    private static void processPackageImport(TableInfo tableInfo) {
        List<String> colTypes = tableInfo.getColTypes();
        List<Integer> colScale = tableInfo.getColScale();
        for (int i = 0; i < colTypes.size(); i++) {
            if ("image".equalsIgnoreCase(colTypes.get(i)) || "text".equalsIgnoreCase(colTypes.get(i))) {
                tableInfo.setImportSql(true);
            }
            if (colScale.get(i) >= 0) {
                tableInfo.setImportMath(true);
            }
        }
    }

    /**
     * 解析处理(生成实体类主体代码)
     */
    private static String parse(TableInfo tableInfo) {
        StringBuffer sb = new StringBuffer();
        sb.append("package " + GenerateJavaConstant.DEFAULT_PACKAGE + ";\r\n");
        sb.append("import java.io.Serializable;\r\n");
        sb.append("import java.util.*;\r\n");

        if (tableInfo.isImportSql()) {
            sb.append("import java.sql.*;\r\n");
        }
        if (tableInfo.isImportMath()) {
            sb.append("import java.math.*;\r\n");
        }

        String tableName = tableInfo.getTableName();
        List<String> colNames = tableInfo.getColNames();
        List<String> colTypes = tableInfo.getColTypes();
        List<Integer> colSizes = tableInfo.getColSizes();
        List<Integer> colScale = tableInfo.getColScale();


        //表注释
        processColnames(sb, tableName, colNames, colTypes, colSizes, colScale);
        sb.append("public class " + StringUtil.transCamel(tableInfo.getTableName()) + " implements Serializable {\r\n");
        //attributes
        processAllAttrs(sb, tableName, colNames, colTypes, colSizes, colScale);
        //getter/setter
        processAllMethod(sb, tableName, colNames, colTypes, colSizes, colScale);
        //equals
        processEqualsMethod(sb, tableName, colNames, colTypes, colSizes, colScale);
        //hashcode
        processHashCodeMethod(sb, tableName, colNames, colTypes, colSizes, colScale);
        //toString
        processToStringMethod(sb, tableName, colNames, colTypes, colSizes, colScale);

        sb.append("}\r\n");
        return sb.toString();
    }

    /**
     * 处理列名,把空格下划线'_'去掉,同时把下划线后的首字母大写
     * 要是整个列在3个字符及以内,则去掉'_'后,不把"_"后首字母大写.
     * 同时把数据库列名,列类型写到注释中以便查看,
     *
     * @param sb
     */
    private static void processColnames(StringBuffer sb, String tableName, List<String> colNames, List<String> colTypes, List<Integer> colSizes, List<Integer> colScale) {
        sb.append("\r\n/** " + tableName + "\r\n");
        String colsiz = "";

        for (int i = 0; i < colNames.size(); i++) {
            colNames.set(i, colNames.get(i).toLowerCase());
            colsiz = colSizes.get(i) <= 0 ? "" : (colScale.get(i) <= 0 ? "(" + colSizes.get(i) + ")" : "(" + colSizes.get(i) + "," + colScale.get(i) + ")");
            sb.append("  *\t" + colNames.get(i).toUpperCase() + "	" + colTypes.get(i).toUpperCase() + colsiz + "\r\n");
            char[] ch = colNames.get(i).toCharArray();
            char c = 'a';
            if (ch.length > 3) {
                for (int j = 0; j < ch.length; j++) {
                    c = ch[j];
                    if (c == '_') {
                        if (ch[j + 1] >= 'a' && ch[j + 1] <= 'z') {
                            ch[j + 1] = (char) (ch[j + 1] - 32);
                        }
                    }
                }
            }
            String str = new String(ch);
            colNames.set(i, str.replaceAll("_", ""));
        }
        sb.append("*/\r\n");
    }

    /**
     * 解析输出属性
     *
     * @return
     */
    private static void processAllAttrs(StringBuffer sb, String tableName, List<String> colNames, List<String> colTypes, List<Integer> colSizes, List<Integer> colScale) {
        sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");
        for (int i = 0; i < colNames.size(); i++) {
            sb.append("\tprivate " + SqlUtil.oracleSqlType2JavaType(colTypes.get(i), colScale.get(i), colSizes.get(i)) + " " + colNames.get(i) + ";\r\n");
        }
        sb.append("\r\n");
    }

    /**
     * 生成所有的getter/setter方法
     *
     * @param sb
     */
    private static void processAllMethod(StringBuffer sb, String tableName, List<String> colNames, List<String> colTypes, List<Integer> colSizes, List<Integer> colScale) {
        for (int i = 0; i < colNames.size(); i++) {
            sb.append("\tpublic void set" + StringUtil.transCapital(colNames.get(i)) + "("
                    + SqlUtil.oracleSqlType2JavaType(colTypes.get(i), colScale.get(i), colSizes.get(i)) + " " + colNames.get(i)
                    + "){\r\n");
            sb.append("\t\tthis." + colNames.get(i) + "=" + colNames.get(i) + ";\r\n");
            sb.append("\t}\r\n");

            sb.append("\tpublic " + SqlUtil.oracleSqlType2JavaType(colTypes.get(i), colScale.get(i), colSizes.get(i)) + " get" + StringUtil.transCapital(colNames.get(i)) + "(){\r\n");
            sb.append("\t\treturn " + colNames.get(i) + ";\r\n");
            sb.append("\t}\r\n");
        }
    }

    /**
     * @param sb
     */
    private static void processHashCodeMethod(StringBuffer sb, String tableName, List<String> colNames, List<String> colTypes, List<Integer> colSizes, List<Integer> colScale) {
        /**
         *     @Override
         *     public int hashCode() {
         *         return Objects.hash(getId(), getName(), getAge(), getEmail(), getClassId(), getHobby());
         *     }
         */
        sb.append("\r\n")
                .append("\t@Override\r\n")
                .append("\tpublic int hashCode() {\r\n");
        sb.append("\t\treturn Objects.hash(");
        StringBuffer ss = new StringBuffer();
        for (String colname : colNames) {
            ss.append(colname).append(",");
        }
        StringBuffer buffer = ss.deleteCharAt(ss.length() - 1);
        sb.append(buffer);
        sb.append(");\r\n");
        sb.append("\t}\r\n");
    }

    /**
     * 处理eques方法
     *
     * @param sb
     */
    private static void processEqualsMethod(StringBuffer sb, String tableName, List<String> colNames, List<String> colTypes, List<Integer> colSizes, List<Integer> colScale) {
        /**
         * 	public boolean equals(Object o) {
         * 		if (this == o) return true;
         * 		if (!(o instanceof TCom)) return false;
         * 		TCom tCom = (TCom) o;
         * 		return
         * 		Objects.equals(getId(), tCom.getId()) &&
         * 				Objects.equals(getName(), tCom.getName()) &&
         * 				Objects.equals(getAge(), tCom.getAge()) &&
         * 				Objects.equals(getEmail(), tCom.getEmail()) &&
         * 				Objects.equals(getClassId(), tCom.getClassId()) &&
         * 				Objects.equals(getHobby(), tCom.getHobby());
         *        }
         */
        String name = StringUtil.transCamel(tableName);
        String var = StringUtil.transLower(name);

        sb.append("\r\n")
                .append("\t@Override\r\n")
                .append("\tpublic boolean equals(Object o) {\r\n")
                .append("\t\tif (this == o) return true;\r\n")
                .append("\t\tif (o == null || getClass() != o.getClass()) return false;\r\n")
                .append("\t\t" + name + " " + var + " = (" + name + ") o;\r\n")
                .append("\t\treturn\r\n");
        StringBuffer ss = new StringBuffer();
        for (String colname : colNames) {
            ss.append("\t\t\tObjects.equals(" + colname + ", " + var + "." + colname + ") &&\r\n");
        }
        StringBuffer delete = ss.delete(ss.length() - 5, ss.length() - 1);
        sb.append(delete);
        sb.append(";\r\n");
        sb.append("\t}\r\n");
    }

    private static void processToStringMethod(StringBuffer sb, String tableName, List<String> colNames, List<String> colTypes, List<Integer> colSizes, List<Integer> colScale) {
        String name = StringUtil.transCamel(tableName);

        sb.append("\r\n")
                .append("\t@Override\r\n")
                .append("\tpublic String toString() {\r\n");
        sb.append("\t\treturn \"" + name + "{");
        StringBuffer ss = new StringBuffer();
        for (String colname : colNames) {
            ss.append(colname + "=\"+" + colname).append("+\",");
        }
        StringBuffer buffer = ss.deleteCharAt(ss.length() - 1);
        sb.append(buffer);
        sb.append("}\";\r\n");
        sb.append("\t}\r\n");

    }




    public static void main(String[] args) {
        TableInfo tableInfo = new TableInfo();
//        tableInfo.setTableName("sys_user");
//        tableInfo.setColNames(new String[]{"id", "USER_NAME", "ctime", "sex", "LENGTH", "ramark"});
//        tableInfo.setColTypes(new String[] {"number", "varchar2", "date", "boolean", "decimal", "varchar"});
//        tableInfo.setColSizes(new Integer[] {20, 200, 0, 0, 10, 20});
//        tableInfo.setColScale(new Integer[] {0, 0, 0, 0, 2, 0});

        JavaUtil.tableToEntity(tableInfo);
    }
}
