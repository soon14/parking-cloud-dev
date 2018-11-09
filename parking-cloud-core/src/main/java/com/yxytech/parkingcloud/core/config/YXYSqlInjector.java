package com.yxytech.parkingcloud.core.config;

import com.baomidou.mybatisplus.entity.TableFieldInfo;
import com.baomidou.mybatisplus.entity.TableInfo;
import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.session.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YXYSqlInjector extends AutoSqlInjector {
    private static final Map<String, List> needToInjectorMap = new HashMap<String, List>() {{
        put(" /*org_id:org_id*/ ", new ArrayList<String>() {{
            add("yxy_org_area"); add("yxy_org_parking"); add("yxy_user"); add("yxy_user_account");
            add("yxy_fee_schema");
        }});
        put(" /*parking_id:parking_id*/ ", new ArrayList<String>() {{
            add("yxy_blacklist"); add("yxy_whitelist"); add("yxy_freelist"); add("yxy_charge_facility"); add("yxy_magnetic_device");
            add("yxy_video_monitor"); add("yxy_video_pile"); add("yxy_coupon_info");
            add("yxy_order_info"); add("yxy_order_transaction"); add("yxy_transaction_success"); add("yxy_parking_owner");
            add("yxy_parking_operator"); add("yxy_parking_amount_statistics"); add("yxy_parking_time_length_statistics");
            add("yxy_wechat_payment_setting");
        }});
        put(" /*parking_id:id*/ ", new ArrayList<String >() {{
            add("yxy_parking");
        }});
        put(" /*org_id:id*/ ", new ArrayList<String>() {{
            add("yxy_organzation");
        }});
    }};

    @Override
    public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        this.injectSelectIdNameMaps(mapperClass, modelClass, table);
    }

    private void injectSelectIdNameMaps(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        String sql = "<script>SELECT %s FROM %s %s</script>";
        sql = String.format(sql, "${ew.sqlSelect}", table.getTableName(), sqlWhereEntityWrapper(table));

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Map.class);
        this.addSelectMappedStatement(mapperClass, "selectIdNameMap", sqlSource, Map.class, table);
    }

    @Override
    protected void injectSelectByMapSql(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        SqlMethod sqlMethod = SqlMethod.SELECT_BY_MAP;
        String sql = String.format(sqlMethod.getSql(), sqlSelectColumns(table, false), table.getTableName(), sqlWhereByMap(table));

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, Map.class);
        this.addSelectMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource, modelClass, table);
    }

    @Override
    protected void injectSelectOneSql(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        SqlMethod sqlMethod = SqlMethod.SELECT_ONE;
        String sql = String.format(sqlMethod.getSql(), sqlSelectColumns(table, false), table.getTableName(), sqlWhere(table));
//        sql = addIsolateSqlHolder(sql, table.getTableName());
        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addSelectMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource, modelClass, table);
    }

    @Override
    protected void injectSelectCountSql(Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        SqlMethod sqlMethod = SqlMethod.SELECT_COUNT;
        String sql = String.format(sqlMethod.getSql(), table.getTableName(), sqlWhereEntityWrapper(table));

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addSelectMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource, Integer.class, null);
    }

    @Override
    protected void injectSelectListSql(SqlMethod sqlMethod, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        String sql = String.format(sqlMethod.getSql(), sqlSelectColumns(table, true), table.getTableName(),
                sqlWhereEntityWrapper(table));

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addSelectMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource, modelClass, table);
    }

    @Override
    protected void injectSelectMapsSql(SqlMethod sqlMethod, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        String sql = String.format(sqlMethod.getSql(), sqlSelectColumns(table, true), table.getTableName(),
                sqlWhereEntityWrapper(table));

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addSelectMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource, Map.class, table);
    }

    @Override
    protected void injectSelectObjsSql(SqlMethod sqlMethod, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        String sql = String.format(sqlMethod.getSql(), sqlSelectObjsColumns(table), table.getTableName(),
                sqlWhereEntityWrapper(table));

        SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        this.addSelectMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource, Object.class, table);
    }

    @Override
    protected void injectSelectByIdSql(boolean batch, Class<?> mapperClass, Class<?> modelClass, TableInfo table) {
        SqlMethod sqlMethod = SqlMethod.SELECT_BY_ID;
        SqlSource sqlSource;
        String sql;
        if (batch) {
            sqlMethod = SqlMethod.SELECT_BATCH_BY_IDS;
            StringBuilder ids = new StringBuilder();
            ids.append("\n<foreach item=\"item\" index=\"index\" collection=\"list\" separator=\",\">");
            ids.append("#{item}");
            ids.append("\n</foreach>");
            sql = String.format(sqlMethod.getSql(),
                    sqlSelectColumns(table, false), table.getTableName(), table.getKeyColumn(), ids.toString());
            sql = sql.replaceFirst("</script>", getDelimiter(table.getTableName()) + "</script>");
            sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
        } else {
            sql = String.format(sqlMethod.getSql(), sqlSelectColumns(table, false),
                    table.getTableName(), table.getKeyColumn(), table.getKeyProperty());
            sqlSource = new RawSqlSource(configuration, sql, Object.class);
        }
        this.addSelectMappedStatement(mapperClass, sqlMethod.getMethod(), sqlSource, modelClass, table);
    }

    private String getDelimiter(String tableName) {
        for (Map.Entry<String, List> map : needToInjectorMap.entrySet()) {
            if (map.getValue().contains(tableName)) {
                return map.getKey();
            }
        }

        return "";
    }

    @Override
    protected String sqlWhereEntityWrapper(TableInfo table) {
        String isolateSqlHolder = getDelimiter(table.getTableName());
        if (StringUtils.isEmpty(isolateSqlHolder)) {
            return super.sqlWhereEntityWrapper(table);
        }

        StringBuilder where = new StringBuilder(128);
        where.append("\n<where>");
        where.append("\n<if test=\"ew!=null\">");
        where.append("\n<if test=\"ew.entity!=null\">");
        if (StringUtils.isNotEmpty(table.getKeyProperty())) {
            where.append("\n<if test=\"ew.entity.").append(table.getKeyProperty()).append("!=null\">\n");
            where.append(table.getKeyColumn()).append("=#{ew.entity.").append(table.getKeyProperty()).append("}");
            where.append("\n</if>");
        }
        List<TableFieldInfo> fieldList = table.getFieldList();
        for (TableFieldInfo fieldInfo : fieldList) {
            where.append(convertIfTag(fieldInfo, "ew.entity.", false));
            where.append(" AND ").append(fieldInfo.getColumn()).append("=#{ew.entity.").append(fieldInfo.getEl()).append("}");
            where.append(convertIfTag(fieldInfo, true));
        }
        where.append("\n</if>");
        where.append("\n<if test=\"ew!=null and ew.sqlSegment!=null and ew.notEmptyOfWhere\">\n");
        where.append(isolateSqlHolder).append("${ew.sqlSegment}\n</if>");
        where.append("\n</if>");
        where.append("\n</where>");
        where.append("\n<if test=\"ew!=null and ew.sqlSegment!=null and ew.emptyOfWhere\">\n").append(isolateSqlHolder).append("${ew.sqlSegment}\n</if>");
        return where.toString();
    }
}
