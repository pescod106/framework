package com.ltar.base.helper;

import java.util.Collection;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/19
 * @version: 1.0.0
 */
public class CollectionHelper {

    /**
     * get the only element of collection
     * if collection's size great than 1 ,throw exception
     *
     * @param col
     * @param <T>
     * @return
     */
    public static <T> T getMostOneFromCollection(Collection<T> col) {
        if (col == null || col.size() == 0) {
            return null;
        }
        if (col.size() > 1) {
            throw new RuntimeException("Size of collection is more than 1.");
        }
        return col.iterator().next();
    }
}
