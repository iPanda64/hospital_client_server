package controller;

import javafx.application.Platform;
import model.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AsistentController extends BaseViewController {

    private final Client client;

    public AsistentController(ClientController clientController, Client client) {
        super(clientController, client, "asistent.html");
        this.client = client;
        onPageLoadFinished(() -> {
            getListaPacienti();
            getProgramari();
        });
    }
    public void getListaPacienti() {
        try {
            Request req = new Request(UtilizatorType.asistent, UseCaseType.AsistentViewPacienti, null, ClientController.getCurrentId(), null);
            client.sendToServer(req);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void getProgramari() {
        try {
            Request req = new Request(UtilizatorType.asistent, UseCaseType.AsistentViewProgramari, null, ClientController.getCurrentId(), null);
            client.sendToServer(req);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void approveProgramare(int id) {
        try {
            Request req = new Request(UtilizatorType.asistent, UseCaseType.AsistentGestionareProgramari, null, ClientController.getCurrentId(), Integer.valueOf(id));
            client.sendToServer(req);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void deleteProgramare(int id) {
        try {
            Request req = new Request(UtilizatorType.asistent, UseCaseType.AsistentStergeProgramare, null, ClientController.getCurrentId(), Integer.valueOf(id));
            client.sendToServer(req);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void getPrescriptii(int idPacient) {
        try {
            Request req = new Request(UtilizatorType.asistent, UseCaseType.AsistentViewPrescriptii, null, ClientController.getCurrentId(), Integer.valueOf(idPacient));
            client.sendToServer(req);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void printFactura(int idConsultatie) {
        try {
            Request req = new Request(UtilizatorType.asistent, UseCaseType.AsistentGetFacturaData, null, ClientController.getCurrentId(), Integer.valueOf(idConsultatie));
            client.sendToServer(req);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void setPacienti(List<Utilizator> users) {
        String json = users.stream()
                .map(u -> String.format(
                        "{\"id\":%d,\"username\":\"%s\",\"nume\":\"%s\",\"prenume\":\"%s\",\"telefon\":\"%s\",\"email\":\"%s\",\"dataNasterii\":\"%s\"}",
                        u.getId(), u.getUsername(), u.getNume(), u.getPrenume(), u.getTelefon(), u.getEmail(), u.getDataNastere().toString()
                ))
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> webEngine.executeScript(String.format("displayPacienti('%s');", json)));
    }

    public void setProgramari(List<Programare> list) {
        String json = list.stream()
                .map(p -> String.format(
                        "{\"id\":%d,\"id_pacient\":%d,\"id_doctor\":%d,\"data\":\"%s\",\"status\":\"%s\"}",
                        p.getId(), p.getId_pacient(), p.getId_doctor(), p.getData_programarii().toString(), p.getStatus().name()
                ))
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> webEngine.executeScript(String.format("displayProgramari('%s');", json)));
    }

    public void setPrescriptiiPacient(List<Prescriptie> list) {
        String json = list.stream()
                .map(p -> String.format(
                        "{\"id\":%d,\"medicament\":\"%s\",\"doza\":%d,\"durata\":%d}",
                        p.getId(), p.getMedicament(), p.getDoza_zilnica(), p.getDurata_tratament_in_zile()
                ))
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> webEngine.executeScript(String.format("displayPrescriptii('%s');", json)));
    }

    public void displayFacturaData(Factura f) {
        String json = String.format("{\"id\":%d,\"id_consultatie\":%d,\"data\":\"%s\",\"suma\":%d}",
                f.getId(), f.getId_consultatie(), f.getData_emitere().toString(), f.getSuma());

        Platform.runLater(() -> {
            webEngine.executeScript(String.format("showFacturaPreview('%s');", json));
            try {
                System.out.println("Se genereaza PDF pentru factura: " + f.getId());
                clientController.showSuccess("Factura a fost salvata cu succes!");
            } catch (Exception e) {
                clientController.showError("Eroare la salvarea PDF: " + e.getMessage());
            }
        });
    }

    public void setDetaliiPacient(Utilizator u) {
        String json = String.format(
                "{\"id\":%d,\"nume\":\"%s\",\"prenume\":\"%s\",\"telefon\":\"%s\",\"email\":\"%s\",\"dataNasterii\":\"%s\"}",
                u.getId(), u.getNume(), u.getPrenume(), u.getTelefon(), u.getEmail(), u.getDataNastere().toString()
        );

        Platform.runLater(() -> webEngine.executeScript(String.format("displayPacientDetails('%s');", json)));
    }
    public void createProgramare(String data, String idPacient, String idDoctor) {
        try {
            LocalDate dataProg = LocalDate.parse(data);
            int pacId = Integer.parseInt(idPacient);
            int docId = Integer.parseInt(idDoctor);
            Programare noua = new Programare(docId, pacId, dataProg, StatusProgramare.InAsteptare);
            Request req = new Request(UtilizatorType.asistent, UseCaseType.AsistentCreareProgramare, null, ClientController.getCurrentId(), noua);
            client.sendToServer(req);
        } catch (Exception e) {
            clientController.showError("Date invalide pentru programare.");
        }
    }
}