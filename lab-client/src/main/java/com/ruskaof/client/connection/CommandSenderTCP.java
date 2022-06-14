package com.ruskaof.client.connection;

import com.ruskaof.client.ClientApi;
import com.ruskaof.common.dto.CommandFromClientDto;
import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.util.DataCantBeSentException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class CommandSenderTCP implements CommandSender {
    private final ObjectOutputStream objectOutputStream;
    private final ObjectInputStream objectInputStream;

    public CommandSenderTCP(int port, String ip) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(ip, port));
        objectInputStream = new ObjectInputStream(socket.getInputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
    }

    @Override
    public CommandResultDto sendCommand(CommandFromClientDto commandFromClientDto) throws DataCantBeSentException {
        try {
            objectOutputStream.writeObject(commandFromClientDto);
            return (CommandResultDto) objectInputStream.readObject();
        } catch (IOException e) {
            ClientApi.getInstance().notifyDisconnect();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
