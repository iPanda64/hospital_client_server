package controller.handler;

import controller.AdminCRUDController;
import controller.BaseViewController;
import controller.ClientController;
import controller.ViewFacturiController;
import model.Factura;
import model.Response;

import java.util.List;

public class ViewFacturiResponseHandler implements ResponseHandler  {
    private final ClientController clientController;
    public ViewFacturiResponseHandler  (ClientController clientController) {
        this.clientController = clientController;
    }
    @Override
    public void handle(Response<?> response) {
        BaseViewController activeController = clientController.getActiveController();

        System.out.println(response.getResponseObject());
        if (!(activeController instanceof ViewFacturiController)) {
            return;
        }
        List<Factura>facturiList=null;
        try{
            facturiList=(List<Factura>) response.getResponseObject();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        ((ViewFacturiController) activeController).setFacturi(facturiList);
    }
}
