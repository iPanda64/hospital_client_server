package controller.handler;

import controller.ClientController;
import controller.ViewAccountController;
import model.Response;
import model.UtilizatorType;
import model.ViewType;

public class CreateAccountHandler implements ResponseHandler {

    private final ClientController clientController;

    public CreateAccountHandler (ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void handle(Response<?> response) {
        if(!response.isSuccessful())clientController.showError("Couldn't create account");
        else clientController.showSuccess("Account successfully created");
        clientController.switchView(ViewType.LOGIN);
    }
}
