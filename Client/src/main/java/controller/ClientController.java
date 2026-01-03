package controller;

import controller.handler.*;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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
    private static int currentId=-1;

    public ClientController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("hospital");
        this.client = new Client("localhost", 5555, this);

        this.responseHandlers = new HashMap<>();
        responseHandlers.put(UseCaseType.Login, new LoginResponseHandler(this));
        responseHandlers.put(UseCaseType.Chat, new ChatResponseHandler(this));
        responseHandlers.put(UseCaseType.ViewAccount, new ViewAccountResponseHandler(this));
        responseHandlers.put(UseCaseType.CreateAccount, new CreateAccountHandler(this));
        responseHandlers.put(UseCaseType.AdminViewAllAccounts, new AdminCrudResponseHandler(this));
        responseHandlers.put(UseCaseType.AdminDeleteUser, new AdminCrudResponseHandler(this));
        responseHandlers.put(UseCaseType.AdminAddUser, new AdminCrudResponseHandler(this));
        responseHandlers.put(UseCaseType.AdminEditUser, new AdminCrudResponseHandler(this));
        responseHandlers.put(UseCaseType.PacientViewFacturi, new ViewFacturiResponseHandler(this));



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
            case VIEW_ACCOUNT:
                Platform.runLater(() -> setView(new ViewAccountController(this, client)));
                break;
            case CREATE_ACCOUNT:
                Platform.runLater(() -> setView(new CreateAccountController(this, client)));
                break;
            case ADMIN:
                Platform.runLater(() -> setView(new AdminCRUDController(this, client)));
                break;
            case VIEW_FACTURI:
                Platform.runLater(() -> setView(new ViewFacturiController(this, client)));
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

    public void showError(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    public void showSuccess(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    public static int getCurrentId(){
        return currentId;
    }
    public static void setCurrentId(int newId){
        currentId=newId;
    }
}