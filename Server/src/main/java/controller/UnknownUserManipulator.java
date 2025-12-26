package controller;

import model.Request;

public class UnknownUserManipulator extends ServerManipulator {
    private UnknownUserHandler unknownUser;

    @Override
    public UnknownUserHandler instantiate(Request request) {
        this.unknownUser = new UnknownUserHandler(request);
        return unknownUser;
    }
}