package blocklandglass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.HttpParams;
import org.apache.http.params.BasicHttpParams;

public class Authenticator {
	private static HttpClient httpclient = new DefaultHttpClient();

	public static boolean queryAuth() {
		//Place holder so that I can compile stuff without errors
		return false;
	}
	
	public static boolean queryAuth(String name, String ip, String blid) {
		if((getBLID(name, ip)) != -1) {
			return true;
		} else {
			return false;
		}
	}
	
	public static int getBLID(String name, String ip) {
		HttpPost query = new HttpPost("http://auth.blockland.us/authQuery.php");
		HttpParams params = new BasicHttpParams();
		params.setParameter("NAME", name);
		params.setParameter("IP", ip);
		query.setParams(params);
		
		try {
			HttpResponse response = httpclient.execute(query);
			HttpEntity resEntity = response.getEntity();
			
			if (resEntity != null) {
				InputStream is = null;
				try {
					is = resEntity.getContent();
					BufferedReader in = new BufferedReader(new InputStreamReader(is));
	
					String line;
					while((line = in.readLine()) != null) {
						if(line.startsWith("YES")) {
							try {
								return Integer.parseInt(line.substring(4));
							} catch (NumberFormatException e) {
								return -1;
							}
						}
					}
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if(is != null) {
							is.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
						return -1;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
