package com.sun.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
* @author ����:Chaoguo Sun
* @createDate ����ʱ�䣺2018��8��7�� ����10:23:51
*/
public class DBUtils {
	

	public static Connection getConn() {
		Connection conn=null;
		
			try {
				// ��������
				Class.forName("com.mysql.jdbc.Driver");
				// ��ȡ���Ӷ���
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
