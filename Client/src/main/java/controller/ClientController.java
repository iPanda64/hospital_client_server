package controller;

import controller.handler.ChatResponseHandler;
import controller.handler.LoginResponseHandler;
import controller.handler.ResponseHandler;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ClientController implements MessageHandler {

    private final Stage primaryStage;
    private final Client client;
    private static BaseViewController activeController;
    private final Map<UseCaseType, ResponseHandler> responseHandlers;


    public ClientController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("hospital");
        this.client = new Client("localhost", 5555, this);

        this.responseHandlers = new HashMap<>();
        responseHandlers.put(UseCaseType.Login, new LoginResponseHandler(this));
        responseHandlers.put(UseCaseType.Chat, new ChatResponseHandler(this));



        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void switchView(ViewType viewType) {
        switch (viewType) {
            case LOGIN:
                Platform.runLater(() -> {
                    setView(new LoginController(this, client));
                    primaryStage.show();
                });
                break;
            case CHAT:
                Platform.runLater(() -> setView(new ChatController(this, client)));
                break;
            case CREATE_ACCOUNT:
                Platform.runLater(() -> setView(new CreateAccountController(this, client)));
                break;
        }
    }

    private void setView(BaseViewController controller) {
        this.activeController = controller;
        primaryStage.setScene(new Scene(controller.getView(), 1000, 500));
    }

    @Override
    public void handleMessageFromServer(Object msg) {
        if (msg instanceof Response) {
            Response<?> response = (Response<?>) msg;
            System.out.println("Response for Request ID: " + response.getId() + ", Successful: " + response.isSuccessful() + ", Response Object: " + response.getResponseObject());

            UseCaseType useCase = (response.getRequest() != null) ? response.getRequest().getUseCaseType() : null;
            ResponseHandler handler = responseHandlers.get(useCase);

            if (handler != null) {
                handler.handle(response);
            }
        }
    }

    public void closeConnection() throws IOException {
        if (client != null && client.isConnected()) {
            client.closeConnection();
        }
    }

    public BaseViewController getActiveController() {
        return activeController;
    }
}