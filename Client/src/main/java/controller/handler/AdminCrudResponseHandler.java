package controller.handler;

import controller.AdminCRUDController;
import controller.BaseViewController;
import controller.ClientController;
import model.Response;
import model.Utilizator;

import java.util.List;

public class AdminCrudResponseHandler implements ResponseHandler {
    private final ClientController clientController;
    public AdminCrudResponseHandler (ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void handle(Response<?> response) {
        BaseViewController activeController = clientController.getActiveController();

        if (!(activeController instanceof AdminCRUDController)) {
            return;
        }

        if (!response.isSuccessful()) {
            handleError(response);
            return;
        }

        handleSuccess(activeController, response);
    }

    private void handleSuccess(BaseViewController activeController, Response<?> response) {
        List<Utilizator> users = (List<Utilizator>) response.getResponseObject();
        ((AdminCRUDController) activeController).setAllUsers(users);
    }

    private void handleError(Response<?> response) {
        String errorMessage = "An unknown error occurred.";
        if (response.getRequest() != null) {
            switch (response.getRequest().getUseCaseType()) {
                case AdminViewAllAccounts:
                    errorMessage = "Fetching all Users Failed";
                    break;
                case AdminDeleteUser:
                    errorMessage = "Deleting user failed";
                    break;
                case AdminEditUser:
                    errorMessage = "Editing user failed";
                    break;
                case AdminAddUser:
                    errorMessage = "Adding user failed";
                    break;
            }
        }
        clientController.showError(errorMessage);
    }
}
