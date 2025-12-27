package controller.handler;

import controller.ClientController;
import model.Response;
import model.ViewType;

public class LoginResponseHandler implements ResponseHandler {
    private final ClientController clientController;

    public LoginResponseHandler(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void handle(Response<?> response) {
        System.out.println(response.getId());
        clientController.switchView(ViewType.CHAT);
    }
}