package lab4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class UDPDateTimeServer {

	private static final int PORT = 12346;

	public static void main(String[] args) {
		try (DatagramSocket socket = new DatagramSocket(PORT)) {
			System.out.println("UDP Server đang chờ dữ liệu tại port: " + PORT);

			while (true) {
				byte[] receiveBuffer = new byte[1024];
				DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
				socket.receive(receivePacket);
				String command = new String(receivePacket.getData(), receivePacket.getOffset(),
						receivePacket.getLength(), StandardCharsets.UTF_8);
				
				System.out.println("Server nhận: " + command);

				String response = DateTimeService.processCommand(command);
				byte[] sendBuffer = response.getBytes(StandardCharsets.UTF_8);

				InetAddress clientAddress = receivePacket.getAddress();
				int clientPort = receivePacket.getPort();

				DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, clientAddress,
						clientPort);
				socket.send(sendPacket);
			}
		} catch (Exception e) {
			System.err.println("UDP Server lỗi: " + e.getMessage());
		}
	}
}
