package lab4.part2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPUDPServer {

    static final int TCP_PORT = 6970;
    static final int UDP_PORT = 6971;
    static final int SIZE = 1024;

    public static void main(String[] args) {
        Thread tcpThread = new Thread(() -> runTCP());
        Thread udpThread = new Thread(() -> runUDP());

        tcpThread.start();
        udpThread.start();
    }

    private static void runTCP() {
        try (ServerSocket server = new ServerSocket(TCP_PORT)) {
            System.out.println("TCP Server port " + TCP_PORT);

            while (true) {
                Socket socket = server.accept();

                Thread clientThread = new Thread(() -> {
                    try (
                        Socket s = socket;
                        DataInputStream in = new DataInputStream(
                            s.getInputStream());
                        DataOutputStream out = new DataOutputStream(
                            s.getOutputStream())
                    ) {
                        byte[] buffer = new byte[SIZE];

                        while (true) {
                            try {
                                in.readFully(buffer);
                            } catch (EOFException e) {
                                break;
                            }

                            out.write(buffer);
                            out.flush();
                        }
                    } catch (IOException e) {
                        // Client đã ngắt kết nối
                    }
                });

                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void runUDP() {
        byte[] buffer = new byte[SIZE];

        try (DatagramSocket server = new DatagramSocket(UDP_PORT)) {
            System.out.println("UDP Server port " + UDP_PORT);

            while (true) {
                DatagramPacket request =
                    new DatagramPacket(buffer, buffer.length);

                server.receive(request);

                DatagramPacket response = new DatagramPacket(
                    request.getData(),
                    request.getLength(),
                    request.getAddress(),
                    request.getPort()
                );

                server.send(response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
