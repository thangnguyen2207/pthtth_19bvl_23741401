package lab4.part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPLogServer {

    private static final int PORT = 6972;

    private static final Path LOG_DIR =
            Path.of("data", "logs");

    private static final ExecutorService pool =
            Executors.newFixedThreadPool(20);

    // Khóa riêng cho từng clientId khi ghi log
    private static final ConcurrentHashMap<String, Object> locks =
            new ConcurrentHashMap<>();

    public static void main(String[] args) {
        try {
            Files.createDirectories(LOG_DIR);

            try (ServerSocket server = new ServerSocket(PORT)) {
                System.out.println(
                        "Log Server dang chay tai port " + PORT);

                while (true) {
                    Socket socket = server.accept();
                    pool.execute(() -> handleClient(socket));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    private static void handleClient(Socket socket) {
        try (
            Socket clientSocket = socket;
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    clientSocket.getInputStream(),
                    StandardCharsets.UTF_8
                )
            );
            PrintWriter out = new PrintWriter(
                clientSocket.getOutputStream(),
                true,
                StandardCharsets.UTF_8
            )
        ) {
            // Yêu cầu đầu tiên phải có dạng HELLO clientId
            String hello = in.readLine();

            if (hello == null) {
                return;
            }

            String[] parts = hello.trim().split("\\s+", 2);

            if (parts.length != 2
                    || !parts[0].equals("HELLO")
                    || !parts[1].matches("[A-Za-z0-9_-]+")) {
                out.println("ERR INVALID_HELLO");
                return;
            }

            String clientId = parts[1];
            String remoteAddress =
                    socket.getRemoteSocketAddress().toString();

            Path logFile =
                    LOG_DIR.resolve(clientId + ".txt");

            out.println("OK " + clientId);

            System.out.println(
                    clientId + " connected from " + remoteAddress);

            String message;

            while ((message = in.readLine()) != null) {
                // Chỉ dòng QUIT riêng biệt mới kết thúc phiên
                if (message.trim().equals("QUIT")) {
                    out.println("BYE");
                    break;
                }

                String timestamp = OffsetDateTime.now()
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

                String logLine = timestamp + " | "
                        + remoteAddress + " | " + message
                        + System.lineSeparator();

                Object lock = locks.computeIfAbsent(
                        clientId, _ -> new Object());

                synchronized (lock) {
                    Files.writeString(
                        logFile,
                        logLine,
                        StandardCharsets.UTF_8,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND
                    );
                }

                out.println("OK SAVED");
            }

        } catch (IOException e) {
            System.out.println(
                    "Client ngat ket noi hoac loi ghi log: "
                    + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }
}

