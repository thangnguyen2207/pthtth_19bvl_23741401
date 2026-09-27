package lab4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ConvertNumberServer {
	private static final int PORT = 34567;

	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Server đang chờ client kết nối tại port: " + PORT);
			
			Socket socket = serverSocket.accept();
			System.out.println("Đã kết nối Client " + socket.getInetAddress());
			
			BufferedReader in = new BufferedReader(
					new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
			
			while (true) {
				String clientMessage = in.readLine();
				if (clientMessage == null || clientMessage.equalsIgnoreCase("QUIT"))
					break;

				try {
					int number = Integer.parseInt(clientMessage);
					out.println(numberToString(number));

				} catch (NumberFormatException e) {
					out.println("INVALID_DIGIT");
				} catch (IllegalArgumentException e) {
					out.println("INVALID_DIGIT");
				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public static String numberToString(int number) {
		switch (number) {
		case 0:
			return "không";
		case 1:
			return "một";
		case 2:
			return "hai";
		case 3:
			return "ba";
		case 4:
			return "bốn";
		case 5:
			return "năm";
		case 6:
			return "sáu";
		case 7:
			return "bảy";
		case 8:
			return "tám";
		case 9:
			return "chín";

		default:
			throw new IllegalArgumentException("Unexpected value: " + number);
		}
	}
}
