package controller.handler;

import controller.BaseViewController;
import controller.ChatController;
import controller.ClientController;
import model.Response;
import controller.handler.ResponseHandler;

public class ChatResponseHandler implements ResponseHandler {
    private final ClientController clientController;

    public ChatResponseHandler(ClientController clientController) {
        this.clientController = clientController;
    }

    @Override
    public void handle(Response<?> response) {
        BaseViewController activeController = clientController.getActiveController();
        if (activeController instanceof ChatController) {
            ((ChatController) activeController).displayMessage(response.getResponseObject().toString());
        }
    }
}