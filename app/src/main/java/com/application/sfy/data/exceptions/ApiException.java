package com.application.mxm.soundtracks.data.exceptions;


import com.application.mxm.soundtracks.R;

public class ApiException extends RuntimeException {

    private int code = 0;
    private String body = "";


    private ApiException(int code, String body) {
        super(body);
        this.code = code;
        this.body = body;
    }

    private ApiException(String message, Throwable cause, int code, String body) {
        super(message, cause);
        this.code = code;
        this.body = body;
    }

    public static ApiException create(int httpStatus, String body) {
        return new ApiException(httpStatus, body);
    }

    public static ApiException create(String message, Throwable cause, int httpStatus, String body) {
        return new ApiException(message, cause, httpStatus, body);
    }

    public int getCode() {
        return code;
    }


    public String getBody() {
        return body;
    }


    public int getErrorString() {
        return R.string.server_error;
    }

}