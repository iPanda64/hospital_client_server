package controller.handler;

import controller.AsistentController;
import controller.BaseViewController;
import controller.ClientController;
import model.*;

import java.util.List;

public class AsistentResponseHandler implements ResponseHandler {
    private final ClientController clientController;

    public AsistentResponseHandler(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void handle(Response<?> response) {
        BaseViewController activeController = clientController.getActiveController();
        if (!(activeController instanceof AsistentController)) {
            return;
        }
        if (!response.isSuccessful()) {
            handleError(response);
            return;
        }
        handleSuccess(activeController, response);
    }

    private void handleSuccess(BaseViewController activeController, Response<?> response) {
        AsistentController asistentController = (AsistentController) activeController;
        UseCaseType type = response.getRequest().getUseCaseType();

        switch (type) {
            case AsistentViewPacienti:
                List<Utilizator> pacienti = (List<Utilizator>) response.getResponseObject();
                asistentController.setPacienti(pacienti);
                break;

            case AsistentViewProgramari:
            case AsistentGestionareProgramari:
            case AsistentStergeProgramare:
            case AsistentCreareProgramare:
                List<Programare> programari = (List<Programare>) response.getResponseObject();
                asistentController.setProgramari(programari);
                if (type != UseCaseType.AsistentViewProgramari) {
                    clientController.showSuccess("Operațiune efectuată cu succes!");
                }
                break;

            case AsistentViewPrescriptii:
                List<Prescriptie> prescriptii = (List<Prescriptie>) response.getResponseObject();
                asistentController.setPrescriptiiPacient(prescriptii);
                break;

            case AsistentGetFacturaData:
                Factura factura = (Factura) response.getResponseObject();
                asistentController.displayFacturaData(factura);
                break;

            case AsistentViewDatePersonalePacienti:
                Utilizator detaliiPacient = (Utilizator) response.getResponseObject();
                asistentController.setDetaliiPacient(detaliiPacient);
                break;

            default:
                System.out.println("Succes neașteptat pentru asistent: " + type);
                break;
        }
    }

    private void handleError(Response<?> response) {
        String errorMessage = "Eroare la procesarea cererii asistentului.";
        if (response.getRequest() != null) {
            switch (response.getRequest().getUseCaseType()) {
                case AsistentViewPacienti:
                    errorMessage = "Nu s-a putut încărca lista de pacienți.";
                    break;
                case AsistentGestionareProgramari:
                    errorMessage = "Aprobarea programării a eșuat.";
                    break;
                case AsistentStergeProgramare:
                    errorMessage = "Ștergerea programării a eșuat.";
                    break;
                case AsistentGetFacturaData:
                    errorMessage = "Nu s-au putut prelua datele facturii.";
                    break;
                case AsistentViewPrescriptii:
                    errorMessage = "Nu s-au putut prelua prescripțiile.";
                    break;
            }
        }
        clientController.showError(errorMessage);
    }
}
