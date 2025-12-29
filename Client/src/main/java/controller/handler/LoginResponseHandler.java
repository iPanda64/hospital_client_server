package controller.handler;

import controller.ClientController;
import controller.ViewAccountController;
import model.Response;
import model.UtilizatorType;
import model.ViewType;

public class LoginResponseHandler implements ResponseHandler {
    private final ClientController clientController;

    public LoginResponseHandler(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void handle(Response<?> response) {
        if(!response.isSuccessful()){
            clientController.showError("Username or password incorrect");
            return;
        }
        ClientController.setCurrentId(response.getId());
        ViewAccountController.setUtilizatorTip((UtilizatorType)response.getResponseObject());
        clientController.switchView(ViewType.VIEW_ACCOUNT);
    }
}