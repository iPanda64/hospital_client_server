package controller;

import model.Request;

public class PacientManipulator extends ServerManipulator {
    private PacientHandler pacient;

    @Override
    public PacientHandler instantiate(Request request) {
        this.pacient = new PacientHandler(request);
        return pacient;
    }
}