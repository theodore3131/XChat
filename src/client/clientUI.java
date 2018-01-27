package client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;

import server.ChatServer;

public class clientUI {
	JFrame jf_login;
	JFrame jf_chat;
	JTextField jt_name;
	JTextField jt_pwd;
	JTextArea jArea_input = new JTextArea(10, 40);
	JFileChooser jChooser = new JFileChooser();
	NetClient conn;
	public clientUI() {

	}
	public void showloginUI() {
		jf_login = new JFrame("登录");
		jf_login.setSize(300, 400);
		jf_login.setLocationRelativeTo(null);
		jf_login.getContentPane().setBackground(Color.white);
		
		ImageIcon img = new ImageIcon("images/a.png");
		JLabel jl = new JLabel(img);
		Dimension d=new Dimension(300,250);
		jl.setPreferredSize(d);
		
		JPanel jp=new JPanel();
		jp.setLayout(new FlowLayout());
		Dimension d1=new Dimension(100, 100);
		
		JLabel la1 = new JLabel("用户名");
		jt_name = new JTextField("name",20);

		JLabel la2 = new JLabel("密码 :");
		jt_pwd = new JPasswordField(20);

		JButton log=new JButton("登录");
		log.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				loginAction();
			}
		});
		
		jp.add(jl);
		jp.add(la1);
		jp.add(jt_name);
		jp.add(la2);
		jp.add(jt_pwd);
		jp.add(log);
		
		jf_login.add(jp);
		jf_login.setDefaultCloseOperation(3);
		jf_login.setVisible(true);
	}
	public void loginAction() {
		String name = jt_name.getText();
		String pwd = jt_pwd.getText();
		conn = new NetClient("localhost", 9090, jArea_input);
		
		if (conn.Conn2Sever()) {
			boolean res = conn.loginServer(name,pwd);
			if (res) {
				showMainUI();
				conn.start();
				jf_login.dispose();
			}
			else {
			    jt_name.setText("");
			    jt_pwd.setText("");
				jf_login.setTitle("错误❌");
			}
		}
	}
	
	public void showMainUI() {
		jf_chat = new JFrame("Welcome");
		jf_chat.setSize(600, 600);
		jf_chat.setLayout(new FlowLayout());
		
//		JLabel label = new JLabel("message you sent");
		JTextArea jt_input = new JTextArea(10, 30);
		JButton bu_send = new JButton("send");
		bu_send.setPreferredSize(new Dimension(60, 60));
		Listener lis = new Listener(jt_input, conn);
		
		bu_send.addActionListener(lis);
		jt_input.addKeyListener(lis);
		
		JButton filebutton = new JButton("选择文件");
		filebutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				jChooser.showDialog(new JLabel(), "选择");
				File file = jChooser.getSelectedFile();
				if (file.exists()) {
					conn.sendFile(file, 1);
				}
			}
		});
		jf_chat.add(jArea_input);
	
		jf_chat.add(jt_input);
		jf_chat.add(bu_send);
		jf_chat.add(filebutton);
		jf_chat.setVisible(true);
		jf_chat.setDefaultCloseOperation(3);
	}
	public static void main(String[] args) {
		clientUI clientUI = new clientUI();
		clientUI.showloginUI();
	}
}

