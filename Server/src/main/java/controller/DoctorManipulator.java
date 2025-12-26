package controller;

import model.Request;

public class DoctorManipulator extends ServerManipulator {
    private DoctorHandler doctor;

    @Override
    public DoctorHandler instantiate(Request request) {
        this.doctor = new DoctorHandler(request);
        return doctor;
    }
}