package controller;

import model.*;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CreateAccountController extends BaseViewController {

    private final Client client;

    public CreateAccountController (ClientController clientController, Client client) {
        super(clientController, client, "createAccount.html");
        this.client = client;
    }

    public void getAccount(String username, String parola, String nume, String prenume, String numar_de_telefon, String email, String data_nasterii){
        System.out.println("hey");
        try {
            if(username.contains(" "))throw new UnsupportedOperationException();
            if(parola.contains(" "))throw new UnsupportedOperationException();
            if(nume.contains(" "))throw new UnsupportedOperationException();
            if(prenume.contains(" "))throw new UnsupportedOperationException();
            if(numar_de_telefon.contains(" "))throw new UnsupportedOperationException();
            if(email.contains(" "))throw new UnsupportedOperationException();
            if(data_nasterii.contains(" "))throw new UnsupportedOperationException();
            LocalDate dataNasterii=LocalDate.parse(data_nasterii);
            Utilizator utilizator=new Utilizator(0,nume,prenume,username,parola,email,numar_de_telefon,dataNasterii, UtilizatorType.pacient);
            Request loginRequest = new Request(UseCaseType.CreateAccount, utilizator);
            client.sendToServer(loginRequest);
        } catch (IOException e) {
            e.printStackTrace();
        } catch(UnsupportedOperationException e) {
            clientController.showError("No spaces in the fields");
        } catch(DateTimeException e) {
            clientController.showError("Date should follow yyyy-mm-dd");
        }
    }
    public void goBackToLogin() {
        clientController.switchView(ViewType.LOGIN);
    }
}