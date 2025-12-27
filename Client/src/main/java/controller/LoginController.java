package controller;

import model.Request;
import model.UseCaseType;
import model.ViewType;

import java.io.IOException;

public class LoginController extends BaseViewController {

    private final Client client;

    public LoginController(ClientController clientController, Client client) {
        super(clientController, client, "login.html");
        this.client = client;
    }

    public void login(String username, String password) {
        try {
            Request loginRequest = new Request(UseCaseType.Login, username + " " + password);
            client.sendToServer(loginRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchToCreateAccount() {
        clientController.switchView(ViewType.CREATE_ACCOUNT);
    }
}