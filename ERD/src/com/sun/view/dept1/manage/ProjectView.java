package com.sun.view.dept1.manage;

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

import com.sun.dao.ProjectDao;
import com.sun.dao.ProjectDao1;
import com.sun.dao.EmployeeDao;
import com.sun.pojo.CallBack;
import com.sun.pojo.Dept;
import com.sun.pojo.Project;



/**
 * @author 作者:Chaoguo Sun
 * @createDate 创建时间：2018年8月7日 下午2:01:59
 */
public class ProjectView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// 主面板
	BoxLayout boxLayout;// 盒子控件
	JLabel nameLable;// lable控件
	JTextField nameField;// 文本文档
	JButton addBt, deleteBt;// 按钮
	JTable jTable;// 表格
	ProjectTableModle modle1;// 表格模型d
	Dept dept;
	JComboBox<String> comboBox;
	public ProjectView(Dept dept) {
		
		this.dept = dept;
	}
	List<Project> listProject = new ArrayList<>();
	List<Project> listProject1 = new ArrayList<>();
	EmployeeDao employeeDao = new EmployeeDao();
	ProjectDao projectDao = new ProjectDao();
	ProjectDao1 projectDao1 = new ProjectDao1();
	public void init() {

		// -------------------- java swing 框架 -----------------------
		JFrame frame = new JFrame("项目管理系统");
		frame.setSize(500, 400);// 设置宽度和高度
		frame.setLocation(800, 200);
//		frame.setLocationRelativeTo(null);// 居中
		frame.setResizable(false);// 不可放大
	
		jPanel = (JPanel) frame.getContentPane();// 获得主面板
		boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);// y轴分开
		jPanel.setLayout(boxLayout);
		// 第一面板

		nameLable = new JLabel(dept.getName());
		nameLable.setPreferredSize(new Dimension(70, 30));
		
		
		
		// 模板分为上中下三部分
		// 第一部分搜索框
		// 第二部分table
		// 的三部分按钮
		JPanel jPanel2 = new JPanel();
		JPanel jPanel3 = new JPanel();
		JPanel jPanel4 = new JPanel();

		jPanel2.add(nameLable);
	
	

	
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		jPanel.add(jPanel2);
		// 将信息显示出来
		listProject = projectDao1.selectAll(dept.getId());
		modle1 = new ProjectTableModle(listProject);
		jTable = new JTable();
		jTable.setModel(modle1);

		JScrollPane jScrollPane = new JScrollPane(jTable);
		jScrollPane.setPreferredSize(new Dimension(400, 200));
		jPanel3.add(jScrollPane);
		jPanel.add(jPanel3);
		// 第三模块
		// ------------------------------------添加------------------------------------------------
		comboBox = new JComboBox<>();
		listProject1 = projectDao1.selectNotIn(dept.getId());
		for (int i = 0; i < listProject1.size(); i++) {
			comboBox.addItem(listProject1.get(i).getName());
		}
		addBt = new JButton("添加");
		addBt.setPreferredSize(new Dimension(60, 30));
		addBt.setMnemonic(java.awt.event.KeyEvent.VK_S);// 热键alt+s
		addBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int index = comboBox.getSelectedIndex();
				if (index>-1) {
					int dId= dept.getId();
					int pId=listProject1.get(index).getId();
					boolean flag=projectDao1.addProject(dId, pId);
					if (flag) {
						JOptionPane.showMessageDialog(null, "保存成功！");
					} else {
						JOptionPane.showMessageDialog(null, "保存失败！");
					}
					
					flush();
				}
				else {
					JOptionPane.showMessageDialog(null, "项目已选完");
				}
				
				
			}
			
		});

		// ---------------------------------------------修改----------------------------------------------------------
		
		// -------------------------------删除---------------------------------------------
		deleteBt = new JButton("删除");
		deleteBt.setPreferredSize(new Dimension(60, 30));
		deleteBt.setMnemonic(java.awt.event.KeyEvent.VK_D);
		deleteBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 int selectIndex = jTable.getSelectedRow();
				 if (selectIndex == -1) {
				 JOptionPane.showMessageDialog(frame, "请先选中员工！", "警告",
				 JOptionPane.WARNING_MESSAGE);
				 }else {
				
				 boolean flag = projectDao1.deleteProject(dept.getId(), listProject.get(selectIndex).getId());
				 if (flag) {
				 JOptionPane.showMessageDialog(null, "删除成功！");
				 }
				 else {
				 JOptionPane.showMessageDialog(null, "删除失败！");
				 }
				 }
				 flush();
		
				
			}
		});

		jPanel4.add(comboBox);
		jPanel4.add(addBt);
		
		jPanel4.add(deleteBt);
		
		jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
		jPanel.add(jPanel4);
		frame.setVisible(true);

	}

	// 》》》》》》》》》》》》》》刷新《《《《《《《《《《《《《《《《《《《《《《《

	public void flush() {
		
		listProject = projectDao1.selectAll(dept.getId());
		comboBox.removeAllItems();
		listProject1 = projectDao1.selectNotIn(dept.getId());
		for (int i = 0; i < listProject1.size(); i++) {
			comboBox.addItem(listProject1.get(i).getName());
		}
		
		modle1.setList(listProject);
		jTable.updateUI();
		jTable.clearSelection();
	}

	public void flush(List<Project> listProject) {

		modle1.setList(listProject);
		jTable.updateUI();
		jTable.clearSelection();
	}

	public void deleteBach() {
		int[] index = jTable.getSelectedRows();

		if (index.length > 0) {
			String deleteIds = "";
			for (int i = index.length - 1; i >= 0; i--) {
				deleteIds += listProject.get(index[i]).getId() + ",";

			}
			deleteIds = deleteIds.substring(0, deleteIds.length() - 1);
			boolean flag = projectDao.deleteProjects(deleteIds);
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