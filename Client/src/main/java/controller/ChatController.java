package controller;

import javafx.application.Platform;
import model.Request;
import model.UseCaseType;

import java.io.IOException;

public class ChatController extends BaseViewController {

    private final Client client;

    public ChatController(Client client) {
        super(client, "index.html");
        this.client = client;
    }

    public void sendMessage(String message) {
        try {
            Request chatRequest = new Request(UseCaseType.Chat, message);
            client.sendToServer(chatRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void displayMessage(String message) {
        Platform.runLater(() -> {
            // Corrected escaping for JavaScript string literal
            String cleanMessage = message.replace("\\", "\\\\")
                    .replace("'", "\'" )
                    .replace("\n", " ")
                    .replace("\r", " ");

            webEngine.executeScript("displayMessage('" + cleanMessage + "', 'server')");
        });
    }
}
