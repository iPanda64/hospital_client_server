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
                    case Chat:
                        // Broadcast chat message to all clients
                        String chatMessage = (String) request.getPayload();
                        System.out.println("Chat message: " + chatMessage);
                        this.server.sendToAllClients(new Response<>(request, request.getId(), chatMessage, true)); // Echo as a Response
                        break;
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