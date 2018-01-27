package client;

import server.DaoTools;
import server.UserInfo;

import java.io.*;
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
    boolean flag = true;

	public NetClient(String severIP, int port, JTextArea jArea) {
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
			dataOutput = new DataOutputStream(outs);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean loginServer(String name, String pwd) {
		try {
            UserInfo user = new UserInfo();
            user.setname(name);
            user.setpwd(pwd);

			name += "\r\n";
			outs.write(name.getBytes());
			outs.flush();

			pwd += "\r\n";
			outs.write(pwd.getBytes());
			outs.flush();

            //check if the user exist in the database
            boolean loginState = DaoTools.checkLogin(user);
            return loginState;
		} catch (Exception e) {
            return false;
		}
	}
	
	public void readFromServer() throws IOException {
		String input;
		if (flag) {
            input = bReader.readLine();
            System.out.println("Server said: " + input);
            jArea_receive.append(input + "\r\n");
        }
	}
	private void writeString(DataOutputStream out,String str,int len){
		byte[] data = str.getBytes();
		try {
			out.write(data);
			out.writeByte('\0');//补二进制0
		} catch (IOException e) {
			e.printStackTrace();
		}
		len--;
	}
	public void sendMsg(String msg, int destNum) {
		try {

		    byte[] str = msg.getBytes();
		    int totallen = 4+1+4+str.length;
            System.out.println( "发送总字节数为 "+totallen);

            dataOutput.writeInt(totallen);
            dataOutput.writeByte(1);
			dataOutput.writeInt(destNum);
			dataOutput.write(str);
			dataOutput.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendFile(File file, int destNum) {
	    try {

	        InputStream ins = new FileInputStream(file);
	        int fileDataLen = ins.available();
	        int totallen = 4+1+4+256+fileDataLen;

	        dataOutput.writeInt(totallen);
	        dataOutput.writeByte(2);
	        dataOutput.writeInt(destNum);

	        String shorFileName = file.getName();
	        writeString(dataOutput,shorFileName,256);
	        byte[] filedata = new byte[fileDataLen];
	        ins.read(filedata);

	        dataOutput.write(filedata);
	        dataOutput.flush();

        }catch (Exception e){
	        e.printStackTrace();
        }
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void run() {
		while(flag) {
			try {
				readFromServer();
			} catch (IOException e) {
				flag = false;
				e.printStackTrace();
			}
		}
	}
}
