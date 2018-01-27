package server;

import java.io.*;
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
			e.printStackTrace();
		}
		//处理方法执行完毕后，线程自已即退出...
	}
	public boolean login(Socket client) throws Exception {
		out = client.getOutputStream();
		InputStream in = client.getInputStream();
		BufferedReader bReader = new BufferedReader(new InputStreamReader(in));

		String userName = bReader.readLine();
		String pwd = bReader.readLine();

		user = new UserInfo();
		user.setname(userName);
		user.setpwd(pwd);

		//check if the user exist in the database
		boolean loginState = DaoTools.checkLogin(user);
		return loginState;
	}
	public void processChat(Socket client) throws Exception {
		try {
			InputStream in = client.getInputStream();
			BufferedReader bReader = new BufferedReader(new InputStreamReader(in));

			boolean loginState = login(client);

			DataInputStream dins = new DataInputStream(in);

			if (loginState) {
				ChatTools.addClient(this);

//				String input = bReader.readLine();
//				while (!input.equals("bye")) {
//					System.out.println("用户"+this.getID()+"说："+input);
//					ChatTools.castMsg(this.user, input);
//					input = bReader.readLine();
//				}
			}
			else {
				sendMsg2Me("name or password error!\r\n");
				client.close();
			}
			while (true) {
				int totallen = dins.readInt();

				byte flag = dins.readByte();

				int destNum = dins.readInt();

				if (flag==1) {
					System.out.println("消息类型是文本");
					byte[] data = new byte[totallen-4-1-4];
					dins.readFully(data);
					String msg = new String(data);
					System.out.println("发送的消息是"+msg);
				}
				else if (flag==2) {
					System.out.println("发送文件给"+destNum);
					byte [] data = new byte[256];
					dins.readFully(data);
					String fileName = new String(data).trim();
					System.out.println("文件名是："+fileName);
					data = new byte[totallen-4-1-4-256];
					dins.readFully(data);
					FileOutputStream fos = new FileOutputStream(fileName);
					fos.write(data);
					fos.flush();
					fos.close();
					System.out.println("文件保存完成");
				}
				else {
					System.out.println("收到未知数据包" + flag);
					ChatTools.removeClient(this);
					client.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			ChatTools.removeClient(this);
			client.close();
		}
		
	}
}
