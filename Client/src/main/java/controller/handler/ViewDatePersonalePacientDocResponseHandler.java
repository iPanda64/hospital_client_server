package controller.handler;

import controller.ClientController;
import controller.ViewDatePersonalePacientDocController;
import model.Response;
import model.Utilizator;

import java.util.List;

public class ViewDatePersonalePacientDocResponseHandler implements ResponseHandler {

    private final ClientController clientController;

    public ViewDatePersonalePacientDocResponseHandler(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void handle(Response<?> response) {
        if (response.isSuccessful()) {
            if (clientController.getActiveController() instanceof ViewDatePersonalePacientDocController) {
                ViewDatePersonalePacientDocController controller = (ViewDatePersonalePacientDocController) clientController.getActiveController();
                List<Utilizator> utilizatori = (List<Utilizator>) response.getResponseObject();
                controller.setDatePersonale(utilizatori);
            }
        } else {
            clientController.showError("Can't access personal data");
        }
    }
}