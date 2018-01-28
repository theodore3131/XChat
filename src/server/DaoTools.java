package server;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DaoTools {
	/**
	* 查看这个用户是否可以登录成功!
	* @param user :要检查的用户对象 * @return:是否登录成功
	*/
	// JDBC 驱动名及数据库 URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?characterEncoding=utf8&useSSL=false";
	// 数据库的用户名与密码，需要根据自己的设置
	static final String USER = "root";
	static final String PASS = "XZW315";
	
	public static boolean checkLogin(UserInfo user) {
		Connection conn = null;
		int retValue = 0;
		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("连接数据库");
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			String sql = "select count(*) from XChat where Name=? and Password=? ";
			PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1,user.getname());
			pst.setString(2,user.getpwd());
			ResultSet res = pst.executeQuery();
			while (res.next()){
				retValue = res.getInt(1);
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		if (retValue>0){
		    String sql = "update Xchat set state=1 where Name=? and Password=?";
            try {
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1,user.getname());
                pst.setString(2,user.getpwd());
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
		}
		else
			return false;
	}
	public static List<UserInfo> getUsers() {
	    List<UserInfo> userList = new LinkedList<>();
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			String sql = "select * from XChat";
			PreparedStatement pst = conn.prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			while (res.next()) {
			    UserInfo user = new UserInfo();
			    user.setname(res.getString(1));
			    user.setpwd(res.getString(2));
			    userList.add(user);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
}
