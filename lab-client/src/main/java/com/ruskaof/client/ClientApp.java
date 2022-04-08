package com.ruskaof.client;

import com.ruskaof.common.dto.CommandResultDto;
import com.ruskaof.common.dto.ToServerDto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public final class ClientApp {
    private static final int BF_SIZE = 2048;
    private final int clientPort;
    private final int serverPort;
    private final String ip;

    public ClientApp(int clientPort, int serverPort, String ip) {
        this.clientPort = clientPort;
        this.serverPort = serverPort;
        this.ip = ip;
    }

    public CommandResultDto sendCommand(ToServerDto toServerDto) throws ClassNotFoundException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {

            // Send
            datagramChannel.bind(new InetSocketAddress(ip, clientPort));
            byte[] buf1 = serialize(toServerDto);
            ByteBuffer sendBuffer = ByteBuffer.wrap(buf1);
            SocketAddress socketAddress = new InetSocketAddress(ip, serverPort);

            int limit = 65507;
            while (sendBuffer.hasRemaining()) {
                if (sendBuffer.remaining() < limit) {
                    limit = sendBuffer.remaining();
                }
                ByteBuffer limitedSendBuffer = sendBuffer.slice();
                limitedSendBuffer.limit(limit);
                datagramChannel.send(limitedSendBuffer, socketAddress);
                sendBuffer.position(sendBuffer.position() + limit);
            }

            // Receive
            byte[] buf2 = new byte[BF_SIZE];
            ByteBuffer receiveBuffer = ByteBuffer.wrap(buf2);
            datagramChannel.receive(receiveBuffer);

            return (CommandResultDto) deserialize(buf2);
        } catch (IOException e) {
            e.printStackTrace();
            return new CommandResultDto("Something went wrong executing the command");
        }

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
}
