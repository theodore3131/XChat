package server;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SeverUI {
	
	ArrayList<SeverThread> stList;
	List<UserInfo> userList = new LinkedList<>();
	JFrame jFrame = new JFrame("Server");
	
	public SeverUI(ArrayList<SeverThread> userList) {
		// Transport the stlist to this UI to create an JList of users
		this.stList = userList;
	}
	
	public void drawUI() {
		jFrame.setSize(600, 600);
		jFrame.setLayout(new FlowLayout());
		JPanel jPanel1 = new JPanel(new FlowLayout());
		
		Dimension dimension1 = new Dimension(600, 100);
		Dimension dimension2 = new Dimension(600, 500);
		
		jPanel1.setPreferredSize(dimension1);
		
		JButton button1 =  new JButton("开启服务器");
		JButton button2 = new JButton("关闭服务器");
		jPanel1.add(button1);
		jPanel1.add(button2);
		
		JPanel jPanel2 = new JPanel();
		
		jPanel2.setPreferredSize(dimension2);
		  
        JScrollPane scrollPane = new JScrollPane();  
        scrollPane.setPreferredSize(new Dimension(300, 200)); 
        
//        将arraylist转为对象数组，以数组构造方法创建
        final JList jList = new JList(userList.toArray());

        jList.addListSelectionListener(new ListSelectionListener(){
            @Override  
            public void valueChanged(ListSelectionEvent e) {  
                if(!jList.getValueIsAdjusting()){   //设置只有释放鼠标时才触发  
                    System.out.println(jList.getSelectedValue());  
                }  
            }
        });

	    scrollPane.setViewportView(jList);
	    jPanel2.add(scrollPane);

		UIListener l = new UIListener(jList);
		
		jFrame.add(jPanel1);
		jFrame.add(jPanel2);
		
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(3);

		button1.addActionListener(l);
		button2.addActionListener(l);
	}
	
	public void refresh() {
		jFrame.repaint();
	}

}
