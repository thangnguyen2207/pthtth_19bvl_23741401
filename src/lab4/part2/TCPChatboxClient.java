package lab4.part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPChatboxClient {

    private static final String HOST = "localhost";
    private static final int PORT = 6969;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(HOST, PORT);
            BufferedReader in = new BufferedReader(
                new InputStreamReader(
                    socket.getInputStream(),
                    StandardCharsets.UTF_8
                )
            );
            PrintWriter out = new PrintWriter(
                socket.getOutputStream(),
                true,
                StandardCharsets.UTF_8
            );
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.print("Nhap nickname: ");
            String nickname = scanner.nextLine().trim();

            // Gửi nickname ngay sau khi kết nối
            out.println(nickname);

            // Đọc phản hồi xác nhận nickname
            String response = in.readLine();

            if (response == null || response.startsWith("ERR ")) {
                System.out.println(
                    response == null
                        ? "Server da dong ket noi."
                        : response
                );
                return;
            }

            System.out.println(response);
            System.out.println("Lenh: USERS | MSG noi_dung | QUIT");

            // Thread riêng để liên tục nhận tin nhắn từ Server
            Thread receiver = new Thread(() -> {
                try {
                    String message;

                    while ((message = in.readLine()) != null) {
                        System.out.println("\n" + message);
                        System.out.print("> ");
                    }

                    System.out.println(
                        "\nDa mat ket noi voi Server."
                    );

                } catch (IOException e) {
                    System.out.println(
                        "Khong the tiep tuc nhan tin nhan."
                    );
                }
            });

            receiver.setDaemon(true);
            receiver.start();

            // Luồng chính chuyên nhập và gửi lệnh
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine().trim();

                if (message.isEmpty()) {
                    continue;
                }

                out.println(message);

                if (message.equalsIgnoreCase("QUIT")) {
                    break;
                }

                System.out.print("> ");
            }

        } catch (IOException e) {
            System.out.println(
                "Khong the ket noi Server: " + e.getMessage()
            );
        }
    }
}
