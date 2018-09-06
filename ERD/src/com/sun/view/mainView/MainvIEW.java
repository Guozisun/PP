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
 * @author ����:Chaoguo Sun
 * @createDate ����ʱ�䣺2018��8��9�� ����7:40:41
 */
public class MainvIEW extends JFrame {
JButton empBt,deptBt,projectBt,scBt;
	private static final long serialVersionUID = 1L;
	public void init() {
		// TODO Auto-generated method stub
		JFrame frame = new JFrame("Ա������ϵͳ");
		frame.setSize(300, 200);
		frame.setLocationRelativeTo(null);// ����
		frame.setResizable(false);// ���ɷŴ�
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �ر������˳�
		JPanel jPanel = (JPanel) frame.getContentPane();// ��������
		
		jPanel.setLayout(new FlowLayout());
		
		empBt = new JButton("Ա������");
		
		empBt.setPreferredSize(new Dimension(120, 70));
		empBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new EmployeeView().init();
			}
		});
		deptBt = new JButton("���Ź���");
		deptBt.setPreferredSize(new Dimension(120, 70));
		deptBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new DeptView().init();
			}
		});
		projectBt = new JButton("��Ŀ����");
		projectBt.setPreferredSize(new Dimension(120, 70));
		projectBt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new ProjectView().init();
			}
		});
		scBt = new JButton("��Ч����");
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
