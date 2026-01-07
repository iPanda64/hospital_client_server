package controller;

import javafx.application.Platform;
import model.Factura;
import model.Request;
import model.UseCaseType;
import model.ViewType;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ViewFacturiController extends BaseViewController{

    private final Client client;

    public ViewFacturiController  (ClientController clientController, Client client) {
        super(clientController, client, "viewFacturi.html");
        this.client = client;
        onPageLoadFinished(this::getFacturi);
    }
    public void getFacturi() {
        try {
            Request viewFacturi = new Request(UseCaseType.PacientViewFacturi, ClientController.getCurrentId());
            client.sendToServer(viewFacturi);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setFacturi(List<Factura>facturi) {

        String json = facturi.stream()
                .map(u -> String.format(
                        "{\"id\":%d,\"id_consultatie\":\"%d\",\"data_emitere\":\"%s\",\"suma\":\"%.2f\"}",
                        u.getId(), u.getId_consultatie(), u.getData_emitere(), (float)u.getSuma()
                ))
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> {
            webEngine.executeScript(String.format("setFacturi('%s');", json));
        });
    }
    public void previous(){
        clientController.switchView(ViewType.VIEW_ACCOUNT);
    }
    public void next(){
        clientController.switchView(ViewType.PACIENT);
    }
}
