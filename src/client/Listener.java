package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JTextArea;

public class Listener extends KeyAdapter implements ActionListener{
	JTextArea jt_output;
	NetClient conn;
	public Listener(JTextArea output, NetClient conn) {
		this.jt_output = output;
		this.conn = conn;
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("send")) {
			String msg = jt_output.getText();

			conn.sendMsg(msg,1);
			jt_output.setText("");
		}
		ArrayList<Byte> arrayList = new ArrayList<>();

	}
	
//	push down "enter" on keyboard to send message
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_ENTER) {
			
//			ignore the /r/n when you push down enter to send.
			String msg = jt_output.getText().trim();

			conn.sendMsg(msg,1);
			jt_output.setText("");
		}
	}
	
}
