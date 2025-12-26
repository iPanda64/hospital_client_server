package controller;

import model.Request;

public class AsistentManipulator extends ServerManipulator {
    private AsistentHandler asistent;

    public AsistentManipulator() {
    }

    @Override
    public AsistentHandler instantiate(Request request) {
        this.asistent = new AsistentHandler(request);
        return asistent;
    }
}