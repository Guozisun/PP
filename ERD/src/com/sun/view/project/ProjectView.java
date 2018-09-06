package com.sun.view.project;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

import com.sun.dao.ProjectDao;
import com.sun.dao.EmployeeDao;
import com.sun.pojo.CallBack;
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
	JButton serchBt, addBt, updataBt, deleteBt, flushBt;// 按钮
	JTable jTable;// 表格
	ProjectTableModle modle1;// 表格模型

	List<Project> listProject = new ArrayList<>();
	List<Project> listProjectProject = new ArrayList<>();
	EmployeeDao employeeDao = new EmployeeDao();
	ProjectDao projectDao = new ProjectDao();

	public void init() {

		// -------------------- java swing 框架 -----------------------
		JFrame frame = new JFrame("部门管理系统");
		frame.setSize(600, 500);// 设置宽度和高度
		frame.setLocation(50, 400);
//		frame.setLocationRelativeTo(null);// 居中
		frame.setResizable(false);// 不可放大
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭立即退出
		jPanel = (JPanel) frame.getContentPane();// 获得主面板
		boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);// y轴分开
		jPanel.setLayout(boxLayout);
		// 第一面板

		nameLable = new JLabel("项目名称：");
		nameLable.setPreferredSize(new Dimension(70, 30));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(90, 30));
		
		serchBt = new JButton("搜索");
		serchBt.setPreferredSize(new Dimension(70, 30));
		serchBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = nameField.getText();
				

				Project project = new Project();
				project.setName(name);
				
			
				listProject = projectDao.selectByCondition(project);
				flush(listProject);

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
	

		jPanel2.add(serchBt);
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		jPanel.add(jPanel2);
		// 将信息显示出来
		listProject = projectDao.selectAll();
		modle1 = new ProjectTableModle(listProject);
		jTable = new JTable();
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 单元格渲染器
		tcr.setHorizontalAlignment(JLabel.CENTER);// 居中显示

		jTable.setDefaultRenderer(Object.class, tcr);//设置渲染器
		jTable.setModel(modle1);

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
				// TODO Auto-generated method stub

				AddProjectView add = AddProjectView.getInstance(new CallBack() {

					@Override
					public void call() {
						// TODO Auto-generated method stub
						flush();
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
				int selectId = listProject.get(selectIndex).getId();
				if (selectIndex == -1) {
					JOptionPane.showMessageDialog(frame, "请先选中员工！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					Project selectProject = listProject.get(selectIndex);
					UpdateProject updateEmployee = UpdateProject.getInstance(selectProject, new CallBack() {

						@Override
						public void call() {
							// TODO Auto-generated method stub
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
//				 int selectIndex = jTable.getSelectedRow();
//				 if (selectIndex == -1) {
//				 JOptionPane.showMessageDialog(frame, "请先选中员工！", "警告",
//				 JOptionPane.WARNING_MESSAGE);
//				 }else {
//				
//				 boolean flag = employeeDao.deleteEmployee(listProject.get(selectIndex).getId());
//				 if (flag) {
//				 JOptionPane.showMessageDialog(null, "删除成功！");
//				 }
//				 else {
//				 JOptionPane.showMessageDialog(null, "删除失败！");
//				 }
//				 }
//				 flush();
				deleteBach();
				flush();
			}
		});

		flushBt = new JButton("项目管理");
		flushBt.setPreferredSize(new Dimension(100, 30));
		flushBt.setMnemonic(java.awt.event.KeyEvent.VK_F);
		flushBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIndex = jTable.getSelectedRow();
				if (selectIndex == -1) {
					JOptionPane.showMessageDialog(frame, "请先选中部门！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
//					new ProjectView().init();
				}

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
		listProject.clear();
		listProject = projectDao.selectAll();
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
//	public static void main(String[] args) {
//		new ProjectView().init();
//	}
}