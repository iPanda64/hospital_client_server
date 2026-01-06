package controller;

import javafx.application.Platform;
import model.Request;
import model.UseCaseType;
import model.UtilizatorType;
import model.ViewType;

import java.io.IOException;

public class ViewAccountController extends BaseViewController{
    private final Client client;
    private static UtilizatorType utilizatorTip=null;

    public ViewAccountController(ClientController clientController, Client client) {
        super(clientController, client, "viewAccount.html");
        this.client = client;
        onPageLoadFinished(this::getAccount);
    }
    public void getAccount() {
        try {
            System.out.println(ClientController.getCurrentId());
            Request viewAccount = new Request(UseCaseType.ViewAccount,ClientController.getCurrentId());
            client.sendToServer(viewAccount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void goBackToLogin() {
        clientController.switchView(ViewType.LOGIN);
    }
    public void previous(){
        // todo
        if(utilizatorTip==UtilizatorType.administrator){
            clientController.switchView(ViewType.ADMIN);
        }else if(utilizatorTip==UtilizatorType.pacient){
            clientController.switchView(ViewType.PACIENT);
        }else if(utilizatorTip==UtilizatorType.doctor)
            clientController.switchView(ViewType.VIEW_FISA_MEDICALA_PACIENT_DOCTOR);
        else if(utilizatorTip==UtilizatorType.asistent){
            clientController.switchView(ViewType.ASISTENT);
        }
        else clientController.switchView(ViewType.LOGIN);
    }
    public void next(){
        // todo
        if(utilizatorTip==UtilizatorType.administrator){
            clientController.switchView(ViewType.ADMIN);
        }else if(utilizatorTip==UtilizatorType.pacient){
            clientController.switchView(ViewType.VIEW_FACTURI);
        }else if(utilizatorTip==UtilizatorType.doctor){
            clientController.switchView(ViewType.VIEW_PROGRAMARI_DOCTOR);
        }else if(utilizatorTip==UtilizatorType.asistent){
            clientController.switchView(ViewType.ASISTENT);
        }
        else clientController.switchView(ViewType.LOGIN);
    }
    public void setAccount(String account) {
        Platform.runLater(() -> {
            String cleanMessage = account.replace("\\", "\\\\")
                    .replace("'", "'" )
                    .replace("\n", " ")
                    .replace("\r", " ");
            System.out.println(cleanMessage);
            webEngine.executeScript("setAccount('" + cleanMessage + "')");
        });
    }
    public static void setUtilizatorTip(UtilizatorType utilizatorTip) {
        ViewAccountController.utilizatorTip = utilizatorTip;
    }
}

