package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;

public class UIListener implements ActionListener {
	List<UserInfo> userList;
	JList jList;
    ChatServer chatServer;
    UserListThread userListThread;
	public UIListener(JList jList) {
		this.jList = jList;
        chatServer = new ChatServer(9090);
        userListThread = new UserListThread(jList);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("开启服务器")) {
			((JButton)e.getSource()).setText("重启服务器");
			chatServer.setflag(true);
			chatServer.start();
			userListThread.start();
		}
		else if (e.getActionCommand().equals("关闭服务器")) {
			chatServer.setflag(false);
			System.out.println("服务器休息一下");
		}
		else if (e.getActionCommand().equals("重启服务器")) {
			chatServer.setflag(true);
			System.out.println("服务器又回来啦");
		}
	}
	
}
