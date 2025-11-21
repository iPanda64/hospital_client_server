package controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import model.AppClient;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    private AppClient client;
    private WebEngine webEngine;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // --- Setup OCSF Client ---
        client = new AppClient("localhost", 5555, this);
        try {
            client.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // --- Setup JavaFX WebView ---
        WebView webView = new WebView();
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);

        // Set up the bridge from JavaScript to Java
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                JSObject jsObject = (JSObject) webEngine.executeScript("window");
                jsObject.setMember("javaBridge", new JavaBridge(client));
            }
        });

        // Load the HTML file from the view directory
        URL url = getClass().getResource("/view/index.html");
        webEngine.load(url.toExternalForm());

        // --- Setup Stage ---
        primaryStage.setTitle("Hospital Client");
        primaryStage.setScene(new Scene(webView, 1000, 500));
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            try {
                if (client.isConnected()) {
                    client.closeConnection();
                }
            } catch (IOException e) {
                // Ignore
            }
        });
    }

    // Method for the client to call to update the UI
    public void displayMessage(String message) {
        Platform.runLater(() -> {
            webEngine.executeScript("displayMessage('" + message.replace("\\", "\\\\").replace("'", "\\'") + "', 'server')");
        });
    }
}