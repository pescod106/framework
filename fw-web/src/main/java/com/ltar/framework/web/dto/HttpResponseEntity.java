package com.ltar.framework.web.dto;

import com.ltar.framework.web.constant.HttpStatusEnum;
import lombok.ToString;
import org.apache.http.Header;

import java.io.Serializable;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/16
 * @version: 1.0.0
 */
@ToString
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

    public HttpResponseEntity() {
    }

    public HttpResponseEntity(boolean success, HttpStatusEnum statusCode, String body, Header[] headers) {
        this.success = success;
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HttpStatusEnum getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatusEnum statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = (Header[]) headers.clone();
    }
}
