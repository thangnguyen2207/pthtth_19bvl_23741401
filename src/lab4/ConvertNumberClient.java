package lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class ConvertNumberClient {
	private static final String HOST_NAME = "localhost";
	private static final int PORT = 34567;

	public static void main(String[] args) {
		System.out.println("Khởi động client...");
		
		try (Socket socket = new Socket(HOST_NAME, PORT)) {
			System.out.println("Đã kết nối tới Server - " + HOST_NAME + ", " + PORT);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
			
			String[] sendMessages = {"9", "2", "19", "Không"};
			
			for (String message : sendMessages) {
				System.out.println("Client gửi dữ liệu: " + message);
				out.println(message);
				System.out.println("Client nhận phản hồi từ Server: " + in.readLine() + "\n");
			}
			
		} catch (UnknownHostException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
