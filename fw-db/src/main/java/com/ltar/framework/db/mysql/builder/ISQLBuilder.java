package com.ltar.framework.db.mysql.builder;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/12/8
 * @version: 1.0.0
 */
public interface ISQLBuilder extends Cloneable {
    ISQLBuilder clone();

    void setTable(String table);

    Object[] getParameters();

    String toCountSQL();

    String toSQL();

    String escapeColumn(String column);

    void tag(String var);

    void addParam(Object... var);

    void where(String column, Object... values);

    void select(String... columns);

    void clearSelect();

    void join(String joinStr);

    void groupBy(String groupByField);

    void having(String havingField);

    void orderBy(String orderByField);

    void limit(Integer offset, Integer count);

    boolean hasLimit();

    void lock();

    String getLastInsertIdSQL();
}
