package controller;

import ocsf.ConnectionToClient;
import model.Request;
import model.Response; // Added import
import model.UseCaseType; // Added import
import controller.AbstractHandler;
import controller.AdminManipulator;
import controller.AsistentManipulator;
import controller.DoctorManipulator;
import controller.PacientManipulator;
import controller.ServerManipulator;
import controller.UnknownUserManipulator;


import java.io.IOException;

public class ServerController {

    private Server server;

    public ServerController(Server server) {
        this.server = server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public void handleClientRequest(Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg + " from " + client);

        if (msg instanceof Request) {
            Request request = (Request) msg;
            System.out.println("Request Type: " + request.getUseCaseType() + ", Payload: " + request.getPayload());

            try {
                switch (request.getUseCaseType()) {
                    case Login:
                        UnknownUserManipulator manipulator = new UnknownUserManipulator();
                        UnknownUserHandler handler = manipulator.instantiate(request);
                        Response response = handler.loginHandler(request);
                        client.sendToClient(response);
                        break;
                    case ViewAccount: {
                        client.sendToClient(
                                new UnknownUserManipulator()
                                        .instantiate(request)
                                        .viewAccontHandler(request));
                        break;
                    }
                    case CreateAccount:{
                        client.sendToClient(
                                new UnknownUserManipulator()
                                        .instantiate(request)
                                        .createAccountHandler(request));
                        break;
                    }
                    case AdminViewAllAccounts:{
                        client.sendToClient(
                                new AdminManipulator()
                                        .instantiate(request)
                                        .viewAllAccountsHandler(request));
                        break;
                    }
                    case AdminDeleteUser:{
                        client.sendToClient(
                                new AdminManipulator()
                                        .instantiate(request)
                                        .deleteHandler(request));
                        break;
                    }
                    case AdminAddUser:{
                        client.sendToClient(
                                new AdminManipulator()
                                        .instantiate(request)
                                        .createAccountHandler(request));
                        break;
                    }
                    case AdminEditUser:{
                        client.sendToClient(
                                new AdminManipulator()
                                        .instantiate(request)
                                        .updateAccountHandler(request));
                        break;
                    }
                    case PacientViewFacturi:{
                        client.sendToClient(
                                new PacientManipulator()
                                        .instantiate(request)
                                        .viewFacturiHandler(request));
                        break;
                    }
                    case DoctorViewProgramari:{
                        client.sendToClient(
                                new DoctorManipulator()
                                        .instantiate(request)
                                        .viewProgramariHandler(request));
                        break;
                    }
                    case DoctorViewFisaMedicala:{
                        client.sendToClient(
                                new DoctorManipulator()
                                        .instantiate(request)
                                        .viewFisaMedicalaPacientHandler(request));
                        break;
                    }
                    case DoctorViewDatePersonalePacient: {
                        client.sendToClient(
                                new DoctorManipulator()
                                        .instantiate(request)
                                        .viewDatePersonalePacienti(request));
                        break;
                    }
                    case Chat:
                        // Broadcast chat message to all clients
                        String chatMessage = (String) request.getPayload();
                        System.out.println("Chat message: " + chatMessage);
                        this.server.sendToAllClients(new Response<>(request, request.getId(), chatMessage, true)); // Echo as a Response
                        break;
                    case PacientViewProgramari: {
                        client.sendToClient(
                                new PacientManipulator()
                                        .instantiate(request)
                                        .viewProgramariHandler(request));
                        break;
                    }
                    case PacientCreateProgramare: {
                        client.sendToClient(
                                new PacientManipulator()
                                        .instantiate(request)
                                        .createProgramareHandler(request));
                        break;
                    }
                    case PacientViewHistory: {
                        client.sendToClient(
                                new PacientManipulator()
                                        .instantiate(request)
                                        .viewMedicalHistoryHandler(request));
                        break;
                    }
                    case PacientGetResults: {
                        client.sendToClient(
                                new PacientManipulator()
                                        .instantiate(request)
                                        .getConsultatieResults(request));
                        break;
                    }
                    case AsistentViewPacienti: {
                        client.sendToClient(
                                new AsistentManipulator()
                                        .instantiate(request)
                                        .viewListaPacientiHandler(request));
                        break;
                    }
                    case AsistentViewProgramari: {
                        client.sendToClient(
                                new AsistentManipulator()
                                        .instantiate(request)
                                        .viewProgramariPacientiHandler(request));
                        break;
                    }
                    case AsistentCreareProgramare: {
                        client.sendToClient(
                                new AsistentManipulator()
                                        .instantiate(request)
                                        .createProgramarePacientiHandler(request));
                        break;
                    }
                    case AsistentGestionareProgramari: {
                        client.sendToClient(
                                new AsistentManipulator()
                                        .instantiate(request)
                                        .approveProgramariPacientiHandler(request));
                        break;
                    }

                    case AsistentStergeProgramare: {
                        client.sendToClient(
                                new AsistentManipulator()
                                        .instantiate(request)
                                        .deleteProgramariPacientiHandler(request));
                        break;
                    }

                    case AsistentViewPrescriptii: {
                        client.sendToClient(
                                new AsistentManipulator()
                                        .instantiate(request)
                                        .viewPrescriptiePacientHandler(request));
                        break;
                    }

                    case AsistentGetFacturaData: {
                        client.sendToClient(
                                new AsistentManipulator()
                                        .instantiate(request)
                                        .printFacturaConsultatieHandler(request));
                        break;
                    }
                    default:
                        System.out.println("Unknown UseCaseType: " + request.getUseCaseType());
                        client.sendToClient(new Response<>(request, request.getId(), "Unknown UseCaseType", false)); // Send Response
                        break;
                }
            } catch (IOException e) {
                System.out.println("Failed to send response to client " + client);
                e.printStackTrace();
            }
        } else {
            System.out.println("Received non-Request object: " + msg);
            try {
                client.sendToClient(new Response<>(null, 0, "Unexpected message format", false)); // Send Response
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}