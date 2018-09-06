package com.sun.view.employee;

import java.awt.Dimension;

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
public class AddEmployeeView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// 主面板
	BoxLayout boxLayout;// 盒子控件
	JLabel nameLable, sexLable, ageLable, deptLable;// lable控件
	JTextField nameField,  ageField;// 文本文档
	JButton saveBt;// 按钮
	JComboBox<Object> box;
	JComboBox<String> box1;
	List<Dept> listDept = new ArrayList<>();
	DeptDao deptDao = new DeptDao();
	List<Employee> list = new ArrayList<>();
	EmployeeDao employeeDao = new EmployeeDao();
	CallBack callBack;
	JFrame frame;

	private static AddEmployeeView instance;

	public static AddEmployeeView getInstance(CallBack callBack) {
		if (instance == null) {
			instance = new AddEmployeeView(callBack);
		}
		return instance;
	}

	public void fun() {
		nameField.setText("");
		box1.setSelectedIndex(0);
		ageField.setText("");
		box.setSelectedIndex(0);
	}

	public void openFrame() {
		if (frame == null) {
			init();
		} else {
			fun();
			frame.setVisible(true);
		}
	}

	public AddEmployeeView(CallBack callBack) {

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

		nameLable = new JLabel("姓名:");
		nameLable.setPreferredSize(new Dimension(30, 30));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(60, 30));
		JPanel jPanel2 = new JPanel();
		jPanel2.add(nameLable);
		jPanel2.add(nameField);

		sexLable = new JLabel("性别:");
		sexLable.setPreferredSize(new Dimension(30, 30));
		box1 = new JComboBox<String>(); 
		box1.addItem("男");
		box1.addItem("女");
		box1.setPreferredSize(new Dimension(60, 30));
		JPanel jPanel3 = new JPanel();
		jPanel3.add(sexLable);
		jPanel3.add(box1);

		ageLable = new JLabel("年龄:");
		ageLable.setPreferredSize(new Dimension(30, 30));
		ageField = new JTextField();
		ageField.setPreferredSize(new Dimension(60, 30));
		JPanel jPanel4 = new JPanel();
		jPanel4.add(ageLable);
		jPanel4.add(ageField);

		deptLable = new JLabel("部门:");
		deptLable.setPreferredSize(new Dimension(30, 30));
		box = new JComboBox<Object>();
		box.addItem("请选择部门");
		listDept = deptDao.selectAll();
		for (int i = 0; i < listDept.size(); i++) {
			box.addItem(listDept.get(i).getName());
		}
		JPanel jPanel5 = new JPanel();
		jPanel5.add(deptLable);
		jPanel5.add(box);

		saveBt = new JButton("保存");
		saveBt.setPreferredSize(new Dimension(60, 30));
		saveBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			

				String name = nameField.getText();
				int sexIndex = box1.getSelectedIndex();
				int age=-1;
				try {
					age = Integer.parseInt(ageField.getText());
				} catch (Exception e2) {
					e2.getStackTrace();
				} 
				int index = box.getSelectedIndex();
				if(index!=0) {
					if (!name.equals("")) {
						Employee emp = new Employee();
						emp.setName(name);
						emp.setSex(box1.getItemAt(sexIndex));
						emp.setAge(age);
						emp.setD_id(listDept.get(index - 1).getId());
						boolean flag=employeeDao.addEmployees(emp);
						if (!flag) {
							frame.dispose();
							callBack.call();
						}
						
					}else {
						JOptionPane.showMessageDialog(null,"请输入姓名！");
					}

				}
				else {
					JOptionPane.showMessageDialog(null,"请选中部门");
				}
				
					
					
				
				
			}
		});
		JPanel jPanel6 = new JPanel();
		jPanel6.add(saveBt);

		jPanel.add(jPanel2);
		jPanel.add(jPanel3);
		jPanel.add(jPanel4);
		jPanel.add(jPanel5);
		jPanel.add(jPanel6);
		frame.setVisible(true);

	}

}
