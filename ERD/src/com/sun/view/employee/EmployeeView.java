package com.sun.view.employee;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import com.sun.dao.DeptDao;
import com.sun.dao.EmployeeDao;
import com.sun.pojo.CallBack;
import com.sun.pojo.Dept;
import com.sun.pojo.Employee;

/**
 * @author 作者:Chaoguo Sun
 * @createDate 创建时间：2018年8月7日 下午2:01:59
 */
public class EmployeeView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// 主面板
	BoxLayout boxLayout;// 盒子控件
	JLabel nameLable, sexLable, ageLable, deptLable;// lable控件
	JTextField nameField, sexField, ageField, deptField;// 文本文档
	JButton serchBt, addBt, updataBt, deleteBt, flushBt;// 按钮
	JTable jTable;// 表格
	EmpTableModle modle1;// 表格模型
	JComboBox<Object> box;
	List<Employee> list = new ArrayList<>();
	List<Dept> listDept = new ArrayList<>();
	EmployeeDao employeeDao = new EmployeeDao();
	DeptDao deptDao = new DeptDao();

	public void init() {

		// -------------------- java swing 框架 -----------------------
		JFrame frame = new JFrame("员工管理系统");
		frame.setSize(600, 500);// 设置宽度和高度
		frame.setLocation(50, 20);
		// frame.setLocationRelativeTo(null);// 居中
		frame.setResizable(false);// 不可放大

		jPanel = (JPanel) frame.getContentPane();// 获得主面板
		boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);// y轴分开
		jPanel.setLayout(boxLayout);
		// 第一面板

		nameLable = new JLabel("姓名:");
		nameLable.setPreferredSize(new Dimension(30, 30));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(60, 30));
		sexLable = new JLabel("性别:");
		sexLable.setPreferredSize(new Dimension(30, 30));
		sexField = new JTextField();
		sexField.setPreferredSize(new Dimension(60, 30));
		ageLable = new JLabel("年龄:");
		ageLable.setPreferredSize(new Dimension(30, 30));
		ageField = new JTextField();
		ageField.setPreferredSize(new Dimension(60, 30));
		deptLable = new JLabel("部门:");
		deptLable.setPreferredSize(new Dimension(30, 30));
		// 下拉框
		box = new JComboBox<Object>();
		box.addItem("请选择部门");
		listDept = deptDao.selectAll();
		for (int i = 0; i < listDept.size(); i++) {
			box.addItem(listDept.get(i).getName());
		}
		serchBt = new JButton("搜索");
		serchBt.setPreferredSize(new Dimension(60, 30));
		serchBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = nameField.getText();
				String sex = sexField.getText();
				int age = -1;
				try {
					age = Integer.parseInt(ageField.getText());
				} catch (Exception e2) {
					e2.getStackTrace();
				}

				Employee emp = new Employee();
				emp.setName(name);
				emp.setSex(sex);
				emp.setAge(age);

				Dept dept = new Dept();
				int index = box.getSelectedIndex();
				if (index == 0) {
					dept.setId(-1);
				} else {
					dept = listDept.get(index - 1);
				}

				emp.setDept(dept);

				list = employeeDao.selectByCondition(emp);
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(null, "没有查询到信息！");
					flush();
				} else {
					flush(list);
				}

			}
		});

		// 模板分为上中下三部分
		// 第一部分搜索框
		// 第二部分table
		// 的三部分按钮
		JPanel jPanel2 = new JPanel();
		JPanel jPanel3 = new JPanel();
		JPanel jPanel4 = new JPanel();

		jPanel2.add(nameLable);
		jPanel2.add(nameField);
		jPanel2.add(sexLable);
		jPanel2.add(sexField);
		jPanel2.add(ageLable);
		jPanel2.add(ageField);
		jPanel2.add(deptLable);
		jPanel2.add(box);
		jPanel2.add(serchBt);
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		jPanel.add(jPanel2);
		// 将信息显示出来
		/*
		 * 1首先将信息显示出来 查询出来放到list中 将list放到tablemodle中
		 */
		list = employeeDao.selectAll();
		modle1 = new EmpTableModle(list);
		jTable = new JTable();
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 单元格渲染器
		tcr.setHorizontalAlignment(JLabel.CENTER);// 居中显示

		jTable.setDefaultRenderer(Object.class, tcr);//设置渲染器
		jTable.setModel(modle1);
		// 将table放入滚动条中
		JScrollPane jScrollPane = new JScrollPane(jTable);
		jScrollPane.setPreferredSize(new Dimension(500, 300));
		jPanel3.add(jScrollPane);
		jPanel.add(jPanel3);
		// 第三模块
		// ------------------------------------添加------------------------------------------------
		addBt = new JButton("添加");
		addBt.setPreferredSize(new Dimension(60, 30));
		addBt.setMnemonic(java.awt.event.KeyEvent.VK_S);// 热键alt+s
		addBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				AddEmployeeView add = AddEmployeeView.getInstance(new CallBack() {

					@Override
					public void call() {

						flush();// 刷新
						// 添加之后选中添加行
						int rowCount = jTable.getRowCount(); // 获得行数
						jTable.getSelectionModel().setSelectionInterval(rowCount - 1, rowCount - 1);// 选中表格的最后一行。
						Rectangle rect = jTable.getCellRect(rowCount - 1, 0, true); // 获取最后一行显示区域的坐标信息。
						jTable.scrollRectToVisible(rect);
					}
				});
				add.openFrame();
			}
		});

		// ---------------------------------------------修改----------------------------------------------------------
		updataBt = new JButton("修改");
		updataBt.setPreferredSize(new Dimension(60, 30));
		updataBt.setMnemonic(java.awt.event.KeyEvent.VK_A);
		updataBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIndex = jTable.getSelectedRow();
				int selectId = list.get(selectIndex).getId();
				if (selectIndex == -1) {
					JOptionPane.showMessageDialog(frame, "请先选中员工！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					Employee selectEmp = list.get(selectIndex);

					UpdateEmployee updateEmployee = UpdateEmployee.getInstance(selectEmp, new CallBack() {

						@Override
						public void call() {
							flush();
						}
					});
					updateEmployee.setSelectId(selectId);
					updateEmployee.openFrame();
				}
			}
		});

		// -------------------------------删除---------------------------------------------
		deleteBt = new JButton("删除");
		deleteBt.setPreferredSize(new Dimension(60, 30));
		deleteBt.setMnemonic(java.awt.event.KeyEvent.VK_D);
		deleteBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// int selectIndex = jTable.getSelectedRow();
				// if (selectIndex == -1) {
				// JOptionPane.showMessageDialog(frame, "请先选中员工！", "警告",
				// JOptionPane.WARNING_MESSAGE);
				// }else {
				//
				// boolean flag = employeeDao.deleteEmployee(list.get(selectIndex).getId());
				// if (flag) {
				// JOptionPane.showMessageDialog(null, "删除成功！");
				// }
				// else {
				// JOptionPane.showMessageDialog(null, "删除失败！");
				// }
				// }
				// flush();
				deleteBach();
				flush();
			}
		});

		flushBt = new JButton("刷新");
		flushBt.setPreferredSize(new Dimension(60, 30));
		flushBt.setMnemonic(java.awt.event.KeyEvent.VK_F);
		flushBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flush();

			}
		});
		jPanel4.add(addBt);
		jPanel4.add(updataBt);
		jPanel4.add(deleteBt);
		jPanel4.add(flushBt);
		jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
		jPanel.add(jPanel4);
		frame.setVisible(true);

	}

	// 》》》》》》》》》》》》》》刷新《《《《《《《《《《《《《《《《《《《《《《《

	public void flush() {
		list.clear();
		list = employeeDao.selectAll();
		modle1.setList(list);
		jTable.updateUI();

	}

	public void flush(List<Employee> list) {

		modle1.setList(list);
		jTable.updateUI();
		jTable.clearSelection();
	}

	// 批量删除
	public void deleteBach() {
		//定义数组 将选中的行数放入数组
		int[] index = jTable.getSelectedRows();

		if (index.length > 0) {
			//获取ID 将Id放入字符串中
			String deleteIds = "";
			for (int i = index.length - 1; i >= 0; i--) {
				deleteIds += list.get(index[i]).getId() + ",";

			}
			deleteIds = deleteIds.substring(0, deleteIds.length() - 1);
			boolean flag = employeeDao.deleteEmployee1(deleteIds);
			if (flag) {
				JOptionPane.showMessageDialog(null, "删除成功！");
			} else {
				JOptionPane.showMessageDialog(null, "删除失败！");
			}
		}

		else {
			JOptionPane.showMessageDialog(null, "请选中信息");
		}

	}

	// 》》》》》》》》主函数《《《《《《《《《《《《《《

}