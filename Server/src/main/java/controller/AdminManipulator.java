package controller;

import model.Request;

public class AdminManipulator extends ServerManipulator {
    private AdminHandler admin;

    @Override
    public AdminHandler instantiate(Request request) {
        this.admin = new AdminHandler(request);
        return admin;
    }
}