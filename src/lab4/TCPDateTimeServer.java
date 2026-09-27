package lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPDateTimeServer {

	private static final int PORT = 12345;

	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("TCP Server đang chờ Client tại port: " + PORT);

			try (Socket socket = serverSocket.accept()) {
				System.out.println("Client đã kết nối: " + socket.getInetAddress());

				BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);

				String command;

				while ((command = in.readLine()) != null) {
					System.out.println("Server nhận: " + command);

					if (command.equalsIgnoreCase("QUIT")) {
						out.println("BYE");
						System.out.println("Client yêu cầu đóng kết nối.");
						break;
					}

					String response = DateTimeService.processCommand(command);
					out.println(response);
				}
			}

		} catch (Exception e) {
			System.err.println("TCP Server lỗi: " + e.getMessage());
		}
	}
}
