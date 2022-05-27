package com.ruskaof.server.connection.tcp;

import com.ruskaof.common.util.State;
import com.ruskaof.server.connection.MainApp;
import com.ruskaof.server.util.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainAppTCP implements MainApp {
    private final InetSocketAddress address;
    private ServerSocket server;

    public MainAppTCP(int port, String ip) {
        this.address = new InetSocketAddress(ip, port);
    }

    @Override
    public void start(State<Boolean> isWorking) throws IOException {
        initSerer();

        final Thread clientReceptionThread = new Thread(new ClientReception(
                isWorking, server, clients
        ));
        clientReceptionThread.setDaemon(true);
        clientReceptionThread.start();

        while (isWorking.getValue()) {

        }
    }


    private void initSerer() throws IOException {
        server = new ServerSocket();
        server.bind(new InetSocketAddress("0.0.0.0", 0));
        Logger.log("The server was built on the port: " + server.getLocalPort());
    }
}

class ClientReception implements Runnable {
    private final State<Boolean> isWorking;
    private final ServerSocket server;
    private final HashSet<Client> clients;
    private final ExecutorService clientsThreads = Executors.newCachedThreadPool();

    public ClientReception(State<Boolean> isWorking, ServerSocket server, HashSet<Client> clients) {
        this.isWorking = isWorking;
        this.server = server;
        this.clients = clients;
    }


    @Override
    public void run() {
        while (isWorking.getValue()) {
            try {
                final Socket clientSocket = server.accept();
                final Client client = new Client(clientSocket);
                clients.add(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
