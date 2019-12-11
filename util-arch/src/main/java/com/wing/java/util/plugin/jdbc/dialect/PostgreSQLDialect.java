package com.wing.java.util.plugin.jdbc.dialect;

/**
 * @author badqiu
 */
public class PostgreSQLDialect extends Dialect {

    public boolean supportsLimit() {
        return true;
    }

    public boolean supportsLimitOffset() {
        return true;
    }

    public String getLimitString(String sql, long offset,
                                 String offsetPlaceholder, long limit, String limitPlaceholder) {
        return new StringBuffer(sql.length() + 20)
                .append(sql)
                .append(offset > 0 ? " limit " + limitPlaceholder + " offset " + offsetPlaceholder : " limit " + limitPlaceholder)
                .toString();
    }
}
