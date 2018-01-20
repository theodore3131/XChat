package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;

import server.ChatServer;

public class UIListener implements ActionListener {
	public UIListener() {
		
	}
	ChatServer chatServer = new ChatServer(9090);
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("开启服务器")) {
			((JButton)e.getSource()).setText("重启服务器");
//			set flag to setup server to avoid the GUI of being blocked.
			chatServer.setflag(true);
			chatServer.start();
		}
		else if (e.getActionCommand().equals("关闭服务器")) {
//			chatServer.setflag(false);
			chatServer.setflag(false);
//			chatServer.interrupt();
			System.out.println("服务器休息一下");
		}
		else if (e.getActionCommand().equals("重启服务器")) {
			chatServer.setflag(true);
//			chatServer.interrupted();
			System.out.println("服务器又回来啦");
		}
	}
	
}
