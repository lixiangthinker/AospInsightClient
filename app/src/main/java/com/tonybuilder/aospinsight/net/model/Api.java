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

    public Api(T payload, int resultCode, String message, String version) {
        this.resultCode = resultCode;
        this.version = version;
        this.payload = payload;
        this.message = message;
    }

    public Api ok(T payload) {
        return new Api(payload, Api.ERROR_CODE_NONE, "OK",Api.VERSION);
    }

    public static Api resourceNotFound(String message) {
        return new Api(null, Api.ERROR_CODE_RESOURCE_NOT_FOUND, message, Api.VERSION);
    }

    public static Api internalError(String message) {
        return new Api(null, Api.ERROR_CODE_INTERNAL_ERROR, message, Api.VERSION);
    }

    public static Api paramNotValid(String message) {
        return new Api(null, Api.ERROR_CODE_PARAM_NOT_VALID, message, Api.VERSION);
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

    @NonNull
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + resultCode + ",");
        sb.append(" " + version + ",");
        sb.append(" " + message + "]");
        return sb.toString();
    }
}
