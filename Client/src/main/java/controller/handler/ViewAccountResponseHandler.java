package controller.handler;

import controller.BaseViewController;
import controller.ClientController;
import controller.ViewAccountController;
import model.Response;
import model.Utilizator;
import model.ViewType;

import javax.swing.text.View;

public class ViewAccountResponseHandler implements ResponseHandler{
    private final ClientController clientController;

    public ViewAccountResponseHandler (ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void handle(Response<?> response) {
        if(!response.isSuccessful()){
            clientController.showError("An error has occured when loading profile");
            return;
        }
        try {
            BaseViewController activeController = clientController.getActiveController();
            if (activeController instanceof ViewAccountController) {
                Utilizator utilizator = (Utilizator) response.getResponseObject();
                String info=utilizator.getUsername()+","+utilizator.getNume()+","+utilizator.getPrenume()+","+utilizator.getTelefon()+","+utilizator.getEmail()+","+utilizator.getDataNastere();
                System.out.println(info);
                ((ViewAccountController) activeController).setAccount(info);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
