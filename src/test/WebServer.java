package test;
import java.net.*;
import java.io.*;

class WebServer {
	public static void main(String[] args) {
		try {
			setUpServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public static void setUpServer() throws IOException{
		ServerSocket server = new ServerSocket(8080);
		System.out.println("服务器创建成功！");
			while(true) {
				try {
					Socket client = server.accept();
					InputStream in = client.getInputStream();
					OutputStream ous = client.getOutputStream();
					
					BufferedReader buf = new BufferedReader(new InputStreamReader(in));
				
					String str = "<h1>Hello World!</h1>";
					
					ous.write(str.getBytes());
					ous.flush();
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
					continue;
				}
				
				
		}
	}
}