package lab4.part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TCPLogClient {

    private static final String HOST = "localhost";
    private static final int PORT = 6972;

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
            System.out.print("Nhap clientId: ");
            String clientId = scanner.nextLine().trim();

            // Phải gửi HELLO trước nội dung tin nhắn
            out.println("HELLO " + clientId);

            String response = in.readLine();
            System.out.println("Server: " + response);

            if (response == null || !response.startsWith("OK ")) {
                return;
            }

            System.out.println(
                "Nhap tin nhan. Go QUIT de ket thuc."
            );

            while (true) {
                System.out.print("> ");
                String message = scanner.nextLine();

                out.println(message);

                response = in.readLine();
                System.out.println("Server: " + response);

                if (message.trim().equals("QUIT")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println(
                "Loi ket noi: " + e.getMessage()
            );
        }
    }
}

