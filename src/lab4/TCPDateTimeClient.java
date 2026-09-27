package lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPDateTimeClient {
	private static final String HOST = "localhost";
	private static final int PORT = 12345;

	public static void main(String[] args) {
		try (Socket socket = new Socket(HOST, PORT)) {
			System.out.println("Đã kết nối TCP Server.");

			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

			String[] commands = { "DATE", "TIME", "DATETIME", "ABC", "QUIT" };

			for (String command : commands) {
				System.out.println("\nClient gửi: " + command);
				out.println(command);
				
				String response = in.readLine();

				System.out.println("Server trả lời: " + response);

				if (command.equalsIgnoreCase("QUIT")) {
					break;
				}
			}

		} catch (Exception e) {
			System.err.println("TCP Client lỗi: " + e.getMessage());
		}
	}
}
