package com.ltar.net.dto;

import com.ltar.net.constant.HttpStatusEnum;
import lombok.*;
import org.apache.http.Header;

import java.io.Serializable;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/16
 * @version: 1.0.0
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponseEntity implements Serializable {

    /**
     * 通信是否成功，只要是请求出去有响应回来就是true,不是代表业务上的成功与否
     */
    private boolean success = false;

    /**
     * 返回状态码
     */
    private HttpStatusEnum statusCode;

    /**
     * 响应体
     */
    private String body;

    /**
     * 响应头
     */
    private Header[] headers;
}
