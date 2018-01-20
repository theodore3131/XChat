package server;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class SeverUI {
	
	ArrayList<SeverThread> stList;
	ArrayList<String> data = new ArrayList<>();
	JFrame jFrame = new JFrame("Server");
	
	public SeverUI(ArrayList<SeverThread> userList) {
		// TODO Auto-generated constructor stub
		// Transport the stlist to this UI to create an JList of users
		this.stList = userList;
		for (int i = 0; i < stList.size(); i++) {
//			get the users name and put it into the list.
			data.add(userList.get(i).getName());
		}
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
        
        //将arraylist转为对象数组，以数组构造方法创建  
        final JList jList = new JList(data.toArray());
        
//        String[] str = {"aa","bb","cc","dd","ee","ff","gg","aa","bb","cc","dd","ee","ff","gg"};
//        final JList jList = new JList(str);
        
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
		
		jFrame.add(jPanel1);
		jFrame.add(jPanel2);
		
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);
		jFrame.setDefaultCloseOperation(3);
		
		UIListener l = new UIListener();
		button1.addActionListener(l);
		button2.addActionListener(l);
	}
	
	public void refresh() {
		jFrame.repaint();
	}

}
