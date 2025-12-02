package com.example.server;

import ocsf.server.EchoServer;

public class Main {
    public static void main(String[] args) {
        int port = 8080; // The port the server will listen on
        EchoServer server = new EchoServer(port);
        try {
            server.listen(); // Start listening for connections
            System.out.println("Server started on port " + port);
        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }
}System.err.println("ERROR - Could not listen for clients!");
            e.printStackTrace();
        }
    }
}