package controller;

import javafx.concurrent.Worker;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;

public class BaseViewController {

    protected final ClientController clientController;
    protected final Client client;
    protected final WebView webView;
    protected final WebEngine webEngine;

    public BaseViewController(ClientController clientController, Client client, String htmlFile) {
        this.clientController = clientController;
        this.client = client;
        this.webView = new WebView();
        this.webEngine = webView.getEngine();
        this.webEngine.setJavaScriptEnabled(true);

        onPageLoadFinished(() -> {
            JSObject jsObject = (JSObject) webEngine.executeScript("window");
            jsObject.setMember("javaBridge", getBridgeObject());
        });

        URL url = getClass().getResource("/view/" + htmlFile);
        if (url != null) {
            webEngine.load(url.toExternalForm());
        } else {
            webEngine.loadContent("<html><body><h1>Error: Resource not found: /view/" + htmlFile + "</h1></body></html>");
        }
    }

    protected void onPageLoadFinished(Runnable action) {
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                action.run();
            }
        });
    }

    public Parent getView() {
        return webView;
    }

    protected Object getBridgeObject() {
        return this;
    }
}
