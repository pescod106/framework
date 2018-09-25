package com.ltar.db.mysql;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/2
 * @version: ${version}
 */
public class DB implements Comparable<String> {
    private String url;
    private boolean equals;

    public void test() {
        this.equals = url.equals("");
        boolean equals = this.equals;
    }

    public int compareTo(String o) {
        return o.length();

    }
}
