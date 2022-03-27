package com.ruskaof.client.util;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.dto.ToServerDto;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public final class ConnectionManager {
    private static final int BF_SIZE = 2048;
    private final int clientPort;
    private final int serverPort;
    private final DatagramSocket datagramSocket;

    public ConnectionManager(int clientPort, int serverPort) throws SocketException {
        this.clientPort = clientPort;
        this.serverPort = serverPort;
        datagramSocket = new DatagramSocket(this.clientPort);
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public CommandResultDto sendCommand(ToServerDto toServerDto) throws ClassNotFoundException {

        try {
            // SENDING
            byte[] toSend = serialize(toServerDto);

            final DatagramPacket datagramPacket = new DatagramPacket(toSend, toSend.length, InetAddress.getByName("127.0.0.1"), serverPort);

            datagramSocket.send(datagramPacket);

            // RECEIVINGm
            byte[] buf = new byte[BF_SIZE];
            DatagramPacket dp = new DatagramPacket(buf, BF_SIZE);
            datagramSocket.receive(dp);
            return (CommandResultDto) deserialize(dp.getData());
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResultDto("Something went wrong executing the command");
        }

    }
}
