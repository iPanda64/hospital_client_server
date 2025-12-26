package controller;

import model.Request;
import model.Response;

public abstract class AbstractHandler {
    private Request request;
    private Response response=null;
    public Response getResponse() {
        return response;
    }
    public AbstractHandler(Request request) {
        this.request = request;
    }

}
