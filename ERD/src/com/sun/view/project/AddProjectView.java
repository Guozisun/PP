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
 * @author ����:Chaoguo Sun
 * @createDate ����ʱ�䣺2018��8��7�� ����3:34:35
 */
public class AddProjectView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// �����
	BoxLayout boxLayout;// ���ӿؼ�
	JLabel nameLable;// lable�ؼ�
	JTextField nameField;// �ı��ĵ�
	JButton saveBt;// ��ť
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

		// -------------------- java swing ��� -----------------------
		frame = new JFrame("Ա������ϵͳ");
		frame.setSize(300, 400);// ���ÿ�Ⱥ͸߶�
		frame.setLocationRelativeTo(null);// ����
		frame.setResizable(false);// ���ɷŴ�
		jPanel = (JPanel) frame.getContentPane();// ��������
		boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);// y��ֿ�
		jPanel.setLayout(boxLayout);
		// ��һ���

		nameLable = new JLabel("��Ŀ����");
		nameLable.setPreferredSize(new Dimension(70, 30));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(90, 30));
		
		JPanel jPanel2 = new JPanel();
		jPanel2.add(nameLable);
		jPanel2.add(nameField);

		
		saveBt = new JButton("����");
		saveBt.setPreferredSize(new Dimension(60, 30));
		saveBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (nameField.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "������������Ϣ��");

				} else {
			
					String name = nameField.getText();
					Project project = new Project();
					project.setName(name);
				
					

					boolean flag = projectDao.addProject(project);
					if (flag) {
						JOptionPane.showMessageDialog(null, "��ӳɹ���");
					} else {
						JOptionPane.showMessageDialog(null, "���ʧ�ܣ�");

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
