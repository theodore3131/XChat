package server;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class UserListThread extends Thread {
    JList jList;
    private List<UserInfo> userList;
    public UserListThread (JList jList) {
        this.jList = jList;
    }

    @Override
    public void run() {
        updateList();
    }

    public void updateList() {
        userList = DaoTools.getUsers();
        jList = new JList(userList.toArray());
        jList.updateUI();
        System.out.println(userList.size());
    }
}
