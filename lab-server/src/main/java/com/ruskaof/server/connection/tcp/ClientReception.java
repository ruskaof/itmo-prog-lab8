package com.ruskaof.server.connection.tcp;

import com.ruskaof.common.util.State;
import com.ruskaof.server.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class ClientReception implements Runnable {
    private final CommandHandler commandHandler;
    private final State<Boolean> isWorking;
    private final ServerSocket server;

    // Making all the clients thread daemons
    private final ExecutorService clientsThreads = Executors.newCachedThreadPool(
            r -> {
                final Thread thread = Executors.defaultThreadFactory().newThread(r);
                thread.setDaemon(true);
                return thread;
            }
    );

    ClientReception(State<Boolean> isWorking, ServerSocket server, CommandHandler commandHandler) {
        this.isWorking = isWorking;
        this.server = server;
        this.commandHandler = commandHandler;
    }


    @Override
    public void run() {
        while (isWorking.getValue()) {
            try {
                final Socket clientSocket = server.accept();
                Logger.log("Found new client, accepting");
                final ClientServant client = new ClientServant(clientSocket, commandHandler);
                clientsThreads.submit(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
