package com.sun.view.mainView;

import java.awt.Dimension;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.sun.view.dept1.DeptView;
import com.sun.view.employee.EmployeeView;
import com.sun.view.project.ProjectView;
import com.sun.view.score.ScoreView;

/**
 * @author 作者:Chaoguo Sun
 * @createDate 创建时间：2018年8月9日 下午7:40:41
 */
public class MainvIEW extends JFrame {
JButton empBt,deptBt,projectBt,scBt;
	private static final long serialVersionUID = 1L;
	public void init() {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("员工管理系统");
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(null);// 居中
		frame.setResizable(false);// 不可放大
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭立即退出
		JPanel jPanel = (JPanel) frame.getContentPane();// 获得主面板
		
		jPanel.setLayout(new FlowLayout());
		
		empBt = new JButton("员工管理");
		
		empBt.setPreferredSize(new Dimension(120, 70));
		empBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new EmployeeView().init();
			}
		});
		deptBt = new JButton("部门管理");
		deptBt.setPreferredSize(new Dimension(120, 70));
		deptBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new DeptView().init();
			}
		});
		projectBt = new JButton("项目管理");
		projectBt.setPreferredSize(new Dimension(120, 70));
		projectBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ProjectView().init();
			}
		});
		scBt = new JButton("绩效管理");
		scBt.setPreferredSize(new Dimension(120, 70));
		scBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ScoreView().init();
			}
		});
		jPanel.add(empBt);
		jPanel.add(deptBt);
		jPanel.add(projectBt);
		jPanel.add(scBt);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		new MainvIEW().init();
	}

}
