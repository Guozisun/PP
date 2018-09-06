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
import java.util.List;

import javax.swing.JOptionPane;

import com.sun.pojo.Dept;
import com.sun.pojo.Employee;
import com.sun.utils.DBUtils;

public class EmployeeDao {


//联合查询 查询出来所有
	/*
	 * 问题 将List<Employee> list = new ArrayList<>();共享的话
	 * 不管添加还是刷新都不会清空前面的数据 
	 */
//	List<Employee> list = new ArrayList<>();
	public List<Employee> selectAll() {
		List<Employee> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			String sql = "select e.*,d.name as dName,emp_count from empl1 as e left join dept1 as d on d.id=e.d_id";
			ps = conn.prepareStatement(sql);// 预处理
			rs = ps.executeQuery();// 结果集
			while (rs.next()) {// 处理结果集
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));

				Dept dept = new Dept();
				dept.setId(rs.getInt("d_id"));
				dept.setName(rs.getString("dName"));
				dept.setE_count(rs.getInt("emp_count"));
				emp.setDept(dept);
				list.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, null, rs);
		}

		return list;

	}

	public List<Employee> selectByCondition(Employee conditon) {
		List<Employee> list = new ArrayList<>();
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
				where += " and e.name='" + conditon.getName() + "'";

			}
			if (!conditon.getSex().equals("")) {
				where += " and e.sex='" + conditon.getSex() + "'";

			}
			if (conditon.getAge() != -1) {
				where += " and e.age='" + conditon.getAge() + "'";

			}
			if (conditon.getDept().getId() != -1) {
				where += " and e.d_id='" + conditon.getDept().getId() + "'";

			}
			String sql = "select e.*,d.name as dName,emp_count from empl1 as e left join dept1 as d on d.id=e.d_id "
					+ where;
			rs = ps.executeQuery(sql);// 结果集
			while (rs.next()) {// 处理结果集
				Employee emp = new Employee();
				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));

				Dept dept = new Dept();
				dept.setId(rs.getInt("d_id"));
				dept.setName(rs.getString("dName"));
				dept.setE_count(rs.getInt("emp_count"));
				emp.setDept(dept);
				list.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, null, rs);
		}

		return list;

	}

	public boolean addEmployees(Employee emp) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		
			// 连接数据库
		conn = DBUtils.getConn();
			// 查询语句
		String sql = "insert into empl1(name,sex,age,d_id) values(?,?,?,?)";
		try {
				    String sql1 = "select * from empl1 where name =?";
				    ps= conn.prepareStatement(sql1);
				    ps.setString(1, emp.getName());
				    
				    ResultSet rs= ps.executeQuery();
				   
				    boolean boo = rs.next();
			if (!boo) {
			ps.close();
			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getName());
			ps.setString(2, emp.getSex());
			ps.setInt(3, emp.getAge());
			ps.setInt(4, emp.getD_id());
			ps.executeUpdate();
			
			JOptionPane.showMessageDialog(null,"数据增加成功");
			  }else{
			    JOptionPane.showMessageDialog(null,"姓名已存在,请重新输入");
			    flag=true;
			   }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"有相同的姓名！");
			
		}
		finally {
			DBUtils.realse(conn, ps,null );
		}
		
		return flag;
	}// 预处理

	public boolean updateEmployees(Employee emp) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// 连接数据库
			conn = DBUtils.getConn();
			// 查询语句
			String sql = "update empl1 set name=?,sex=?,age=?, d_id=? where id=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, emp.getName());
			ps.setString(2, emp.getSex());
			ps.setInt(3, emp.getAge());
			ps.setInt(4, emp.getD_id());
			ps.setInt(5, emp.getId());
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

	public Employee selectOne(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Employee emp = new Employee();
		try {
			conn = DBUtils.getConn();
			String sql = "select e.*,d.name as d_name from empl1 as e left join dept1 as d on e.d_id = d.id where e.id=?";

			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {

				emp.setId(rs.getInt("id"));
				emp.setName(rs.getString("name"));
				emp.setSex(rs.getString("sex"));
				emp.setAge(rs.getInt("age"));
				Dept dept = new Dept();
				dept.setId(rs.getInt("e.d_id"));
				dept.setName(rs.getString("d_name"));
				emp.setDept(dept);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, ps, rs);
		}
		return emp;

	}

	public boolean deleteEmployee(int id) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;

		try {
			conn = DBUtils.getConn();
			String sql = "delete from empl1 where id =?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			int row = ps.executeUpdate();
			if (row > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, ps, null);
		}

		return flag;
	}

	public boolean deleteEmployee1(String ids) {
		boolean flag = false;
		Connection conn = null;
		Statement ps = null;

		try {
			conn = DBUtils.getConn();

			ps = conn.createStatement();
			String sql = "delete from empl1 where id in(" + ids + ")";
			int row = ps.executeUpdate(sql);
			if (row > 0) {
				flag = true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtils.realse(conn, ps, null);
		}

		return flag;
	}

}
