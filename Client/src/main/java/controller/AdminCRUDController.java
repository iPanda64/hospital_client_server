package controller;

import javafx.application.Platform;
import model.*;

import java.io.IOException;
import java.security.spec.ECField;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class AdminCRUDController extends BaseViewController {

    private final Client client;

    public AdminCRUDController (ClientController clientController, Client client) {
        super(clientController, client, "admin.html");
        this.client = client;
        onPageLoadFinished(this::getAllUsers);
    }
    public void next(){
        clientController.switchView(ViewType.VIEW_ACCOUNT);
    }
    public void previous(){
        clientController.switchView(ViewType.VIEW_ACCOUNT);
    }
    public void getAllUsers() {
        try {
            Request request = new Request(UtilizatorType.administrator,UseCaseType.AdminViewAllAccounts,null,ClientController.getCurrentId(),null);
            client.sendToServer(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setAllUsers(List<Utilizator> users) {
        String json = users.stream()
                .map(u -> String.format(
                        "{\"id\":%d,\"username\":\"%s\",\"parola\":\"%s\",\"nume\":\"%s\",\"prenume\":\"%s\",\"telefon\":\"%s\",\"email\":\"%s\",\"dataNasterii\":\"%s\",\"tip\":\"%s\"}",
                        u.getId(), u.getUsername(), u.getParola(), u.getNume(), u.getPrenume(), u.getTelefon(), u.getEmail(), u.getDataNastere().toString(), u.getTip().name()
                ))
                .collect(Collectors.joining(",", "[", "]"));

        Platform.runLater(() -> {
            webEngine.executeScript(String.format("setAllUsers('%s');", json));
        });
    }

    public void deleteUser(int id) {
        try {
            Request request = new Request(UtilizatorType.administrator,UseCaseType.AdminDeleteUser,null,ClientController.getCurrentId(),Integer.valueOf(id));
            client.sendToServer(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addUser(String username, String parola, String nume, String prenume, String telefon, String email,String dataNasterii, String tip) {
        LocalDate date;
        UtilizatorType utilizatorType;

        try {
            date = java.time.LocalDate.parse(dataNasterii, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (java.time.format.DateTimeParseException e) {
            clientController.showError("Invalid date format or the date does not exist.\nPlease use YYYY-MM-DD.");
            return;
        }

        try {
            utilizatorType = UtilizatorType.valueOf(tip.toLowerCase());
        } catch (IllegalArgumentException e) {
            clientController.showError("Invalid user type specified: " + tip);
            return;
        }

        Utilizator newUser = new Utilizator(nume, prenume, username, parola, email, telefon, date, utilizatorType);
        try {
            Request request = new Request(UtilizatorType.administrator,UseCaseType.AdminAddUser,null,ClientController.getCurrentId(),newUser);
            client.sendToServer(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void editUser(int id, String username, String parola, String nume, String prenume, String telefon, String email, String dataNasterii, String tip) {
        LocalDate date;
        UtilizatorType utilizatorType;

        try {
            date = java.time.LocalDate.parse(dataNasterii, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (java.time.format.DateTimeParseException e) {
            clientController.showError("Invalid date format or the date does not exist.\nPlease use YYYY-MM-DD.");
            return;
        }

        try {
            utilizatorType = UtilizatorType.valueOf(tip.toLowerCase());
        } catch (IllegalArgumentException e) {
            clientController.showError("Invalid user type specified: " + tip);
            return;
        }

        Utilizator updatedUser = new Utilizator(id, nume, prenume, username, parola, email, telefon, date, utilizatorType);
        try {
            client.sendToServer(new Request(UtilizatorType.administrator, UseCaseType.AdminEditUser, null, ClientController.getCurrentId(), updatedUser));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
