package com.ltar.db.redis;

import lombok.*;

import java.io.Serializable;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/20
 * @version: 1.0.0
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Student implements Serializable {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }
}
