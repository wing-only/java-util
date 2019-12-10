package com.wing.java.util.plugin.jdbc.dialect;

/**
 * MySQL方言
 */
public class MySQLDialect extends Dialect {

    public boolean supportsLimitOffset(){
        return true;
    }

    public boolean supportsLimit() {
        return true;
    }

    @Override
    public String getLimitString(String sql, long offset, String offsetPlaceholder, int limit, String limitPlaceholder) {
        if (offset > 0) {
            return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
        } else {
            return sql + " limit " + limitPlaceholder;
        }
    }

}
