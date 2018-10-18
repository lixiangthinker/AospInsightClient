package com.tonybuilder.aospinsight.net.model;


import androidx.annotation.NonNull;

public class Api<T> {
    public static final String VERSION = "1.0.0";
    public static final int ERROR_CODE_NONE = 200;
    public static final int ERROR_CODE_RESOURCE_NOT_FOUND = 404;
    public static final int ERROR_CODE_INTERNAL_ERROR = 500;
    public static final int ERROR_CODE_PARAM_NOT_VALID = 501;

    private int resultCode;
    private String version;
    private T payload;
    private String message;

    private PagingInfo pageInfo;

    public Api(T payload, int resultCode, String message, String version, PagingInfo pageInfo) {
        this.resultCode = resultCode;
        this.version = version;
        this.payload = payload;
        this.message = message;
        this.pageInfo = pageInfo;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PagingInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PagingInfo pageInfo) {
        this.pageInfo = pageInfo;
    }
    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + resultCode + ",");
        sb.append(" " + version + ",");
        sb.append(" " + pageInfo + ",");
        sb.append(" " + message + "]");
        return sb.toString();
    }
}
