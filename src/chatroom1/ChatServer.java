package chatroom1;

import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
	public void setUpServer(int port) {
		try {
			@SuppressWarnings("resource")
			ServerSocket server = new ServerSocket(port);
			System.out.println("服务器创建成功！"+port);
			int i=0;
			while (true) {
				Socket client = server.accept();
				SeverThread st = new SeverThread(client);
				i++;
				st.setID(i);
				st.start();
				System.out.println("Incoming client"+client.getRemoteSocketAddress());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		ChatServer chatServer = new ChatServer();
		chatServer.setUpServer(9090);
	}

	
}
