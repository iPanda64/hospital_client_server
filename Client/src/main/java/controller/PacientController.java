package controller;

import javafx.application.Platform;
import model.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class PacientController extends BaseViewController {

    public PacientController(ClientController clientController, Client client) {
        super(clientController, client, "pacient.html");

        onPageLoadFinished(this::getProgramari);
    }
    public void goToProfile() {
        Platform.runLater(() -> { clientController.switchView(ViewType.VIEW_ACCOUNT);
        });
    }
    public void getProgramari() {
        try {
            Request req = new Request(UtilizatorType.pacient, UseCaseType.PacientViewProgramari, null, ClientController.getCurrentId(), ClientController.getCurrentId());
            client.sendToServer(req);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void createProgramare(String dataStr,int idDoctor) {
        try {
            LocalDate data = LocalDate.parse(dataStr);
            int idPacient = ClientController.getCurrentId();
            Programare noua = new Programare(idDoctor, idPacient, data, StatusProgramare.InAsteptare);
            noua.setId_pacient(ClientController.getCurrentId());
            noua.setData_programarii(data);
            noua.setStatus(StatusProgramare.InAsteptare);

            Request req = new Request(UtilizatorType.pacient, UseCaseType.PacientCreateProgramare, null, ClientController.getCurrentId(), noua);
            client.sendToServer(req);
        } catch (Exception e) {
            clientController.showError("Format dată invalid (YYYY-MM-DD)");
        }
    }

    public void getHistory() {
        try {
            Request req = new Request(UtilizatorType.pacient, UseCaseType.PacientViewHistory, null, ClientController.getCurrentId(), ClientController.getCurrentId());
            client.sendToServer(req);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void getFacturi() {
        try {
            Request req = new Request(UtilizatorType.pacient, UseCaseType.PacientViewFacturi, null, ClientController.getCurrentId(), ClientController.getCurrentId());
            client.sendToServer(req);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void setProgramari(List<Programare> list) {
        String json = list.stream()
                .map(p -> String.format("{\"id\":%d,\"data\":\"%s\",\"status\":\"%s\"}",
                        p.getId(), p.getData_programarii().toString(), p.getStatus().name()))
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> webEngine.executeScript(String.format("displayProgramari('%s');", json)));
    }

    public void setHistory(List<Consultatie> list) {
        String json = list.stream()
                .map(c -> String.format("{\"id\":%d,\"diagnostic\":\"%s\",\"data\":\"%s\"}",
                        c.getId(), c.getDiagnostic(), c.getData_consultatiei().toString()))
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> webEngine.executeScript(String.format("displayHistory('%s');", json)));
    }

    public void setFacturi(List<Factura> list) {
        String json = list.stream()
                .map(f -> String.format("{\"id\":%d,\"suma\":%d}",
                        f.getId(), f.getSuma()))
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> webEngine.executeScript(String.format("displayFacturi('%s');", json)));
    }
}