package controller;

import model.AppClient;
import java.io.IOException;

public class JavaBridge {

    private final AppClient client;

    public JavaBridge(AppClient client) {
        this.client = client;
    }

    public void sendMessage(String message) {
        try {
            client.sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}