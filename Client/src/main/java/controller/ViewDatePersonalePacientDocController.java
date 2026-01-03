package controller;

import javafx.application.Platform;
import model.Request;
import model.UseCaseType;
import model.ViewType;
import model.Utilizator;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ViewDatePersonalePacientDocController extends BaseViewController {

    private final Client client;

    public ViewDatePersonalePacientDocController(ClientController clientController, Client client) {
        super(clientController, client, "viewDatePersonalePacientDoc.html");
        this.client = client;
        onPageLoadFinished(this::getDatePersonale);
    }

    public void getDatePersonale() {
        try {
            Request viewDatePersonale = new Request(UseCaseType.DoctorViewDatePersonalePacient, ClientController.getCurrentId());
            client.sendToServer(viewDatePersonale);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDatePersonale(List<Utilizator> utilizatori) {
        String json = utilizatori.stream()
                .map(u -> String.format(
                        "{\"username\":\"%s\",\"nume\":\"%s\",\"prenume\":\"%s\",\"telefon\":\"%s\",\"email\":\"%s\",\"dataNastere\":\"%s\"}",
                        u.getUsername(), u.getNume(), u.getPrenume(), u.getTelefon(), u.getEmail(), u.getDataNastere().toString()
                ))
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> {
            webEngine.executeScript(String.format("setDatePersonale('%s');", json));
        });
    }

    public void previous() {
        clientController.switchView(ViewType.VIEW_PROGRAMARI_DOCTOR);
    }

    public void next() {
        clientController.switchView(ViewType.VIEW_PROGRAMARI_DOCTOR);
    }
}