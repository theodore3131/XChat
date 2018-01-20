package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

public class NetClient extends Thread {
	String severIP;
	int port;
	OutputStream outs;
	DataOutputStream dataOutput;
	DataInputStream dataInput;
	BufferedReader bReader;
	JTextArea jArea_receive;
	public NetClient(String severIP, int port, JTextArea jArea) {
		// TODO Auto-generated constructor stub
		this.severIP = severIP;
		this.port = port;
		this.jArea_receive = jArea;
	}
	
	@SuppressWarnings("resource")
	public boolean Conn2Sever() {
		try {
			Socket client = new Socket(severIP, port);
			InputStream in = client.getInputStream();
			bReader = new BufferedReader(new InputStreamReader(in));
			outs = client.getOutputStream();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean loginServer(String name, String pwd) {
		try {
			String input = bReader.readLine();
			System.out.println("Server said: "+input);
			
			name += "\r\n";
			outs.write(name.getBytes());
			outs.flush();
			System.out.println("Cleint sent the msg, waiting for the response");
			
			input = bReader.readLine();
			System.out.println("Server said: "+input);
			pwd+="\r\n";
			outs.write(pwd.getBytes());
			outs.flush();
		
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return false;
	}
	
	public void readFromServer() throws IOException {
		String input;
		try {
			input = bReader.readLine();
			System.out.println("Server said: "+input);
			jArea_receive.append(input+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			bReader.close();
			e.printStackTrace();
		}
	}
	
	public void sendMsg(Object msg) {
		try {
			int type = 0;
			String string = "";
	
			if (msg.getClass()==string.getClass()) {
				//String
				type = 0;
				int len = msg.toString().length();
				msg = String.valueOf(type) + len + msg;
				this.outs.write(msg.toString().getBytes());
			}
			
			else {
				//File
				type = 1;
				int len = msg.toString().length();
				msg = String.valueOf(type) + len + msg;
				this.outs.write(msg.toString().getBytes());
			}
			
			this.outs.flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void run() {
		boolean flag = true;
		while(flag) {
			try {
				readFromServer();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				flag = false;
				e.printStackTrace();
			}
		}
	}
}
