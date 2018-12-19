package com.ltar.framework.db.mysql.exception;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/12/8
 * @version: 1.0.0
 */
public class RecondNotFoundException extends RuntimeException {

    public RecondNotFoundException(String message) {
        super(message);
    }

    public RecondNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
