package com.sun.dao;
/**
* @author ����:Chaoguo Sun
* @createDate ����ʱ�䣺2018��8��7�� ����10:23:14
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


//���ϲ�ѯ ��ѯ��������
	/*
	 * ���� ��List<Employee> list = new ArrayList<>();����Ļ�
	 * ������ӻ���ˢ�¶��������ǰ������� 
	 */
//	List<Employee> list = new ArrayList<>();
	public List<Employee> selectAll() {
		List<Employee> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			// �������ݿ�
			conn = DBUtils.getConn();
			// ��ѯ���
			String sql = "select e.*,d.name as dName,emp_count from empl1 as e left join dept1 as d on d.id=e.d_id";
			ps = conn.prepareStatement(sql);// Ԥ����
			rs = ps.executeQuery();// �����
			while (rs.next()) {// ��������
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
			// �������ݿ�
			conn = DBUtils.getConn();
			// ��ѯ���
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
			rs = ps.executeQuery(sql);// �����
			while (rs.next()) {// ��������
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
		
			// �������ݿ�
		conn = DBUtils.getConn();
			// ��ѯ���
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
			
			JOptionPane.showMessageDialog(null,"�������ӳɹ�");
			  }else{
			    JOptionPane.showMessageDialog(null,"�����Ѵ���,����������");
			    flag=true;
			   }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null,"����ͬ��������");
			
		}
		finally {
			DBUtils.realse(conn, ps,null );
		}
		
		return flag;
	}// Ԥ����

	public boolean updateEmployees(Employee emp) {
		boolean flag = false;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			// �������ݿ�
			conn = DBUtils.getConn();
			// ��ѯ���
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
		} // Ԥ����
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
