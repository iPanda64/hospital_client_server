package controller;

import model.Request;
import model.UseCaseType;

import java.io.IOException;

public class LoginController extends BaseViewController {

    private final Client client;

    public LoginController(Client client) {
        super(client, "login.html");
        this.client = client;
    }

    public void login(String username, String password) {
        try {
            Request loginRequest = new Request(UseCaseType.Login, username + ":" + password);
            client.sendToServer(loginRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}