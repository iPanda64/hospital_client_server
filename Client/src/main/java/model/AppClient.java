package model;

import controller.Main;
import ocsf.AbstractClient;

public class AppClient extends AbstractClient {

    private final Main ui;

    public AppClient(String host, int port, Main ui) {
        super(host, port);
        this.ui = ui;
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        System.out.println("--> Message received from server: " + msg);
        ui.displayMessage("Server says: " + msg.toString());
    }

    @Override
    protected void connectionEstablished() {
        System.out.println("--> Connection established with server.");
    }

    @Override
    protected void connectionClosed() {
        System.out.println("--> Connection closed.");
    }

    @Override
    protected void connectionException(Exception exception) {
        System.err.println("--> Connection exception: " + exception);
        exception.printStackTrace();
    }
}