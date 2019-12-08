package com.wing.java.util.plugin.mybatis;

import com.wing.java.util.CommonUtil;
import com.wing.java.util.ReflectUtil;
import com.wing.java.util.param.page.Page;
import com.wing.java.util.plugin.jdbc.dialect.Dialect;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import javax.xml.bind.PropertyException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Mybatis的分页查询插件，通过拦截StatementHandler的prepare方法来实现。
 * 只有在参数列表中包括Page类型的参数时才进行分页查询。
 */
@SuppressWarnings("all")
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PagePlugin implements Interceptor {

    private static Dialect dialect = null;      // 数据库方言
    private static String pageSqlId = ".*select.*Page$";       // mybaits的数据库xml映射文件中需要拦截的ID(正则匹配)

    public Object intercept(Invocation ivk) throws Throwable {
        if (ivk.getTarget() instanceof RoutingStatementHandler) {
            RoutingStatementHandler statementHandler = (RoutingStatementHandler) ivk.getTarget();
            BaseStatementHandler delegate = (BaseStatementHandler) ReflectUtil.getValueByFieldName(statementHandler, "delegate");
            MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getValueByFieldName(delegate, "mappedStatement");

            /**
             * 步骤1：通过sqlId来区分是否需要分页．select*Page
             * 步骤2：传入的参数是否有page参数，如果有，则分页
             */
            // 拦截需要分页的SQL
            if (mappedStatement.getId().matches(pageSqlId)) {
                BoundSql boundSql = delegate.getBoundSql();
                // 分页SQL<select>中parameterType属性对应的实体参数，即Mapper接口中执行分页方法的参数,该参数不得为空
                Object parameterObject = boundSql.getParameterObject();
                if (parameterObject == null) {
                    return ivk.proceed();
                } else {
                    Page page = null;
                    if (parameterObject instanceof Page) {
                        page = (Page) parameterObject;
                    } else {
                        return ivk.proceed();
                    }

                    String sql = boundSql.getSql();
                    PreparedStatement countStmt = null;
                    ResultSet rs = null;
                    try {
                        if (page.getPage().isShowCount()) {
                            Connection connection = (Connection) ivk.getArgs()[0];
                            String countSql = "select count(1) from (" + sql + ") tmp_count"; // 记录统计
                            countStmt = connection.prepareStatement(countSql);
                            ReflectUtil.setValueByFieldName(boundSql, "sql", countSql);
                            DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
                            parameterHandler.setParameters(countStmt);
                            rs = countStmt.executeQuery();
                            long count = 0;
                            if (rs.next()) {
                                count = ((Number) rs.getObject(1)).longValue();
                            }
                            page.getPage().setTotalCount(count);
                            page.getPage().setTotalPage(((count % page.getPage().getPageSize()) > 0) ? (count / page.getPage().getPageSize() + 1) : (count / page.getPage().getPageSize()));
                        }
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            if (countStmt != null) {
                                countStmt.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    String pageSql = generatePagesSql(sql, page);
                    // 将分页sql语句反射回BoundSql
                    ReflectUtil.setValueByFieldName(boundSql, "sql", pageSql);
                }
            }
        }
        return ivk.proceed();
    }

    /**
     * 根据数据库方言，生成特定的分页sql
     *
     * @param sql
     * @param page
     * @return
     */
    private String generatePagesSql(String sql, Page page) {
        if (page != null && dialect != null) {
            //pageIndex 默认是从1，而已数据库是从0开始计算的．所以(page.getPageIndex()-1)
            long pageIndex = page.getPage().getPageIndex();
            return dialect.getLimitString(sql, (pageIndex <= 0 ? 0 : pageIndex - 1) * page.getPage().getPageSize(), page.getPage().getPageSize());
        }
        return sql;
    }

    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    public void setProperties(Properties p) {
        // 数据库方言
        String dialect = "";
        dialect = p.getProperty("dialect");
        if (CommonUtil.isEmpty(dialect)) {
            try {
                throw new PropertyException("database dialect property is not found");
            } catch (PropertyException e) {
                e.printStackTrace();
            }
        } else {
            try {
                PagePlugin.dialect = (Dialect) Class.forName(dialect).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException(dialect + ", init fail\n" + e);
            }
        }
        //根据id来区分是否需要分页
//        pageSqlId = p.getProperty("pageSqlId");
//        if (CommonUtil.isEmpty(pageSqlId)) {
//            try {
//                throw new PropertyException("pageSqlId property is not found");
//            } catch (PropertyException e) {
//                e.printStackTrace();
//            }
//        }
    }
}
