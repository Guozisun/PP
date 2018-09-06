package com.sun.view.score;

import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import com.sun.dao.ProjectDao;
import com.sun.dao.ScoreDao1;

import com.sun.pojo.Dept;
import com.sun.pojo.Employee;
import com.sun.pojo.Project;
import com.sun.pojo.Score;

/**
 * @author 作者:Chaoguo Sun
 * @createDate 创建时间：2018年8月7日 下午2:01:59
 */
public class ScoreView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// 主面板
	BoxLayout boxLayout;// 盒子控件
	JLabel nameLable, pNameLable, valueLable, leveLable, xNameLable;// lable控件
	JTextField nameField, valueField;// 文本文档
	JButton serchBt, updataBt;// 按钮
	JTable jTable;// 表格
	ScoreTableModle modle1;// 表格模型
	JComboBox<Object> boxEmp, boxDept, boxPro, boxScore;
	List<Employee> list = new ArrayList<>();
	List<Dept> listDept = new ArrayList<>();
	List<Project> listPro = new ArrayList<>();
	List<Score> listScore = new ArrayList<>();

	EmployeeDao employeeDao = new EmployeeDao();
	DeptDao deptDao = new DeptDao();
	ProjectDao projectDao = new ProjectDao();
	ScoreDao1 scoreDao = new ScoreDao1();

	public void init() {

		// -------------------- java swing 框架 -----------------------
		JFrame frame = new JFrame("绩效管理系统");
		frame.setSize(900, 500);// 设置宽度和高度
		frame.setLocation(900, 400);
		// frame.setLocationRelativeTo(null);// 居中
		frame.setResizable(false);// 不可放大
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭立即退出
		jPanel = (JPanel) frame.getContentPane();// 获得主面板
		boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);// y轴分开
		jPanel.setLayout(boxLayout);
		// 第一面板

		nameLable = new JLabel("员工姓名");
		nameLable.setPreferredSize(new Dimension(60, 30));

		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(60, 30));
		pNameLable = new JLabel("部门名称");
		pNameLable.setPreferredSize(new Dimension(60, 30));
		boxDept = new JComboBox<Object>();
		boxDept.addItem("请选择部门");
		listDept = deptDao.selectAll();
		for (int i = 0; i < listDept.size(); i++) {
			boxDept.addItem(listDept.get(i).getName());
		}
		// pNameField = new JTextField();
		// pNameField.setPreferredSize(new Dimension(60, 30));
		xNameLable = new JLabel("项目名称");
		xNameLable.setPreferredSize(new Dimension(60, 30));
		boxPro = new JComboBox<Object>();
		boxPro.addItem("请选择项目");
		listPro = projectDao.selectAll();
		for (int i = 0; i < listPro.size(); i++) {
			boxPro.addItem(listPro.get(i).getName());
		}
		// xNameField = new JTextField();
		// xNameField.setPreferredSize(new Dimension(60, 30));
		valueLable = new JLabel("成绩");
		valueLable.setPreferredSize(new Dimension(30, 30));
		valueField = new JTextField();
		valueField.setPreferredSize(new Dimension(60, 30));
		leveLable = new JLabel("等级");
		leveLable.setPreferredSize(new Dimension(30, 30));
		boxScore = new JComboBox<Object>();
		boxScore.addItem("请选择等级");
		String[] levels = { "优秀", "良好", "中等", "及格", "不及格" };

		for (int i = 0; i < levels.length; i++) {
			boxScore.addItem(levels[i]);
		}
		// leveField = new JTextField();
		// leveField.setPreferredSize(new Dimension(40, 30));
		serchBt = new JButton("搜索");
		serchBt.setPreferredSize(new Dimension(60, 30));
		serchBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Score score = new Score();
				Employee emp = new Employee();
				String name = nameField.getText();
				emp.setName(name);
				Dept dept = new Dept();
				int indexDept = boxDept.getSelectedIndex();
				if (indexDept == 0) {
					dept.setId(-1);
				} else {
					dept = listDept.get(indexDept - 1);
				}
				emp.setDept(dept);

				Project project = new Project();
				int indexPro = boxPro.getSelectedIndex();
				if (indexPro == 0) {
					project.setId(-1);
				} else {
					project = listPro.get(indexPro - 1);
				}
				score.setProject(project);
				int value = -1;
				try {
					value = Integer.parseInt(valueField.getText());
				} catch (Exception e2) {
					// TODO: handle exception
				}
				int indexSore = boxScore.getSelectedIndex();
				String grade = null;
				if (indexSore == 0) {
					grade = "-1";
				} else {
					grade = (String) boxScore.getSelectedItem();
				}

				score.setEmployee(emp);

				score.setValue(value);
				score.setGrade(grade);

				listScore = scoreDao.selectByCondition(score);
				flush(listScore);

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
		jPanel2.add(pNameLable);
		jPanel2.add(boxDept);
		jPanel2.add(xNameLable);
		jPanel2.add(boxPro);
		jPanel2.add(valueLable);
		jPanel2.add(valueField);
		jPanel2.add(leveLable);
		jPanel2.add(boxScore);
		jPanel2.add(serchBt);

		jPanel.add(jPanel2);
		// 将信息显示出来
		
		listScore = scoreDao.selectAll();
		modle1 = new ScoreTableModle(listScore);
		jTable = new JTable();
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// 单元格渲染器
		tcr.setHorizontalAlignment(JLabel.CENTER);// 居中显示

		jTable.setDefaultRenderer(Object.class, tcr);//设置渲染器
		

		jTable.setModel(modle1);

		JScrollPane jScrollPane = new JScrollPane(jTable);
		jScrollPane.setPreferredSize(new Dimension(700, 300));
		jPanel3.add(jScrollPane);
		jPanel.add(jPanel3);
		// 第三模块
		// ------------------------------------添加------------------------------------------------

		// ---------------------------------------------修改----------------------------------------------------------
		// refreshBt = new JButton("刷新");
		// refreshBt.setPreferredSize(new Dimension(60, 30));
		// refreshBt.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		// // TODO Auto-generated method stub
		// flush();
		//
		// }
		// });
		updataBt = new JButton("修改");
		updataBt.setPreferredSize(new Dimension(60, 30));

		updataBt.setMnemonic(java.awt.event.KeyEvent.VK_A);
		updataBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				int selectIndex = jTable.getSelectedRow();
				if (selectIndex == -1) {
					JOptionPane.showMessageDialog(frame, "请先选中员工！", "警告", JOptionPane.WARNING_MESSAGE);
				} else {
					Set<Score> listScore1 = modle1.getListScore();
					scoreDao.save(listScore1);
					listScore1.clear();
				}
				flush();
			}
		});

		// jPanel4.add(refreshBt);
		jPanel4.add(updataBt);
		jPanel4.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));
		jPanel.add(jPanel4);
		frame.setVisible(true);

	}

	// 》》》》》》》》》》》》》》刷新《《《《《《《《《《《《《《《《《《《《《《《

	public void flush() {
		listScore = scoreDao.selectAll();
		modle1.setListScore(listScore);
		jTable.updateUI();
		// jTable.clearSelection();
	}

	public void flush(List<Score> listCore) {

		modle1.setListScore(listScore);
		jTable.updateUI();
		// jTable.clearSelection();
	}

	// 》》》》》》》》主函数《《《《《《《《《《《《《《
	public static void main(String[] args) {
		new ScoreView().init();
	}
}