package lab4.part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPChatboxServer {

    private static final int PORT = 6969;

    // Danh sách Client đang kết nối, an toàn khi nhiều Thread truy cập
    private static final ConcurrentHashMap<String, ClientHandler> clients =
            new ConcurrentHashMap<>();

    // Thread pool xử lý các kết nối Client
    private static final ExecutorService threadPool =
            Executors.newFixedThreadPool(20);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Chat Server đang chạy tại port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();

                // Giao việc xử lý kết nối cho thread pool
                threadPool.execute(() -> handleClient(socket));
            }

        } catch (IOException e) {
            System.err.println("Lỗi Server: " + e.getMessage());
        } finally {
            threadPool.shutdown();
        }
    }

    private static void handleClient(Socket socket) {
        ClientHandler client = null;

        try {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream(),
                            StandardCharsets.UTF_8));

            PrintWriter out = new PrintWriter(
                    socket.getOutputStream(), true,
                    StandardCharsets.UTF_8);

            // Dòng đầu tiên Client gửi lên là nickname
            String nickname = in.readLine();

            if (nickname == null) {
                return;
            }

            nickname = nickname.trim();

            if (!nickname.matches("[a-zA-Z0-9_]{1,20}")) {
                out.println("ERR Nickname khong hop le");
                return;
            }

            client = new ClientHandler(nickname, socket, in, out);

            // putIfAbsent bảo đảm không đăng ký trùng nickname
            if (clients.putIfAbsent(nickname, client) != null) {
                out.println("ERR Nickname da ton tai");
                client = null;
                return;
            }

            client.send("OK Xin chao " + nickname);
            System.out.println(nickname + " da ket noi");

            broadcast("*** " + nickname + " da tham gia chat ***",
                    nickname);

            String message;

            while ((message = in.readLine()) != null) {
                String trimmed = message.trim();

                if (trimmed.equalsIgnoreCase("USERS")) {
                    client.send("USERS " + String.join(", ",
                            clients.keySet()));
                } else if (trimmed.equalsIgnoreCase("QUIT")) {
                    client.send("BYE");
                    break;
                } else if (trimmed.regionMatches(
                        true, 0, "MSG ", 0, 4)) {

                    String content = message.substring(4).trim();

                    if (content.isEmpty()) {
                        client.send("ERR Tin nhan rong");
                    } else {
                        broadcast("[" + nickname + "] " + content,
                                nickname);
                    }
                } else {
                    client.send(
                            "ERR Lenh khong hop le. Dung USERS, MSG noi_dung hoac QUIT");
                }
            }

        } catch (IOException e) {
            System.out.println("Ket noi bi gian doan.");
        } finally {
            if (client != null) {
                // Chỉ xóa đúng phiên Client này
                clients.remove(client.nickname, client);

                broadcast("*** " + client.nickname
                        + " da roi chat ***", client.nickname);

                System.out.println(
                        client.nickname + " da ngat ket noi");
            }

            try {
                socket.close();
            } catch (IOException ignored) {
            }
        }
    }

    // Gửi tin nhắn đến tất cả Client trừ người gửi
    private static void broadcast(String message, String sender) {
        for (ClientHandler client : clients.values()) {
            if (!client.nickname.equals(sender)) {
                client.send(message);
            }
        }
    }

    private static class ClientHandler {
        private final String nickname;
        private final Socket socket;
        private final BufferedReader in;
        private final PrintWriter out;

        ClientHandler(String nickname, Socket socket,
                      BufferedReader in, PrintWriter out) {
            this.nickname = nickname;
            this.socket = socket;
            this.in = in;
            this.out = out;
        }

        // Tránh các Thread ghi đồng thời lên cùng luồng xuất
        synchronized void send(String message) {
            out.println(message);
        }
    }
}
