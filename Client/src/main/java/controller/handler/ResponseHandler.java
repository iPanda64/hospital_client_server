package controller.handler;

import model.Response;

public interface ResponseHandler {
    void handle(Response<?> response);
}
