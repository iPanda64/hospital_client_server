package controller;

import javafx.application.Platform;
import model.Request;
import model.UseCaseType;
import model.ViewType;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ViewProgramariDoctorController extends BaseViewController {
private final Client client;

    public ViewProgramariDoctorController(ClientController clientController, Client client) {
        super(clientController, client, "viewProgramariDoc.html");
        this.client = client;
        onPageLoadFinished(this::getProgramari);
    }

    public void getProgramari() {
        try {
            Request viewProgramari = new Request(UseCaseType.DoctorViewProgramari, ClientController.getCurrentId());
            client.sendToServer(viewProgramari);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setProgramari(List<String> info) {
        String json = info.stream()
                .map(s -> {
                    String[] parts = s.split(" ");
                    String nume = parts[0];
                    String prenume = parts[1];
                    String dataProgramare = parts[2];
                    String status = parts[3];
                    return String.format(
                            "{\"nume\":\"%s\",\"prenume\":\"%s\",\"data_emitere\":\"%s\",\"status\":\"%s\"}",
                            nume, prenume, dataProgramare, status
                    );
                })
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> {
            webEngine.executeScript(String.format("setProgramari('%s');", json));
        });
    }

    public void previous() {
        clientController.switchView(ViewType.VIEW_ACCOUNT);
    }

    public void next() {
        clientController.switchView(ViewType.VIEW_DATE_PERSONALE_PACIENT_DOC);
    }
}