package controller;

import ocsf.ConnectionToClient;
import model.Request;
import model.Response; // Added import
import model.UseCaseType; // Added import

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

            switch (request.getUseCaseType()) {
                case Login:
                    // Placeholder for login logic
                    String credentials = (String) request.getPayload();
                    System.out.println("Login attempt with: " + credentials);
                    // For now, always send a success response for login
                    try {
                        client.sendToClient(new Response<>(request, request.getId(), "Login successful", true)); // Send Response
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case Chat:
                    // Broadcast chat message to all clients
                    String chatMessage = (String) request.getPayload();
                    System.out.println("Chat message: " + chatMessage);
                    this.server.sendToAllClients(new Response<>(request, request.getId(), chatMessage, true)); // Echo as a Response
                    break;
                default:
                    System.out.println("Unknown UseCaseType: " + request.getUseCaseType());
                    try {
                        client.sendToClient(new Response<>(request, request.getId(), "Unknown UseCaseType", false)); // Send Response
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        } else {
            // Handle old string messages or other unexpected objects
            System.out.println("Received non-Request object: " + msg);
            // Send an error response for unexpected messages
            try {
                client.sendToClient(new Response<>(null, 0, "Unexpected message format", false)); // Send Response
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}