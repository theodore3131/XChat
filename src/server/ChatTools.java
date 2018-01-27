package server;

import java.util.ArrayList;

public class ChatTools {
	private static ArrayList<SeverThread> stList =  new ArrayList<SeverThread>();
	private ChatTools() {}
	static SeverUI ui = new SeverUI(stList);
	public static void addClient(SeverThread st) {

        castMsg(st.getOwerUser(), "I'm online now!Users currently are:"+stList.size()+"\r\n");
        stList.add(st);
        ui.refresh();

	}
	public static void removeClient(SeverThread st) {
	    castMsg(st.getOwerUser(),"I'm going to be offline, see ya!"+stList.size()+"\r\n");
	    stList.remove(st);
	    ui.refresh();
    }
	public static void castMsg(UserInfo user, String msg) {
		msg = user.getname()+" : "+msg;

		for (int i = 0; i < stList.size(); i++) {
			SeverThread st = stList.get(i);
			try {
				st.sendMsg2Me(msg+"\r\n");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		ui.drawUI();
	}
}
