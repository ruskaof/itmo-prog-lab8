package com.ruskaof.server.connection.tcp;

import java.net.Socket;

public class Client {
    private final Socket clientSocket;

    public Client(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
}
