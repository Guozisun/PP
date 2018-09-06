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
 * @author ����:Chaoguo Sun
 * @createDate ����ʱ�䣺2018��8��7�� ����2:01:59
 */
public class ProjectView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// �����
	BoxLayout boxLayout;// ���ӿؼ�
	JLabel nameLable;// lable�ؼ�
	JTextField nameField;// �ı��ĵ�
	JButton addBt, deleteBt;// ��ť
	JTable jTable;// ���
	ProjectTableModle modle1;// ���ģ��d
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

		// -------------------- java swing ��� -----------------------
		JFrame frame = new JFrame("��Ŀ����ϵͳ");
		frame.setSize(500, 400);// ���ÿ�Ⱥ͸߶�
		frame.setLocation(800, 200);
//		frame.setLocationRelativeTo(null);// ����
		frame.setResizable(false);// ���ɷŴ�
	
		jPanel = (JPanel) frame.getContentPane();// ��������
		boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);// y��ֿ�
		jPanel.setLayout(boxLayout);
		// ��һ���

		nameLable = new JLabel(dept.getName());
		nameLable.setPreferredSize(new Dimension(70, 30));
		
		
		
		// ģ���Ϊ������������
		// ��һ����������
		// �ڶ�����table
		// �������ְ�ť
		JPanel jPanel2 = new JPanel();
		JPanel jPanel3 = new JPanel();
		JPanel jPanel4 = new JPanel();

		jPanel2.add(nameLable);
	
	

	
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		jPanel.add(jPanel2);
		// ����Ϣ��ʾ����
		listProject = projectDao1.selectAll(dept.getId());
		modle1 = new ProjectTableModle(listProject);
		jTable = new JTable();
		jTable.setModel(modle1);

		JScrollPane jScrollPane = new JScrollPane(jTable);
		jScrollPane.setPreferredSize(new Dimension(400, 200));
		jPanel3.add(jScrollPane);
		jPanel.add(jPanel3);
		// ����ģ��
		// ------------------------------------���------------------------------------------------
		comboBox = new JComboBox<>();
		listProject1 = projectDao1.selectNotIn(dept.getId());
		for (int i = 0; i < listProject1.size(); i++) {
			comboBox.addItem(listProject1.get(i).getName());
		}
		addBt = new JButton("���");
		addBt.setPreferredSize(new Dimension(60, 30));
		addBt.setMnemonic(java.awt.event.KeyEvent.VK_S);// �ȼ�alt+s
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
						JOptionPane.showMessageDialog(null, "����ɹ���");
					} else {
						JOptionPane.showMessageDialog(null, "����ʧ�ܣ�");
					}
					
					flush();
				}
				else {
					JOptionPane.showMessageDialog(null, "��Ŀ��ѡ��");
				}
				
				
			}
			
		});

		// ---------------------------------------------�޸�----------------------------------------------------------
		
		// -------------------------------ɾ��---------------------------------------------
		deleteBt = new JButton("ɾ��");
		deleteBt.setPreferredSize(new Dimension(60, 30));
		deleteBt.setMnemonic(java.awt.event.KeyEvent.VK_D);
		deleteBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				 int selectIndex = jTable.getSelectedRow();
				 if (selectIndex == -1) {
				 JOptionPane.showMessageDialog(frame, "����ѡ��Ա����", "����",
				 JOptionPane.WARNING_MESSAGE);
				 }else {
				
				 boolean flag = projectDao1.deleteProject(dept.getId(), listProject.get(selectIndex).getId());
				 if (flag) {
				 JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
				 }
				 else {
				 JOptionPane.showMessageDialog(null, "ɾ��ʧ�ܣ�");
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

	// ����������������������������ˢ�¡���������������������������������������������

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
	
}