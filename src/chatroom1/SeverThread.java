package chatroom1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class SeverThread extends Thread {
	private Socket client;
	private OutputStream out;
	private UserInfo user;
	private int id = 0;
	public SeverThread(Socket socket) {
		this.client = socket;
	}
	public void setID(int i) {
		this.id = i;
	}
	public int getID() {
		return id;
	}
	public UserInfo getOwerUser(){
		return this.user; 
	}
	//将发送消息的代码包装到一个方法中 
	public void sendMsg2Me(String msg) throws Exception{
		byte[] data = msg.getBytes(); 
		out.write(data); //用输出对象发送! 
		out.flush();//强制输出
	}
	public void run(){ 
		//在线程run中调用处理连接的方法 
		try {
			processChat(this.client);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//处理方法执行完毕后，线程自已即退出...
	}
	public void processChat(Socket client) throws Exception {
		try {
			out = client.getOutputStream();
			InputStream in = client.getInputStream();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(in));
	
			String str = "你好，欢迎来到西奥多先生的房间\r\n";
			this.sendMsg2Me(str);
			
			sendMsg2Me("你好，请输入用户名\r\n");
			String userName = bReader.readLine();
			sendMsg2Me(userName+",please input your password:\r\n");
			String pwd = bReader.readLine();
			
			user = new UserInfo();
			user.setname(userName);
			user.setpwd(pwd);
			
			System.out.println(user.getname());
			System.out.println(user.getpwd());
			
//			check if the user exist in the database
			boolean loginState = DaoTools.checkLogin(user);
			if (!loginState) {
				sendMsg2Me("you have not registered!\r\n");
				client.close();
				return;
			}
			
//			important!!!
			ChatTools.addClient(this);
			sendMsg2Me("hello!"+userName+"\r\n");
			
			String input = bReader.readLine();
			while (!input.equals("bye")) {
				System.out.println("用户"+this.getID()+"说："+input);
				ChatTools.castMsg(this.user, input);
				input = bReader.readLine();
			}
			
			ChatTools.castMsg(this.user, "I am offline,see ya!\r\n");
			ChatTools.removeClient(this);
			
			client.close();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
