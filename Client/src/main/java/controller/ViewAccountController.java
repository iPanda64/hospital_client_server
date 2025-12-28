package controller;

import model.Request;
import model.UseCaseType;

public class ViewAccountController extends BaseViewController{
    private final Client client;

    public ViewAccountController(ClientController clientController, Client client) {
        super(clientController, client, "viewAccount.html");
        this.client = client;
    }
    public void getAccount() {
        Request viewAccount = new Request(UseCaseType.ViewAccount,ClientController.)
    }
}
