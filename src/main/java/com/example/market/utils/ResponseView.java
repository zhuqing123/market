package com.example.market.utils;

/**
 * Author:ZhuQing
 * Date:2017/11/14 16:07
 */
public class ResponseView<T> {

    private int status = 0;

    private String message="success";

    private T data;

    public ResponseView() {
    }

    public ResponseView(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseView(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
