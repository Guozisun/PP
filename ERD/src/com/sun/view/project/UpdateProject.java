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

import com.sun.pojo.CallBack;
import com.sun.pojo.Project;


/**
 * @author ����:Chaoguo Sun
 * @createDate ����ʱ�䣺2018��8��7�� ����3:34:35
 */
public class UpdateProject extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// �����
	BoxLayout boxLayout;// ���ӿؼ�
	JLabel nameLable;// lable�ؼ�
	JTextField nameField;// �ı��ĵ�
	JButton saveBt;// ��ť
	
	List<Project> listProjectProject = new ArrayList<>();
	ProjectDao projectDao = new ProjectDao();
	

	CallBack callBack;
	JFrame frame;
	Project selectProject;
	private int selectId;
	

	public int getSelectId() {
		return selectId;
	}

	public void setSelectId(int selectId) {
		this.selectId = selectId;
	}

	private static UpdateProject instance;

	public static UpdateProject getInstance(Project selectProject, CallBack callBack) {
		if (instance == null) {
			instance = new UpdateProject(selectProject, callBack);
		}
		return instance;
	}

	public void fun() {
		Project project = projectDao.selectOne(selectId);
		nameField.setText(project.getName());
		
	}

	public void openFrame() {
		if (frame == null) {
			init();
		} else {
			fun();
			frame.setVisible(true);
		}
	}

	public UpdateProject(Project selectProject, CallBack callBack) {
		this.selectProject = selectProject;
		this.callBack = callBack;
	}

	public void init() {
		
		// -------------------- java swing ��� -----------------------
		frame = new JFrame("Ա������ϵͳ");
		frame.setSize(300, 400);// ���ÿ�Ⱥ͸߶�
		frame.setLocationRelativeTo(null);// ����
		frame.setResizable(false);// ���ɷŴ�
		jPanel = (JPanel) frame.getContentPane();// ��������
		boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);// y��ֿ�
		jPanel.setLayout(boxLayout);
		// ��һ���

		nameLable = new JLabel("��������");
		nameLable.setPreferredSize(new Dimension(70, 30));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(90, 30));
		
		JPanel jPanel2 = new JPanel();
		jPanel2.add(nameLable);
		jPanel2.add(nameField);

		
		nameField.setText(selectProject.getName());

		saveBt = new JButton("����");
		saveBt.setPreferredSize(new Dimension(60, 30));
		saveBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				
				selectProject.setName(name);
				
				boolean flag= projectDao.updateProject(selectProject);
				if (flag) {
					JOptionPane.showMessageDialog(null,"����ɹ���");
				}else {
					JOptionPane.showMessageDialog(null,"����ʧ�ܣ�");
				}
				frame.dispose();
				callBack.call();
			}
		});
		JPanel jPanel6 = new JPanel();
		jPanel6.add(saveBt);

		jPanel.add(jPanel2);
		jPanel.add(jPanel6);
		frame.setVisible(true);

	}
	
}
