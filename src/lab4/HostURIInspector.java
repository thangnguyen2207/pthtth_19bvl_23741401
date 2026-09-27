package lab4;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class HostURIInspector {

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Usage: java network.HostInspector <hostname> <URI>");
			return;
		}

		String hostname = args[0];
		String URI = args[1];

		System.out.println("== HOST INSPECTOR FOR '" + hostname + "' ==");
		try {
			InetAddress[] addresses = InetAddress.getAllByName(hostname);

			for (InetAddress address : addresses) {
				String IPType = address.getAddress().length == 4 ? "IPV4" : address.getAddress().length == 16 ? "IPV6" : "null";

				System.out.println(" IP: " + address.getHostAddress());
				System.out.println(" IP type: " + IPType);
				System.out.println(" Loopback: " + address.isLoopbackAddress());
				System.out.println(" Site local: " + address.isSiteLocalAddress());
			}
		} catch (UnknownHostException e) {
			System.err.println("Không phân giải được hostname...");
		}
		
		System.out.println();

		System.out.println("== URI INSPECTOR FOR '" + URI + "' ==");
		try {
			URI uri = new URI(URI);
			
			System.out.println("Scheme: " + uri.getScheme());
			System.out.println("Host: " + uri.getHost());
			System.out.println("Port: " + uri.getPort());
			System.out.println("Path: " + uri.getPath());
			System.out.println("Query: " + uri.getQuery());
			System.out.println("Fragment: " + uri.getFragment());
		} catch (URISyntaxException e) {
			System.err.println("URI sai định dạng: " + URI);
		}
	}
}
