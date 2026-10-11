package lab4.part2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class TCPUDPBenchmark {

    static final String HOST = "127.0.0.1";
    static final int TCP_PORT = 6970;
    static final int UDP_PORT = 6971;

    static final int COUNT = 1000;
    static final int SIZE = 1024;
    static final int TIMEOUT_MS = 1000;
    static final int REPEATS = 5;

    public static void main(String[] args) throws Exception {
        InetAddress address = InetAddress.getByName(HOST);

        // Khởi động kết nối và làm nóng JVM trước khi đo
        try (Socket socket = new Socket(HOST, TCP_PORT)) {
            socket.setSoTimeout(TIMEOUT_MS);
        }

        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT_MS);
        }

        System.out.println("TCP benchmark");
        for (int i = 1; i <= REPEATS; i++) {
            Result r = testTCP();
            printResult("TCP", i, r);
        }

        System.out.println("\nUDP benchmark");
        for (int i = 1; i <= REPEATS; i++) {
            Result r = testUDP(address);
            printResult("UDP", i, r);
        }
    }

    static Result testTCP() throws IOException {
        try (Socket socket = new Socket(HOST, TCP_PORT)) {
            socket.setSoTimeout(TIMEOUT_MS);

            DataOutputStream out = new DataOutputStream(
                socket.getOutputStream());
            DataInputStream in = new DataInputStream(
                socket.getInputStream());

            byte[] request = new byte[SIZE];
            byte[] response = new byte[SIZE];

            long start = System.nanoTime();
            int received = 0;

            for (int i = 0; i < COUNT; i++) {
                prepareMessage(request, i);

                out.write(request);
                out.flush();

                try {
                    in.readFully(response);
                    if (Arrays.equals(request, response)) {
                        received++;
                    }
                } catch (SocketTimeoutException e) {
                    // Dừng lượt đo nếu TCP không phản hồi đúng hạn
                    break;
                }
            }

            long elapsed = System.nanoTime() - start;
            return new Result(elapsed, received);
        }
    }

    static Result testUDP(InetAddress address)
            throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT_MS);

            byte[] request = new byte[SIZE];
            byte[] response = new byte[SIZE];

            long start = System.nanoTime();
            int received = 0;

            for (int i = 0; i < COUNT; i++) {
                prepareMessage(request, i);

                DatagramPacket packet = new DatagramPacket(
                    request, request.length, address, UDP_PORT);
                socket.send(packet);

                DatagramPacket reply = new DatagramPacket(
                    response, response.length);

                try {
                    socket.receive(reply);

                    if (reply.getLength() == SIZE
                            && reply.getPort() == UDP_PORT
                            && reply.getAddress().equals(address)
                            && Arrays.equals(request,
                                Arrays.copyOf(reply.getData(),
                                    reply.getLength()))) {
                        received++;
                    }
                } catch (SocketTimeoutException e) {
                    // Ghi nhận thiếu phản hồi, tiếp tục thông điệp sau
                }
            }

            long elapsed = System.nanoTime() - start;
            return new Result(elapsed, received);
        }
    }

    static void prepareMessage(byte[] data, int sequence) {
        Arrays.fill(data, (byte) 'A');
        byte[] id = ByteBuffer.allocate(4).putInt(sequence).array();
        System.arraycopy(id, 0, data, 0, id.length);
    }

    static void printResult(String protocol, int run, Result r) {
        System.out.printf(
            "%s | Lan %d | Thoi gian: %.3f ms | "
                + "Phan hoi: %d/%d%n",
            protocol, run, r.nanos / 1_000_000.0,
            r.received, COUNT);
    }

    static class Result {
        long nanos;
        int received;

        Result(long nanos, int received) {
            this.nanos = nanos;
            this.received = received;
        }
    }
}
