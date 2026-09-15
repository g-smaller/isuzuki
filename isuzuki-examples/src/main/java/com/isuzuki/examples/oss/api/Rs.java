package com.isuzuki.examples.oss.api;

public class Rs<T> {
    private static final String DEFAULT_SUCCESS_CODE = String.valueOf(200);
    private static final String DEFAULT_FAILURE_CODE = String.valueOf(500);

    private String code;
    private String msg;
    private boolean success;
    private T data;
    private Paging paging;


    public Rs(String code, String msg, boolean success, T data) {
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.data = data;
    }

    public Rs() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public static <T> Rs<T> success() {
        return success(null);
    }

    public static <T> Rs<T> success(T data) {
        return success(DEFAULT_SUCCESS_CODE, "success", data);
    }

    public static <T> Rs<T> success(String message, T data) {
        return success(DEFAULT_SUCCESS_CODE, message, data);
    }

    public static <T> Rs<T> success(int code, String message, T data) {
        return success(String.valueOf(code), message, data);
    }

    public static <T> Rs<T> success(String code, String message, T data) {
        return new Rs<>(code, message, Boolean.TRUE, data);
    }

    public static <T> Rs<T> error() {
        return error(DEFAULT_FAILURE_CODE, "error", null);
    }

    public static <T> Rs<T> error(String message) {
        return error(DEFAULT_FAILURE_CODE, message, null);
    }

    public static <T> Rs<T> error(int code, String message) {
        return error(String.valueOf(code), message, null);
    }

    public static <T> Rs<T> error(String code, String message) {
        return new Rs<>(code, message, Boolean.FALSE, null);
    }

    public static <T> Rs<T> error(T data) {
        return error(DEFAULT_FAILURE_CODE, "error", data);
    }

    public static <T> Rs<T> error(String message, T data) {
        return error(DEFAULT_FAILURE_CODE, message, data);
    }

    public static <T> Rs<T> error(int code, String message, T data) {
        return error(String.valueOf(code), message, data);
    }

    public static <T> Rs<T> error(String code, String message, T data) {
        return new Rs<>(code, message, Boolean.FALSE, data);
    }
}
