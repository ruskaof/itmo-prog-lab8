package com.ruskaof.server.connection.tcp;

import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.server.util.Logger;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ClientServant implements Runnable {
    private final Socket clientSocket;
    private final CommandHandler commandHandler;

    public ClientServant(Socket clientSocket, CommandHandler commandHandler) {
        this.clientSocket = clientSocket;
        this.commandHandler = commandHandler;
    }

    @Override
    public void run() {
        try {
            final ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            final ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            Logger.log("Started a new client servant");
            while (true) {
                try {
                    final CommandFromClientDto commandFromClientDto = (CommandFromClientDto) objectInputStream.readObject();
                    Logger.log("Found new command, executing it");

                    final CommandResultDto commandResultDto = commandHandler.handle(commandFromClientDto);
                    Logger.log("Sending the result to the client");
                    objectOutputStream.writeObject(commandResultDto);
                } catch (SocketException e) {
                    Logger.log("A client disconnected");
                    break;
                } catch (EOFException ignored) {
                    System.out.println("eof");
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
