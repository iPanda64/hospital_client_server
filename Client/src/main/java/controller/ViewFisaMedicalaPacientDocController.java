package controller;

import javafx.application.Platform;
import model.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ViewFisaMedicalaPacientDocController extends BaseViewController {
    private final Client client;

    public ViewFisaMedicalaPacientDocController(ClientController clientController, Client client) {
        super(clientController, client, "viewFisaMedicalaPacientDoc.html");
        this.client = client;
        onPageLoadFinished(this::getPacienti);
    }

    public void getPacienti() {
        try {
            Request viewPacienti = new Request(UtilizatorType.doctor,
                    UseCaseType.DoctorViewFisaMedicala,
                    "pacient",
                    ClientController.getCurrentId(),
                    ClientController.getCurrentId());
            client.sendToServer(viewPacienti);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPacienti(List<Utilizator> pacienti) {
        String json = pacienti.stream()
                .map(u -> String.format(
                        "{\"id\":%d,\"nume\":\"%s\",\"prenume\":\"%s\"}",
                        u.getId(), u.getNume(), u.getPrenume()
                ))
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> {
            webEngine.executeScript(String.format("setPacienti('%s');", json));
        });
    }

    public void loadFisaMedicala(int idPacient) {
        try {
            Request request = new Request(UtilizatorType.doctor,
                    UseCaseType.DoctorViewFisaMedicala,
                    "consultatie_prescriptie",
                    ClientController.getCurrentId(),
                    idPacient);
            client.sendToServer(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setFisaMedicala(HashMap<Consultatie, Prescriptie> fisa) {
        String json = fisa.entrySet().stream()
                .map(entry -> {
                    Consultatie c = entry.getKey();
                    Prescriptie p = entry.getValue();
                    String prescriptieJson = "null";
                    if (p != null) {
                        prescriptieJson = String.format(
                                "{\"medicament\":\"%s\",\"doza_zilnica\":%d,\"durata_tratament_in_zile\":%d}",
                                p.getMedicament().isEmpty() ? "" : String.join(", ", p.getMedicament()),
                                p.getDoza_zilnica(),
                                p.getDurata_tratament_in_zile()
                        );
                    }
                    return String.format(
                            "{\"id\":\"%s\",\"data_consultatie\":\"%s\",\"diagnostic\":\"%s\",\"simptome\":\"%s\",\"cost\":%d,\"prescriptie\":%s}",
                            c.getId(),
                            c.getData_consultatiei().toString(),
                            c.getDiagnostic(),
                            String.join(", ", c.getSimptome()),
                            c.getCost(),
                            prescriptieJson
                    );
                })
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> {
            webEngine.executeScript(String.format("setFisaMedicala('%s');", json));
        });
    }

    public void createPrescription(int consultatieId,String medicament,int doza,int durata){
        try {

            List<String> medicamente = Arrays.asList(medicament.split(","));
            Prescriptie prescriptie=new Prescriptie(consultatieId,medicamente,doza,durata);
            Request request = new Request(UseCaseType.DoctorCreatePrescriptie,prescriptie);
            client.sendToServer(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void printPDF(String p){
        PdfSaver.saveTextToDesktop(p,"prescriptie.pdf");
        clientController.showSuccess("Prescriptia salvata pe desktop");
    }
    public void previous() {
        clientController.switchView(ViewType.VIEW_DATE_PERSONALE_PACIENT_DOC);
    }
    public void next(){
        clientController.switchView(ViewType.VIEW_ACCOUNT);
    }
}