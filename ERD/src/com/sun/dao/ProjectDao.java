package com.sun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.pojo.Project;
import com.sun.pojo.Dept;
import com.sun.pojo.Employee;
import com.sun.utils.DBUtils;

/**
 * @author 作者:Chaoguo Sun
 * @createDate 创建时间：2018年8月7日 上午10:23:14
 */
public class ProjectDao {

	

	public List<Project> selectAll() {
		List<Project> listProject = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DBUtils.getConn();
			String sql = "select * from project";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
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

	public boolean addProject(Project project) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			String sql = "insert into project(name) values(?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, project.getName());

			int row = ps.executeUpdate();
			if (row > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 预处理
		finally {
			DBUtils.realse(conn, ps, null);
		}
		return flag;
	}

	public boolean updateProject(Project project) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			String sql = "update project set name=?where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, project.getName());

			

			ps.setInt(2, project.getId());
			int row = ps.executeUpdate();
			if (row > 0) {
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 预处理
		finally {
			DBUtils.realse(conn, ps, null);
		}
		return flag;
	}

	public boolean deleteProjects(String ids) {
		boolean flag = false;
		Connection conn = null;
		Statement ps = null;

		try {
			conn = DBUtils.getConn();
			ps = conn.createStatement();
			String sql = "delete from project where id in(" + ids + ")";
			ps.executeUpdate(sql);
			ps.close();
			sql="delete from r_p_d where p_id in(" + ids + ")";
			ps.executeUpdate(sql);
			flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, ps, null);
		}

		return flag;
	}

	public List<Project> selectByCondition(Project conditon) {
		List<Project> listProject = new ArrayList<>();
		Connection conn = null;
		Statement ps = null;
		ResultSet rs = null;

		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			ps = conn.createStatement();
			String where = "where 1=1";
			if (!conditon.getName().equals("")) {
				where += " and name='" + conditon.getName() + "'";

			}
			

			String sql = "select * from project " + where;
			rs = ps.executeQuery(sql);// 结果集
			while (rs.next()) {// 处理结果集

				Project project = new Project();
				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
			
				listProject.add(project);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, null, rs);
		}

		return listProject;

	}
	public Project selectOne(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Project project = new  Project();
		try {
			conn = DBUtils.getConn();
			String sql = "select * from project where id =?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {

				project.setId(rs.getInt("id"));
				project.setName(rs.getString("name"));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, ps, rs);
		}
		return project;

	}

}
