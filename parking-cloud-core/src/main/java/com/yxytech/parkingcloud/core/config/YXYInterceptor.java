package com.yxytech.parkingcloud.core.config;

import com.baomidou.mybatisplus.plugins.SqlParserHandler;
import com.baomidou.mybatisplus.toolkit.PluginUtils;
import com.yxytech.parkingcloud.commons.entity.UserContext;
import com.yxytech.parkingcloud.core.service.IUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class YXYInterceptor extends SqlParserHandler implements Interceptor, ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static final Pattern PATTERN_IOSLATE_TYPE = Pattern.compile("/\\*(org_id|parking_id):(.*)\\*/");
    private static final String REGEX_IOSLATE_SQL = "/\\*(.*)\\*/";
    private static final String REGEX_IS_START_WITH_AND = "(?i).*(where\\s{0,}/\\*(.*)\\*/).*";

    private Object sqlClean(Invocation invocation, MetaObject metaObject) throws InvocationTargetException, IllegalAccessException {
        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        String sql = boundSql.getSql();
        sql = sql.replaceAll("\n", "");

        String sqlWithWhereAndAnd = "(?i).*WHERE\\s*/\\*(org_id|parking_id):(.*)\\*/\\s*AND.*";
        if (sql.matches(sqlWithWhereAndAnd)) {
            sql = sql.replaceFirst("(?i)WHERE\\s*/\\*(org_id|parking_id):(.*)\\*/\\s*AND", "WHERE");
        } else {
            sql = sql.replaceFirst("(?i)WHERE\\s*/\\*(org_id|parking_id):(.*)\\*/", "");
        }
        metaObject.setValue("delegate.boundSql.sql", sql);

        return invocation.proceed();
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        StatementHandler statementHandler = (StatementHandler) PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(statementHandler);
        this.sqlParser(metaObject);

        // 判断是不是SELECT操作
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("delegate.mappedStatement");
        if (!SqlCommandType.SELECT.equals(mappedStatement.getSqlCommandType())) {
            return sqlClean(invocation, metaObject);
        }

        if (authentication == null) {
            return sqlClean(invocation, metaObject);
        }

        Object userContext = authentication.getPrincipal();

        if (userContext == null) {
            return  sqlClean(invocation, metaObject);
        }

        if (userContext instanceof String) {
            return sqlClean(invocation, metaObject);
        }
        UserContext context = (UserContext) userContext;

        BoundSql boundSql = (BoundSql) metaObject.getValue("delegate.boundSql");
        String sql = boundSql.getSql();
        sql = sql.replaceAll("\n", "");

        Matcher m = PATTERN_IOSLATE_TYPE.matcher(sql);
        if (!m.find()) {
            return sqlClean(invocation, metaObject);
        }

        Long userId = Long.valueOf(context.getUserId());
        IUserService userService = (IUserService)applicationContext.getBean("com.yxytech.parkingcloud.core.service.impl.UserServiceImpl");
        // 判断是否需要隔离
        if (!userService.needDataIsolate(userId)) {
            return sqlClean(invocation, metaObject);
        }

        String isolateType = m.group(1);
        String isolateSql = null;

        if ("org_id".equals(isolateType)) {
            isolateSql = userService.getIsolatedOrgsSql(userId);
        } else if ("parking_id".equals(isolateType)) {
            isolateSql = userService.getIsolatedParkingsSql(userId);
        }
        sql = renderIsolateSql(sql, isolateSql);

        metaObject.setValue("delegate.boundSql.sql", sql);

        return sqlClean(invocation, metaObject);
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private String dealSql(String sql, String replacement) {
        if (StringUtils.isEmpty(replacement)) {
            if (StringUtils.contains(sql, "AND")) {
                sql = sql.replaceAll("WHERE\\s{0,}##\\s{0,}(AND)?", "WHERE");
            } else {
                sql = sql.replaceAll("WHERE ##\\s{0,}", "");
            }
        } else {
            sql = sql.replaceAll("##", replacement);
        }

        return sql;
    }

    private String renderIsolateSql(String sql, String isolateSql) {
        if (sql.matches(REGEX_IS_START_WITH_AND)) {
            sql = sql.replaceFirst(PATTERN_IOSLATE_TYPE.toString(), "/* $2 " + isolateSql + " */");
            sql = sql.replaceFirst(REGEX_IOSLATE_SQL, "$1");

            return sql;
        }

        // 单独处理yxy_parking_statistics_info_view
        if (StringUtils.contains(sql, "yxy_parking_statistics_info_view")) {
            sql = sql.replaceFirst(PATTERN_IOSLATE_TYPE.toString(), "/* $2 " + isolateSql + " */");
            sql = sql.replaceFirst(REGEX_IOSLATE_SQL, "where $1");

            return sql;
        }

        if (StringUtils.contains(sql.toLowerCase(), "where")) {
            sql = sql.replaceFirst(PATTERN_IOSLATE_TYPE.toString(), "/* $2 " + isolateSql + " */");
            sql = sql.replaceFirst(REGEX_IOSLATE_SQL, "and $1");
        } else {
            sql = sql.replaceFirst(PATTERN_IOSLATE_TYPE.toString(), "/* $2 " + isolateSql + " */");
            sql = sql.replaceFirst(REGEX_IOSLATE_SQL, "where $1");
        }

        return sql;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
