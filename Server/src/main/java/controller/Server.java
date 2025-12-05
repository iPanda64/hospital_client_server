package controller;

import ocsf.AbstractServer;
import ocsf.ConnectionToClient;
import model.Request;

import java.io.IOException;

public class Server extends AbstractServer {

    private ServerController controller; // Reference to the controller

    public Server(int port, ServerController controller) { // Updated constructor
        super(port);
        this.controller = controller;
    }

    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
        // Delegate to the controller
        controller.handleClientRequest(msg, client);
    }

    @Override
    protected void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }

    @Override
    protected void serverStopped() {
        System.out.println("Server has stopped listening for connections.");
    }
}
