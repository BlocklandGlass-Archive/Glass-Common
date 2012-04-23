package blocklandglass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Authenticator {
	public static boolean queryAuth() {
		//Place holder so that I can compile stuff without errors
		return false;
	}
	
	public static boolean queryAuth(String name, String ip, String blid) {
		String data = "NAME=" + name + "&IP=" + ip;
		String query = "POST /quthQuery.php HTTP/1.1\n";
		query = query + "Host: auth.blockland.us\n";
		query = query + "Content-Type: application/x-www-form-urlencoded\n";
		query = query + "Content-Length:" + data.length() + "\n";
		query = query + "\n" + data + "\n";
		
		try {
			Socket sock = new Socket("auth.blockland.us", 80);
			PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			
			out.write(query);
			String line;
			while((line = in.readLine()) != null) {
				if(line.startsWith("YES")) {
					return true;
				}
			}
			return false;
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
