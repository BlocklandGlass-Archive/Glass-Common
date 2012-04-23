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
	
	public static boolean queryAuth(String name, String ip, String blid) throws IOException {
		HttpPost query = new HttpPost("http://auth.blockland.us/authQuery.php");
		HttpParams params = new BasicHttpParams();
		params.setParameter("NAME", name);
		params.setParameter("IP", ip);
		query.setParams(params);
		
		HttpResponse response = httpclient.execute(query);
		HttpEntity resEntity = response.getEntity();
		
		if (resEntity != null) {
			InputStream is = resEntity.getContent();
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(is));

				String line;
				while((line = in.readLine()) != null) {
					if(line.startsWith("YES")) {
						return true;
					}
				}
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
}
