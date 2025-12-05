package app;

import controller.ClientController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.ViewType;

import java.io.IOException;

public class Main extends Application {

    private ClientController clientController;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        clientController = new ClientController(primaryStage);
        clientController.switchView(ViewType.LOGIN);
    }

    @Override
    public void stop() throws Exception {
        if (clientController != null) {
            clientController.closeConnection();
        }
        super.stop();
    }
}
