package com.sun.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
* @author 作者:Chaoguo Sun
* @createDate 创建时间：2018年8月7日 上午10:23:51
*/
public class DBUtils {
	

	public static Connection getConn() {
		Connection conn=null;
		
			try {
				// 加载驱动
				Class.forName("com.mysql.jdbc.Driver");
				// 获取连接对象
				conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test1", "root", "123456");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return conn;
	}

	public static void realse(Connection conn,Statement stat, ResultSet rs) {
		if (conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (stat!=null) {
				try {
					stat.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
	}
}
