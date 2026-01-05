package controller.handler;

import controller.BaseViewController;
import controller.ClientController;
import controller.ViewProgramariDoctorController;
import model.Programare;
import model.Response;

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
            info=(List<String>) response.getResponseObject();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        ((ViewProgramariDoctorController) activeController).setProgramari(info);
    }
}
