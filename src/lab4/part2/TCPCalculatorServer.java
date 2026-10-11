package lab4.part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TCPCalculatorServer {
	public static void main(String[] args) {
		int port = 6969;
		
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server listening on port: " + port);
			
			Socket socket = serverSocket.accept();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
			
			while (true) {
				String message = in.readLine();
				if (message == null) {
					return;
				}
				String[] messages = message.split(" ");
				
				if (messages.length != 4 || !messages[0].equalsIgnoreCase("calc")) {
					out.println("ERR INVALID_FORMAT");
					continue;
				}
				
				try {
					int a = Integer.parseInt(messages[2]);
					int b = Integer.parseInt(messages[3]);
					
					if (b == 0 && messages[1].equals("/")) {
						out.println("ERR DIVIDE_BY_ZERO");
						continue;
					}
					out.println(calculate(messages[1], a, b));
					
				} catch (NumberFormatException e) {
					out.println("ERR INVALID_NUMBER");
				} catch (IllegalArgumentException e) {
					out.println("ERR UNSUPPORTED_OPERATOR");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static double calculate(String type, int a, int b) {
		switch (type) {
		case "+": {
			return a + b;
		}
		case "-": {
			return a - b;
		}
		case "*": {
			return a * b;
		}
		case "/": {
			return (double) a / b;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
}
