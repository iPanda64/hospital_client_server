

import ocsf.EchoServer; // Import EchoServer to get DEFAULT_PORT
import controller.Server;
import controller.ServerController;
import view.ServerConsole;

public class Main {
    public static void main(String[] args) {
        int port = 0; //Port to listen on

        try {
            port = Integer.parseInt(args[0]); //Get port from command line
        } catch(Throwable t) {
            port = EchoServer.DEFAULT_PORT; //Set port to 5555
        }

        // Create the ServerController
        ServerController controller = new ServerController(null); // Server is passed later

        // Create the Server instance, passing the controller
        Server server = new Server(port, controller);

        // Set the server in the controller (circular dependency, but common in this pattern)
        controller.setServer(server);

        // Create the ServerConsole, passing the controller
        ServerConsole console = new ServerConsole(controller);

        try {
            server.listen(); //Start listening for connections
            console.accept(); // Start accepting console commands
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
            ex.printStackTrace();
        }
    }
}
