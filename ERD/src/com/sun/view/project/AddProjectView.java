package com.sun.view.project;

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

import com.sun.dao.ProjectDao;
import com.sun.dao.EmployeeDao;
import com.sun.pojo.CallBack;
import com.sun.pojo.Project;
import com.sun.pojo.Employee;

/**
 * @author 作者:Chaoguo Sun
 * @createDate 创建时间：2018年8月7日 下午3:34:35
 */
public class AddProjectView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// 主面板
	BoxLayout boxLayout;// 盒子控件
	JLabel nameLable;// lable控件
	JTextField nameField;// 文本文档
	JButton saveBt;// 按钮
	List<Project> listProjectProject = new ArrayList<>();
	ProjectDao projectDao = new ProjectDao();
	List<Employee> listProject = new ArrayList<>();
	EmployeeDao employeeDao = new EmployeeDao();
	CallBack callBack;
	JFrame frame;

	private static AddProjectView instance;

	public static AddProjectView getInstance(CallBack callBack) {
		if (instance == null) {
			instance = new AddProjectView(callBack);
		}
		return instance;
	}

	public void fun() {
		nameField.setText("");
	
	}

	public void openFrame() {
		if (frame == null) {
			init();
		} else {
			fun();
			frame.setVisible(true);
		}
	}

	public AddProjectView(CallBack callBack) {

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

		nameLable = new JLabel("项目名称");
		nameLable.setPreferredSize(new Dimension(70, 30));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(90, 30));
		
		JPanel jPanel2 = new JPanel();
		jPanel2.add(nameLable);
		jPanel2.add(nameField);

		
		saveBt = new JButton("保存");
		saveBt.setPreferredSize(new Dimension(60, 30));
		saveBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (nameField.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "请输入完整信息！");

				} else {
			
					String name = nameField.getText();
					Project project = new Project();
					project.setName(name);
				
					

					boolean flag = projectDao.addProject(project);
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
	
		
		jPanel.add(jPanel6);
		frame.setVisible(true);
		callBack.call();
	}

}
