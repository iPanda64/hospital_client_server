package controller;

import javafx.application.Platform;
import model.Consultatie;
import model.Request;
import model.UseCaseType;
import model.ViewType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
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
                    String id=parts[0];
                    String nume = parts[1];
                    String prenume = parts[2];
                    String dataProgramare = parts[3];
                    String status = parts[4]+" "+parts[5];
                    return String.format(
                            "{\"id\":\"%s\",\"nume\":\"%s\",\"prenume\":\"%s\",\"data_emitere\":\"%s\",\"status\":\"%s\"}",
                            id,nume, prenume, dataProgramare, status
                    );
                })
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> {
            webEngine.executeScript(String.format("setProgramari('%s');", json));
        });
    }

    public void createConsultatie(int programareId,String diagnostic,String simptom, String cost, String data){
        try{
            LocalDate localDate = LocalDate.parse(data);
            System.out.println(programareId);
            System.out.println(diagnostic);
            List<String> simptomeList = Arrays.asList(simptom.split(","));
            Consultatie send=new Consultatie(programareId,diagnostic,simptomeList,Integer.parseInt(cost),localDate);
            client.sendToServer(new Request(UseCaseType.DoctorCreateConsultatie,send));
        }catch(DateTimeParseException e){
            clientController.showError("Invalid date format, use yy-mm-dd");
        } catch (Exception e) {
            clientController.showError("Couldn't create consultatie");
        }

    }
    public void previous() {
        clientController.switchView(ViewType.VIEW_ACCOUNT);
    }

    public void next() {
        clientController.switchView(ViewType.VIEW_DATE_PERSONALE_PACIENT_DOC);
    }
}