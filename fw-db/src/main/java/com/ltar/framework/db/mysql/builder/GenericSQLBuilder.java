package com.ltar.framework.db.mysql.builder;

import com.google.common.base.Joiner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/12/8
 * @version: 1.0.0
 */
public class GenericSQLBuilder implements ISQLBuilder {
    protected String table;
    protected String tag;

    protected List<String> columns = new ArrayList<>();
    protected List<String> joins = new ArrayList<>();
    protected List<String> conditions = new ArrayList<>();
    protected List<String> groupBys = new ArrayList<>();
    protected List<String> havings = new ArrayList<>();
    protected List<String> orderBys = new ArrayList<>();

    protected boolean lockForUpdate;

    protected Integer offset;
    protected Integer rowCount;
    protected List<Object> parameters = new ArrayList<>();


    @Override
    public ISQLBuilder clone() {
        try {
            GenericSQLBuilder clone = (GenericSQLBuilder) super.clone();
            clone.columns = new ArrayList<>(this.columns);
            clone.joins = new ArrayList<>(this.joins);
            clone.conditions = new ArrayList<>(this.conditions);
            clone.groupBys = new ArrayList<>(this.groupBys);
            clone.havings = new ArrayList<>(this.havings);
            clone.orderBys = new ArrayList<>(this.orderBys);
            clone.parameters = new ArrayList<>(((this.parameters)));
            clone.lockForUpdate = this.lockForUpdate;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setTable(String table) {
        this.table = table;
    }

    @Override
    public Object[] getParameters() {
        return this.parameters.toArray();
    }

    @Override
    public String toCountSQL() {
        if (this.groupBys.isEmpty()) {
            StringBuilder sql = new StringBuilder("SELECT ");
            this.fillTag(sql);
            sql.append(" COUNT(*) FROM ")
                    .append(this.table)
                    .append(this.toJoinSQL())
                    .append(this.toConditionSQL());
            return sql.toString();
        } else {
            String countSQL = this.toSql(true, false, false);
            int countSqlLength = countSQL.length();
            countSQL = countSQL.replace("sql_cache", "");
            StringBuilder sql = new StringBuilder(countSQL.length() + 45);
            if (countSqlLength == countSQL.length()) {
                sql.append("SELECT COUNT(*) FROM (");
            } else {
                sql.append("SELECT SQL_CACHE COUNT(*) FROM (");
            }
            sql.append(countSQL).append(") TABLE_TMP");
            return sql.toString();
        }
    }

    @Override
    public String toSQL() {
        return this.toSql(true, true, true);
    }

    @Override
    public String escapeColumn(String column) {
        return column;
    }

    @Override
    public void tag(String tag) {
        this.tag = tag;
    }

    @Override
    public void addParam(Object... params) {
        this.parameters.addAll(Arrays.asList(params));
    }

    @Override
    public void where(String column, Object... values) {
        this.conditions.add(column);
        this.addParam(values);
    }

    @Override
    public void select(String... columns) {
        this.columns.addAll(Arrays.asList(columns));
    }

    @Override
    public void clearSelect() {
        this.columns.clear();
    }

    @Override
    public void join(String joinStr) {
        this.joins.add(joinStr);
    }

    @Override
    public void groupBy(String groupByField) {
        this.groupBys.add(groupByField);
    }

    @Override
    public void having(String havingField) {
        this.havings.add(havingField);
    }

    @Override
    public void orderBy(String orderByField) {
        this.orderBys.add(orderByField);
    }

    @Override
    public void limit(Integer offset, Integer count) {
        this.offset = offset;
        this.rowCount = count;
    }

    @Override
    public boolean hasLimit() {
        return null == this.offset;
    }

    @Override
    public void lock() {
        this.lockForUpdate = true;
    }

    @Override
    public String getLastInsertIdSQL() {
        return "SELECT LAST_INSERT_ID()";
    }

    protected String toSql(boolean withGroup, boolean withOrderBy, boolean withLimit) {
        StringBuilder sql = new StringBuilder("SELECT ");
        this.fillTag(sql);
        sql.append(this.toSelectColumns())
                .append(" FROM ")
                .append(this.table)
                .append(this.toJoinSQL())
                .append(this.toConditionSQL());

        if (withGroup) {
            sql.append(this.toGroupBySQL()).append(this.toHavingSQL());
        }
        if (withOrderBy) {
            sql.append(this.toOrderBySQL());
        }
        if (withLimit) {
            sql.append(this.toLimitSQL());
        }
        if (this.lockForUpdate) {
            sql.append(" FOR UPDATE");
        }

        return sql.toString();
    }

    private String toSelectColumns() {
        return this.columns.isEmpty() ? "*" : Joiner.on(", ").join(this.conditions);
    }

    private String toConditionSQL() {
        StringBuilder sql = new StringBuilder();
        if (!this.conditions.isEmpty()) {
            sql.append(" WHERE ");

            for (int i = 0; i < this.conditions.size(); i++) {
                if (i > 0) {
                    sql.append(" AND ");
                }
                sql.append(this.conditions.get(i));
            }
        }
        return sql.toString();
    }

    private String toJoinSQL() {
        return this.joins.isEmpty() ? "\n" : Joiner.on('\n').join(this.joins);
    }

    private String toGroupBySQL() {
        return this.groupBys.isEmpty() ? "" : " GROUP BY " + Joiner.on(", ").join(this.groupBys);
    }

    private String toHavingSQL() {
        return this.havings.isEmpty() ? "" : " HAVING " + Joiner.on(" AND ").join(this.havings);
    }

    private String toOrderBySQL() {
        return this.orderBys.isEmpty() ? "" : " ORDER BY " + Joiner.on(", ").join(this.orderBys);
    }

    private String toLimitSQL() {
        return null == this.rowCount ? "" : (null == offset ? " limit " + this.rowCount : "LIMIT " + this.offset + ", " + this.rowCount);
    }

    private void fillTag(StringBuilder sql) {
        if (StringUtils.isEmpty(this.tag)) {
            sql.append("/* tag: ").append(this.tag).append(" */ ");
        }
    }
}
