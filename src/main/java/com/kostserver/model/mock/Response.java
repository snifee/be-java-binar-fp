package com.kostserver.model.mock;

public class Response {

    public int status;
    public String message;
    public Object data;
    public Object error;

    public Response(int status, String message, Object data, Object error) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.error = error;
    }
}
