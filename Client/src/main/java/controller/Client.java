package controller;

import model.MessageHandler;
import ocsf.AbstractClient;

public class Client extends AbstractClient {

    private final MessageHandler messageHandler;

    public Client(String host, int port, MessageHandler messageHandler) {
        super(host, port);
        this.messageHandler = messageHandler;
    }

    @Override
    protected void handleMessageFromServer(Object msg) {
        System.out.println("--> Message received from server: " + msg);
        messageHandler.handleMessageFromServer(msg);
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