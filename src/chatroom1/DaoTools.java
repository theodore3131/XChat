package chatroom1;

import java.util.HashMap;
import java.util.Map;

public class DaoTools {
	/**
	* 查看这个用户是否可以登录成功!
	* @param user :要检查的用户对象 * @return:是否登录成功
	*/
	
	private static Map<String, UserInfo> userDB = new HashMap<String, UserInfo>();
	static {
		for (int i = 0; i < 10; i++) {
			UserInfo user = new UserInfo();
			user.setname("user"+i);
			user.setpwd("pwd"+i);
			userDB.put(user.getname(), user);
		}
		System.out.println(userDB.get("user1"));
	}
	
	public static boolean checkLogin(UserInfo user) {
		if (userDB.containsKey(user.getname())) {
			return true;
		}
		System.out.println("Get out of here!"+user.getname());
		return false;
	}
}
