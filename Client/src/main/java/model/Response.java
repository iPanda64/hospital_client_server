package model;

import java.io.Serializable;

public class Response<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Request request; // The original request this response is for
    private int id;
    private T responseObject; // The actual response object
    private boolean success; // To indicate if the operation was successful

    public Response(Request request, int id, T responseObject, boolean success) {
        this.request = request;
        this.id = id;
        this.responseObject = responseObject;
        this.success = success;
    }

    public Request getRequest() {
        return request;
    }

    public int getId() {
        return id;
    }

    public T getResponseObject() {
        return responseObject;
    }

    public boolean isSuccessful() {
        return success;
    }

    @Override
    public String toString() {
        return "Response{" +
               "request=" + request +
               ", id=" + id +
               ", responseObject=" + responseObject +
               ", success=" + success +
               '}';
    }
}
