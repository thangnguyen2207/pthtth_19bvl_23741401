package lab4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPDateTimeClient {

	private static final String HOST = "localhost";
	private static final int PORT = 12346;

	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket()) {
			InetAddress serverAddress = InetAddress.getByName(HOST);

			String[] commands = { "DATE", "TIME", "DATETIME", "ABC" };

			for (String command : commands) {
				System.out.println("\nClient gửi: " + command);
				byte[] sendBuffer = command.getBytes(StandardCharsets.UTF_8);

				DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, PORT);
				socket.send(sendPacket);

				byte[] receiveBuffer = new byte[1024];
				
				DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
				socket.receive(receivePacket);

				String response = new String(receivePacket.getData(), receivePacket.getOffset(),
						receivePacket.getLength(), StandardCharsets.UTF_8);
				System.out.println("Server trả lời: " + response);
			}
		} catch (Exception e) {
			System.err.println("UDP Client lỗi: " + e.getMessage());
		}
	}
}