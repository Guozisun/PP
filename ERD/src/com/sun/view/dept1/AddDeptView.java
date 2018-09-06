package com.sun.view.dept1;

import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JTextField;

import com.sun.dao.DeptDao;
import com.sun.dao.EmployeeDao;
import com.sun.pojo.CallBack;
import com.sun.pojo.Dept;
import com.sun.pojo.Employee;

/**
 * @author 作者:Chaoguo Sun
 * @createDate 创建时间：2018年8月7日 下午3:34:35
 */
public class AddDeptView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// 主面板
	BoxLayout boxLayout;// 盒子控件
	JLabel nameLable, numLable;// lable控件
	JTextField nameField,numField;// 文本文档
	JButton saveBt;// 按钮
	List<Dept> listDept = new ArrayList<>();
	DeptDao deptDao = new DeptDao();
	List<Employee> list = new ArrayList<>();
	EmployeeDao employeeDao = new EmployeeDao();
	CallBack callBack;
	JFrame frame;

	private static AddDeptView instance;

	public static AddDeptView getInstance(CallBack callBack) {
		if (instance == null) {
			instance = new AddDeptView(callBack);
		}
		return instance;
	}

	public void fun() {
		nameField.setText("");
		numField.setText("0");
	}

	public void openFrame() {
		if (frame == null) {
			init();
		} else {
			fun();
			frame.setVisible(true);
		}
	}

	public AddDeptView(CallBack callBack) {

		this.callBack = callBack;
	}

	public void init() {

		// -------------------- java swing 框架 -----------------------
		frame = new JFrame("员工管理系统");
		frame.setSize(300, 400);// 设置宽度和高度
		frame.setLocationRelativeTo(null);// 居中
		frame.setResizable(false);// 不可放大
		jPanel = (JPanel) frame.getContentPane();// 获得主面板
		boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);// y轴分开
		jPanel.setLayout(boxLayout);
		// 第一面板

		nameLable = new JLabel("部门名称");
		nameLable.setPreferredSize(new Dimension(70, 30));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(90, 30));
		
		JPanel jPanel2 = new JPanel();
		jPanel2.add(nameLable);
		jPanel2.add(nameField);

		numLable = new JLabel("员工人数");
		numLable.setPreferredSize(new Dimension(70, 30));
		numField = new JTextField("默认为0");
		numField.setPreferredSize(new Dimension(90, 30));
		JPanel jPanel3 = new JPanel();
		jPanel3.add(numLable);
		jPanel3.add(numField);
		saveBt = new JButton("保存");
		saveBt.setPreferredSize(new Dimension(60, 30));
		saveBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (nameField.getText().equals("") && numField.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "请输入完整信息！");

				} else {
					numField.setText("禁止输入，默认为0");
					String name = nameField.getText();
					Dept dept = new Dept();
					dept.setName(name);
					dept.setE_count(0);
					

					boolean flag = deptDao.addDept(dept);
					if (flag) {
						JOptionPane.showMessageDialog(null, "添加成功！");
					} else {
						JOptionPane.showMessageDialog(null, "添加失败！");

					}
					frame.dispose();
					callBack.call();
				}

				
				
			}
		});
		JPanel jPanel6 = new JPanel();
		jPanel6.add(saveBt);

		jPanel.add(jPanel2);
		jPanel.add(jPanel3);
		
		jPanel.add(jPanel6);
		frame.setVisible(true);
		callBack.call();
	}

}
