package controller;

import model.Request;
import model.UseCaseType;
import model.ViewType;

import java.io.IOException;

public class CreateAccountController extends BaseViewController {

    private final Client client;

    public CreateAccountController (ClientController clientController, Client client) {
        super(clientController, client, "createAccount.html");
        this.client = client;
    }

    public void getAccount(String username, String parola, String nume, String prenume, String numar_de_telefon, String email, String data_nasterii){
        try {
            Request loginRequest = new Request(UseCaseType.CreateAccount, username + " " + parola + " "+nume+" "+prenume+" "+numar_de_telefon+" "+email);
            client.sendToServer(loginRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void goBackToLogin() {
        clientController.switchView(ViewType.LOGIN);
    }
}