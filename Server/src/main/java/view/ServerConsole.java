package view;

import controller.ServerController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerConsole {

    private ServerController controller;

    public ServerConsole(ServerController controller) {
        this.controller = controller;
    }

    // Placeholder for future console input handling
    public void accept() {
        try {
            BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
            String message;

            while (true) {
                message = fromConsole.readLine();
                // Here you would process console commands, e.g.,
                // controller.handleConsoleCommand(message);
                System.out.println("Console input (not processed yet): " + message);
            }
        } catch (Exception ex) {
            System.out.println("Unexpected error while reading from console!");
        }
    }
}