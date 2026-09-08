package Example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Vidu3 {
	 public static void main(String[] args) {
	        InputStream is = System.in;
	        InputStreamReader isr = new InputStreamReader(is);
	        BufferedReader br = new BufferedReader(isr);
	        while (true) {
	            try {
	                String line = br.readLine();
	                if (line == null || line.equals("q")) {
	                    break;
	                }
	                System.out.println(line);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    }
}
