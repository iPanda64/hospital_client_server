package controller.handler;

import controller.BaseViewController;
import controller.ClientController;
import controller.ViewProgramariDoctorController;
import model.Programare;
import model.Response;
import model.UseCaseType;

import java.util.List;

public class ViewProgramariDoctorResponseHandler implements ResponseHandler  {
    private final ClientController clientController;
    public ViewProgramariDoctorResponseHandler   (ClientController clientController) {
        this.clientController = clientController;
    }
    @Override
    public void handle(Response<?> response) {
        BaseViewController activeController = clientController.getActiveController();

        System.out.println(response.getResponseObject());
        if (!(activeController instanceof ViewProgramariDoctorController)) {
            return;
        }
        List<String> info=null;
        try{
            if(response.getRequest().getUseCaseType()!= UseCaseType.DoctorCreateConsultatie) {
                info = (List<String>) response.getResponseObject();
                ((ViewProgramariDoctorController) activeController).setProgramari(info);
            }
            else if(response.isSuccessful())clientController.showSuccess("Consultatie Added");
            else clientController.showError("Programare already has consultatie");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
