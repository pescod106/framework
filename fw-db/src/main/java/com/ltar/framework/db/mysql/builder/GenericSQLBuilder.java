package com.ltar.framework.db.mysql.builder;

import java.util.ArrayList;
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



    @Override
    public ISQLBuilder clone() {
        return null;
    }

    @Override
    public void setTable(String table) {

    }

    @Override
    public Object[] getParameters() {
        return new Object[0];
    }

    @Override
    public String toCountSQL() {
        return null;
    }

    @Override
    public String toSQL() {
        return null;
    }

    @Override
    public String escapeColumn(String column) {
        return null;
    }

    @Override
    public void tag(String var) {

    }

    @Override
    public void addParam(Object... var) {

    }

    @Override
    public void where(String column, Object... values) {

    }

    @Override
    public void select(String... columns) {

    }

    @Override
    public void clearSelect() {

    }

    @Override
    public void join(String joinStr) {

    }

    @Override
    public void groupBy(String groupByField) {

    }

    @Override
    public void having(String havingField) {

    }

    @Override
    public void orderBy(String orderByField) {

    }

    @Override
    public void limit(Integer offset, Integer count) {

    }

    @Override
    public boolean hasLimit() {
        return false;
    }

    @Override
    public void lock() {

    }

    @Override
    public String getLastInsertIdSQL() {
        return null;
    }
}
