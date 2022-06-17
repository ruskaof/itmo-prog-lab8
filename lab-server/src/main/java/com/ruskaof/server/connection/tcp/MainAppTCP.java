package com.ruskaof.server.connection.tcp;

import com.ruskaof.common.util.State;
import com.ruskaof.server.connection.MainApp;
import com.ruskaof.server.util.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class MainAppTCP implements MainApp {
    private final CommandHandler commandHandler;

    private ServerSocket server;

    public MainAppTCP(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }


    @Override
    public void start(State<Boolean> isWorking) throws IOException {
        initSerer();

        final Thread clientReceptionThread = new Thread(new ClientReception(
                isWorking, server, commandHandler
        ));
        clientReceptionThread.setDaemon(true);
        clientReceptionThread.start();


    }


    private void initSerer() throws IOException {
        server = new ServerSocket();
        server.bind(new InetSocketAddress("0.0.0.0", 7813));
        Logger.log("The server was built on the port: " + server.getLocalPort());
    }

}

