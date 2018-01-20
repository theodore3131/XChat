package chatroom1;

import java.util.ArrayList;

public class ChatTools {
	
	private static ArrayList<SeverThread> stList =  new ArrayList<SeverThread>();
	private ChatTools() {}
	
	public static void addClient(SeverThread st) {
		try {
			castMsg(st.getOwerUser(), "I'm online now!Users currently are:"+stList.size()+"\r\n");
			stList.add(st);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void removeClient(SeverThread st) {
		try {
			castMsg(st.getOwerUser(), "I'm offline now!Users currently are:"+stList.size()+"\r\n");
			stList.remove(st);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void castMsg(UserInfo user, String msg) {
		msg = user.getname()+"says:"+msg;
//		System.out.println(stList.size());
		for (int i = 0; i < stList.size(); i++) {
			SeverThread st = stList.get(i);
			try {
				st.sendMsg2Me(msg+"\r\n");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
