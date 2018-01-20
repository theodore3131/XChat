package server;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer extends Thread{
	boolean flag = false;
	int port;
	
	public ChatServer(int port) {
		this.port = port;
	}
	
	public void setflag(boolean flag) {
		this.flag = flag;
	}
	ServerSocket server;
	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
			int i=0;
			System.out.println("Server created!"+port);
			if (!flag) {
				server.close();
				System.out.println("Server is closed!"+port);
			}
			while(flag) {
				Socket client = server.accept();
				SeverThread st = new SeverThread(client);
				i++;
				st.setID(i);
				st.start();
				System.out.println("Incoming client"+client.getRemoteSocketAddress());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
