package com.sun.dao;
/**
* @author 作者:Chaoguo Sun
* @createDate 创建时间：2018年8月7日 上午10:23:14
*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.sun.pojo.Dept;
import com.sun.pojo.Employee;
import com.sun.pojo.Grade;
import com.sun.pojo.Project;
import com.sun.pojo.Score;
import com.sun.utils.DBUtils;

public class ScoreDao1 {

	

	public List<Score> selectAll() {
		List<Score> listCore = new ArrayList<>();
		
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			String sql = "select * from v_e_d_p";
			ps = conn.prepareStatement(sql);// 预处理
			rs = ps.executeQuery();// 结果集
			while (rs.next()) {// 处理结果集
				Score score = new Score();
				score.setId(rs.getInt("s_id"));
//				score.setE_id(rs.getInt("e_id"));
//				score.setE_id(rs.getInt("p_id"));
				score.setValue((Integer)rs.getObject("value"));
				score.setGrade(rs.getString("grade"));
				Dept dept = new Dept();
				dept.setId(rs.getInt("d_id"));
				dept.setName(rs.getString("d_name"));
				Employee emp = new Employee();
				emp.setId(rs.getInt("eId"));
				emp.setName(rs.getString("e_name"));
				emp.setDept(dept);
				
				score.setEmployee(emp);
				
				Project project = new Project();
				project.setId(rs.getInt("p_id"));
				project.setName(rs.getString("p_name"));
				score.setProject(project);
				listCore.add(score);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, null, rs);
		}

		return listCore;

	}


	

	public void save(Set<Score> set) {
		for (Score score : set) {
			if (score.getId()==0) {
				add(score);
			}
			else {
				update(score);
			}
		
		
		
		}
		
		
	}
	public boolean add(Score score) {
		int rs =0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			String sql = "insert into score (e_id,p_id,value) values(?,?,?)";
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, score.getEmployee().getId());
			ps.setInt(2, score.getProject().getId());
			ps.setInt(3, score.getValue());
			rs = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 预处理
		finally {
			DBUtils.realse(conn, ps, null);
		}
		return rs> 0;
		
		
	}
	public boolean update(Score score) {
		int rs =0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			String sql = "update score set value=? where id = ?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, score.getValue());
			ps.setInt(2, score.getId());
			
			rs = ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 预处理
		finally {
			DBUtils.realse(conn, ps, null);
		}
		return rs> 0;
	}
	public List<Score> selectByCondition(Score conditon) {
		List<Score> listScore = new ArrayList<>();
		Connection conn = null;
		Statement ps = null;
		ResultSet rs = null;

		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			ps = conn.createStatement();
			String where = "where 1=1";
			if (!conditon.getEmployee().getName().equals("")) {
				where += " and e_name='" + conditon.getEmployee().getName() + "'";

			}
			if (conditon.getEmployee().getDept().getId()!=-1) {
				where += " and d_id='" + conditon.getEmployee().getDept().getId() + "'";

			}
			if (conditon.getProject().getId()!=-1) {
				where += " and p_id='" + conditon.getProject().getId() + "'";

			}
			if (conditon.getValue() != -1) {
				where += " and value='" + conditon.getValue() + "'";

			}
			if (!conditon.getGrade().equals("-1")) {
				where += " and grade='" + conditon.getGrade() + "'";

			}
			String sql = "select * from v_e_d_p "
					+ where;
			rs = ps.executeQuery(sql);// 结果集
			while (rs.next()) {// 处理结果集
				Score score = new Score();
				score.setId(rs.getInt("s_id"));
	
				Employee emp = new Employee();
				emp.setId(rs.getInt("eId"));
				emp.setName(rs.getString("e_name"));
				
				score.setEmployee(emp);
				Dept dept = new Dept();
				dept.setId(rs.getInt("d_id"));
				dept.setName(rs.getString("d_name"));
				emp.setDept(dept);
				
				Project project = new Project();
				project.setId(rs.getInt("p_id"));
				project.setName(rs.getString("p_name"));
				score.setValue((Integer)rs.getObject("value"));
				score.setGrade(rs.getString("grade"));
				score.setProject(project);
				listScore.add(score);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, null, rs);
		}

		return listScore;

	}
	public List<Grade> selectAllGrade(){
		List<Grade>listGrade = new ArrayList<>();
		
		
		
		return listGrade;
	}


}

	
