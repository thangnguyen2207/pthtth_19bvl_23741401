package tk1;


import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);

            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), "UTF-8"));
            PrintWriter out = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
            try (Scanner sc = new Scanner(System.in, "UTF-8")) {
				System.out.print("Nhập tên: ");
				String name = sc.nextLine();
				out.println(name);

				Thread receiver = new Thread(() -> {
				    try {
				        String message;

				        while ((message = in.readLine()) != null) {
				            if (message.startsWith("ROOMS|")) {
				                String[] rooms = message.split("\\|");

				                System.out.println("\nDanh sách phòng:");
				                for (int i = 1; i < rooms.length; i++) {
				                    System.out.println(i + ". " + rooms[i]);
				                }

				                System.out.println("Nhập số phòng để tham gia.");
				                System.out.println("Nhập /exit để thoát.");
				            } else if (message.startsWith("JOINED|")) {
				                System.out.println("Đã vào phòng " + message.substring(7));
				                System.out.println("Nhập tin nhắn, /quit để rời phòng.");
				            } else {
				                System.out.println(message);
				            }
				        }
				    } catch (IOException e) {
				        System.out.println("Đã ngắt kết nối server.");
				    }
				});

				receiver.start();

				while (true) {
				    String message = sc.nextLine();

				    if (message.equalsIgnoreCase("/exit")) {
				        out.println("/EXIT");
				        socket.close();
				        break;
				    } else if (message.equals("/quit")) {
				        out.println("/quit");
				    } else if (message.matches("[1-3]")) {
				        out.println("JOIN|" + message);
				    } else {
				        out.println(message);
				    }
				}
			}

        } catch (IOException e) {
            System.out.println("Không thể kết nối server.");
        }
    }
}

