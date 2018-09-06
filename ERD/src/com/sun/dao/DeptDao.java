package com.sun.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sun.pojo.Dept;
import com.sun.pojo.Employee;
import com.sun.utils.DBUtils;

/**
 * @author 作者:Chaoguo Sun
 * @createDate 创建时间：2018年8月7日 上午10:23:14
 */
public class DeptDao {



	public List<Dept> selectAll() {
		List<Dept> listDept = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = DBUtils.getConn();
			String sql = "select * from dept1";
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				Dept dept = new Dept();
				dept.setId(rs.getInt("id"));
				dept.setName(rs.getString("name"));
				dept.setE_count(rs.getInt("emp_count"));
				listDept.add(dept);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, ps, rs);
		}
		return listDept;

	}

	public boolean addDept(Dept dept) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			String sql = "insert into dept1(name,emp_count) values(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dept.getName());
			ps.setInt(2, dept.getE_count());

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

	public boolean updateDept(Dept dept) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			String sql = "update dept1 set name=? where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dept.getName());
			ps.setInt(2, dept.getId());
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

	public boolean deleteDepts(String ids) {
		boolean flag = false;
		Connection conn = null;
		Statement ps = null;

		try {
			conn = DBUtils.getConn();
			conn.setAutoCommit(false);
			ps = conn.createStatement();
			String sql = "delete from dept1 where id in(" + ids + ")";
			ps.executeUpdate(sql);
			ps.close();
			ps = conn.createStatement();
			sql = "update empl1 set d_id = null where d_id in(" + ids + ")";
			ps.executeUpdate(sql);
			ps.close();
			ps = conn.createStatement();
			sql = "delete from r_p_d where d_id in(" + ids + ")";
			ps.executeUpdate(sql);
			conn.commit();
			conn.setAutoCommit(true);
				flag = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, ps, null);
		}

		return flag;
	}

	public List<Dept> selectByCondition(Dept conditon) {
		List<Dept> listDept = new ArrayList<>();
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
			if (conditon.getE_count() != -1) {
				where += " and emp_count='" + conditon.getE_count() + "'";

			}

			String sql = "select * from dept1 " + where;
			rs = ps.executeQuery(sql);// 结果集
			while (rs.next()) {// 处理结果集

				Dept dept = new Dept();
				dept.setId(rs.getInt("id"));
				dept.setName(rs.getString("name"));
				dept.setE_count(rs.getInt("emp_count"));
				listDept.add(dept);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, null, rs);
		}

		return listDept;

	}
	public Dept selectOne(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Dept dept = new Dept();
		try {
			conn = DBUtils.getConn();
			String sql = "select * from dept1 where id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {

				
				dept.setId(rs.getInt("id"));
				dept.setName(rs.getString("name"));
				dept.setE_count(rs.getInt("emp_count"));
				
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, ps, rs);
		}
		return dept;

	}

}
