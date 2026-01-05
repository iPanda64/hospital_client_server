package controller.handler;

import controller.ClientController;
import controller.ViewFisaMedicalaPacientDocController;
import model.*;

import java.util.HashMap;
import java.util.List;

public class ViewFisaMedicalaPacientDocHandler implements ResponseHandler {

    private final ClientController clientController;

    public ViewFisaMedicalaPacientDocHandler (ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void handle(Response<?> response) {
        if (response.isSuccessful()) {
            try {
                if (clientController.getActiveController() instanceof ViewFisaMedicalaPacientDocController) {
                    ViewFisaMedicalaPacientDocController controller = (ViewFisaMedicalaPacientDocController) clientController.getActiveController();
                    if (response.getRequest().getAdditionalInfo().equals("pacient")) {
                        List<Utilizator> utilizators = (List<Utilizator>) response.getResponseObject();
                        controller.setPacienti(utilizators);
                    } else if (response.getRequest().getAdditionalInfo().equals("consultatie_prescriptie")) {
                        HashMap<Consultatie, Prescriptie> cr=(HashMap<Consultatie, Prescriptie>) response.getResponseObject();
                        controller.setFisaMedicala(cr);

                    } else clientController.showError("Can't access pacient data");

                }
            } catch (Exception e) {
                clientController.showError("Can't access pacient data");
            }
        } else {
            clientController.showError("Can't access pacient data");
        }
    }
}
