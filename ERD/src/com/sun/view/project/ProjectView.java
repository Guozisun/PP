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
 * @author ����:Chaoguo Sun
 * @createDate ����ʱ�䣺2018��8��7�� ����2:01:59
 */
public class ProjectView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// �����
	BoxLayout boxLayout;// ���ӿؼ�
	JLabel nameLable;// lable�ؼ�
	JTextField nameField;// �ı��ĵ�
	JButton serchBt, addBt, updataBt, deleteBt, flushBt;// ��ť
	JTable jTable;// ���
	ProjectTableModle modle1;// ���ģ��

	List<Project> listProject = new ArrayList<>();
	List<Project> listProjectProject = new ArrayList<>();
	EmployeeDao employeeDao = new EmployeeDao();
	ProjectDao projectDao = new ProjectDao();

	public void init() {

		// -------------------- java swing ��� -----------------------
		JFrame frame = new JFrame("���Ź���ϵͳ");
		frame.setSize(600, 500);// ���ÿ�Ⱥ͸߶�
		frame.setLocation(50, 400);
//		frame.setLocationRelativeTo(null);// ����
		frame.setResizable(false);// ���ɷŴ�
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// �ر������˳�
		jPanel = (JPanel) frame.getContentPane();// ��������
		boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);// y��ֿ�
		jPanel.setLayout(boxLayout);
		// ��һ���

		nameLable = new JLabel("��Ŀ���ƣ�");
		nameLable.setPreferredSize(new Dimension(70, 30));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(90, 30));
		
		serchBt = new JButton("����");
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

		// ģ���Ϊ������������
		// ��һ����������
		// �ڶ�����table
		// �������ְ�ť
		JPanel jPanel2 = new JPanel();
		JPanel jPanel3 = new JPanel();
		JPanel jPanel4 = new JPanel();

		jPanel2.add(nameLable);
		jPanel2.add(nameField);
	

		jPanel2.add(serchBt);
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		jPanel.add(jPanel2);
		// ����Ϣ��ʾ����
		listProject = projectDao.selectAll();
		modle1 = new ProjectTableModle(listProject);
		jTable = new JTable();
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// ��Ԫ����Ⱦ��
		tcr.setHorizontalAlignment(JLabel.CENTER);// ������ʾ

		jTable.setDefaultRenderer(Object.class, tcr);//������Ⱦ��
		jTable.setModel(modle1);

		JScrollPane jScrollPane = new JScrollPane(jTable);
		jScrollPane.setPreferredSize(new Dimension(500, 300));
		jPanel3.add(jScrollPane);
		jPanel.add(jPanel3);
		// ����ģ��
		// ------------------------------------���------------------------------------------------
		addBt = new JButton("���");
		addBt.setPreferredSize(new Dimension(60, 30));
		addBt.setMnemonic(java.awt.event.KeyEvent.VK_S);// �ȼ�alt+s
		addBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				AddProjectView add = AddProjectView.getInstance(new CallBack() {

					@Override
					public void call() {
						// TODO Auto-generated method stub
						flush();
						int rowCount = jTable.getRowCount(); // �������
						jTable.getSelectionModel().setSelectionInterval(rowCount - 1, rowCount - 1);// ѡ�б������һ�С�
						Rectangle rect = jTable.getCellRect(rowCount - 1, 0, true); // ��ȡ���һ����ʾ�����������Ϣ��
						jTable.scrollRectToVisible(rect);
					}
				});
				add.openFrame();
			}
		});

		// ---------------------------------------------�޸�----------------------------------------------------------
		updataBt = new JButton("�޸�");
		updataBt.setPreferredSize(new Dimension(60, 30));
		updataBt.setMnemonic(java.awt.event.KeyEvent.VK_A);
		updataBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIndex = jTable.getSelectedRow();
				int selectId = listProject.get(selectIndex).getId();
				if (selectIndex == -1) {
					JOptionPane.showMessageDialog(frame, "����ѡ��Ա����", "����", JOptionPane.WARNING_MESSAGE);
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

		// -------------------------------ɾ��---------------------------------------------
		deleteBt = new JButton("ɾ��");
		deleteBt.setPreferredSize(new Dimension(60, 30));
		deleteBt.setMnemonic(java.awt.event.KeyEvent.VK_D);
		deleteBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				 int selectIndex = jTable.getSelectedRow();
//				 if (selectIndex == -1) {
//				 JOptionPane.showMessageDialog(frame, "����ѡ��Ա����", "����",
//				 JOptionPane.WARNING_MESSAGE);
//				 }else {
//				
//				 boolean flag = employeeDao.deleteEmployee(listProject.get(selectIndex).getId());
//				 if (flag) {
//				 JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
//				 }
//				 else {
//				 JOptionPane.showMessageDialog(null, "ɾ��ʧ�ܣ�");
//				 }
//				 }
//				 flush();
				deleteBach();
				flush();
			}
		});

		flushBt = new JButton("��Ŀ����");
		flushBt.setPreferredSize(new Dimension(100, 30));
		flushBt.setMnemonic(java.awt.event.KeyEvent.VK_F);
		flushBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int selectIndex = jTable.getSelectedRow();
				if (selectIndex == -1) {
					JOptionPane.showMessageDialog(frame, "����ѡ�в��ţ�", "����", JOptionPane.WARNING_MESSAGE);
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

	// ����������������������������ˢ�¡���������������������������������������������

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
				JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
			} else {
				JOptionPane.showMessageDialog(null, "ɾ��ʧ�ܣ�");
			}
		}

		else {
			JOptionPane.showMessageDialog(null, "��ѡ����Ϣ");
		}

	}

	// ��������������������������������������������������
//	public static void main(String[] args) {
//		new ProjectView().init();
//	}
}