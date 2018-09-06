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

import com.sun.pojo.CallBack;
import com.sun.pojo.Dept;


/**
 * @author 作者:Chaoguo Sun
 * @createDate 创建时间：2018年8月7日 下午3:34:35
 */
public class UpdateDept extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// 主面板
	BoxLayout boxLayout;// 盒子控件
	JLabel nameLable, numLable;// lable控件
	JTextField nameField, numField;// 文本文档
	JButton saveBt;// 按钮
	CallBack callBack;
	JFrame frame;
	Dept selectDept;
	List<Dept> listDept = new ArrayList<>();
	DeptDao deptDao = new DeptDao();

	private int selectId;

	public int getSelectId() {
		return selectId;
	}

	public void setSelectId(int selectId) {
		this.selectId = selectId;
	}

	

	private static UpdateDept instance;

	public static UpdateDept getInstance(Dept selectDept, CallBack callBack) {
		if (instance == null) {
			instance = new UpdateDept(selectDept, callBack);
		}
		return instance;
	}

	public void fun() {
		Dept dept = deptDao.selectOne(selectId);
	
		nameField.setText(dept.getName());
		numField.setText(String.valueOf(dept.getE_count()));
	}

	public void openFrame() {
		if (frame == null) {
			init();
		} else {
			fun();
			frame.setVisible(true);
		}
	}

	public UpdateDept(Dept selectDept, CallBack callBack) {
		this.selectDept = selectDept;
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
		numField = new JTextField();
		numField.setPreferredSize(new Dimension(90, 30));
		JPanel jPanel3 = new JPanel();
		jPanel3.add(numLable);
		jPanel3.add(numField);
		nameField.setText(selectDept.getName());
		numField.setText(String.valueOf(selectDept.getE_count()));

		saveBt = new JButton("保存");
		saveBt.setPreferredSize(new Dimension(60, 30));
		saveBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				int num = Integer.parseInt(numField.getText());
				selectDept.setName(name);
				selectDept.setE_count(num);

				boolean flag = deptDao.updateDept(selectDept);
				if (flag) {
					JOptionPane.showMessageDialog(null, "保存成功！");
				} else {
					JOptionPane.showMessageDialog(null, "保存失败！");
				}
				frame.dispose();
				callBack.call();

			}
		});
		JPanel jPanel6 = new JPanel();
		jPanel6.add(saveBt);

		jPanel.add(jPanel2);
		jPanel.add(jPanel3);
		jPanel.add(jPanel6);
		frame.setVisible(true);

	}

}
