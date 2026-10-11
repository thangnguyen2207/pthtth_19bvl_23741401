package lab4.part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class TCPCalculatorClient {
	public static void main(String[] args) {
		int port = 6969;
		String host = "localhost";
		
		try (Socket socket = new Socket(host, port)) {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8);
			
			String clientMessage1 = "CALC + 100 200";
			String clientMessage2 = "CALC / 10 0";
			String clientMessage3 = "CALC % 10 3";
			String clientMessage4 = "CALC + a 2";
			String clientMessage5 = "CALC a 2";
			String[] clientMessages = {clientMessage1, clientMessage2, clientMessage3, clientMessage4, clientMessage5};
			
			for (int i = 0; i < 5; i++) {
				out.println(clientMessages[i]);
				System.out.println("Gửi: " + clientMessages[i]);
				System.out.println("Nhận: " + in.readLine() + "\n");
				
				Thread.sleep(2000);
				
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
