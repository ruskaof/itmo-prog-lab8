package com.ruskaof.client.connection;

import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataCantBeSentException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectionManagerTCP implements ConnectionManager {
    private final Socket socket;
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    public ConnectionManagerTCP(int port, String ip) throws IOException {
        this.socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port));
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public CommandResultDto sendCommand(CommandFromClientDto commandFromClientDto) throws DataCantBeSentException {
        try {
            System.out.println("Starting to send");
            objectOutputStream.writeObject(commandFromClientDto);
            System.out.println("Starting to receive");
            return (CommandResultDto) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
