package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JTextArea;

public class Listener extends KeyAdapter implements ActionListener{
	JTextArea jt_output;
	NetClient conn;
	public Listener(JTextArea output, NetClient conn) {
		// TODO Auto-generated constructor stub
		this.jt_output = output;
		this.conn = conn;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("send")) {
//			String msg = jt_output.getText();
			File msg = new File(jt_output.getText());
			conn.sendMsg(msg);
			jt_output.setText("");
		}
	}
	
//	push down "enter" on keyboard to send message
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
			
//			ignore the /r/n when you push down enter to send. 
			
			String msg = jt_output.getText().trim();
			conn.sendMsg(msg);
			jt_output.setText("");
		}
	}
	
}
