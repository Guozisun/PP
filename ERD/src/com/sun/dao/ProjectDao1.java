package com.sun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.pojo.Project;
import com.sun.utils.DBUtils;

/**
 * @author 作者:Chaoguo Sun
 * @createDate 创建时间：2018年8月8日 下午2:41:57
 */
public class ProjectDao1 {

	public List<Project> selectAll(int dId) {
		List<Project> listProject = new ArrayList<>();
		Connection conn = null;
		Statement ps = null;
		ResultSet rs = null;

		try {
			conn = DBUtils.getConn();
			ps = conn.createStatement();
			String sql = "select p_id,p_name from v_p_d where d_id=" + dId;

			rs = ps.executeQuery(sql);
			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("p_id"));

				project.setName(rs.getString("p_name"));
				listProject.add(project);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, ps, rs);
		}
		return listProject;

	}

	public List<Project> selectNotIn(int dId) {
		List<Project> listProject1 = new ArrayList<>();
		Connection conn = null;
		Statement ps = null;
		ResultSet rs = null;

		try {
			conn = DBUtils.getConn();
			ps = conn.createStatement();
			String sql = "select * from project where id not in(select p_id from v_p_d where d_id=" + dId + ")";

			rs = ps.executeQuery(sql);
			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
				listProject1.add(project);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, ps, rs);
		}
		return listProject1;

	}

	public boolean addProject(int dId, int pId) {

		Connection conn = null;
		Statement ps = null;
		int row = 0;
		try {
			// 连接数据库
			conn = DBUtils.getConn();
			ps = conn.createStatement();
			String sql = "insert into r_p_d(d_id,p_id)values (" + dId + "," + pId + ")";
			row = ps.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 预处理
		finally {
			DBUtils.realse(conn, ps, null);
		}
		return row > 0;
	}
	public boolean deleteProject(int dId, int pId) {

		Connection conn = null;
		Statement ps = null;
		int row = 0;
		try {
			// 连接数据库
			conn = DBUtils.getConn();
			ps = conn.createStatement();
			String sql = "delete from r_p_d where d_id ="+dId+" and p_id="+pId+" ";
			row = ps.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 预处理
		finally {
			DBUtils.realse(conn, ps, null);
		}
		return row > 0;
	}

}
