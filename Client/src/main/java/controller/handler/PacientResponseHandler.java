package controller.handler;

import controller.PacientController;
import controller.BaseViewController;
import controller.ClientController;
import model.Response;
import model.Programare;
import model.Consultatie;
import model.Factura;
import model.UseCaseType;

import java.util.List;

public class PacientResponseHandler implements ResponseHandler {
    private final ClientController clientController;

    public PacientResponseHandler(ClientController clientController) {
        this.clientController = clientController;
    }
    @Override
    public void handle(Response<?> response) {
        BaseViewController activeController = clientController.getActiveController();
        if (!(activeController instanceof PacientController)) {
            return;
        }
        if (!response.isSuccessful()) {
            handleError(response);
            return;
        }
        handleSuccess(activeController, response);
    }
    private void handleSuccess(BaseViewController activeController, Response<?> response) {
        PacientController pacientController = (PacientController) activeController;
        UseCaseType type = response.getRequest().getUseCaseType();
        switch (type) {
            case PacientViewProgramari:
                List<Programare> programari = (List<Programare>) response.getResponseObject();
                pacientController.setProgramari(programari);
                break;
            case PacientCreateProgramare:
                List<Programare> listaNoua = (List<Programare>) response.getResponseObject();
                System.out.println(listaNoua);
                pacientController.setProgramari(listaNoua);
                clientController.showSuccess("Programarea a fost creată cu succes!");
                break;
            case PacientViewHistory:
                List<Consultatie> istoric = (List<Consultatie>) response.getResponseObject();
                //pacientController.setIstoric(istoric); //asta sa o implementez
                break;
            case PacientViewFacturi:
                List<Factura> facturi = (List<Factura>) response.getResponseObject();
                pacientController.setFacturi(facturi);
                break;
            default:
                System.out.println("Succes neașteptat pentru: " + type);
                break;
        }
    }

    private void handleError(Response<?> response) {
        String errorMessage = "A apărut o eroare la procesarea cererii.";
        if (response.getRequest() != null) {
            switch (response.getRequest().getUseCaseType()) {
                case PacientViewProgramari:
                    errorMessage = "Eroare: Nu s-au putut încărca programările.";
                    break;
                case PacientCreateProgramare:
                    errorMessage = "Eroare: Nu s-a putut salva programarea.";
                    break;
                case PacientViewHistory:
                    errorMessage = "Eroare: Nu s-a putut accesa istoricul medical.";
                    break;
                case PacientViewFacturi:
                    errorMessage = "Eroare: Nu s-au putut prelua facturile.";
                    break;
            }
        }
        clientController.showError(errorMessage);
    }
}