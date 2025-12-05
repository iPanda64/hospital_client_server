package model;

/**
 * An interface for handling messages received from the server.
 * This decouples the network client from the UI implementation.
 */
public interface MessageHandler {
    void handleMessageFromServer(Object msg);
}
