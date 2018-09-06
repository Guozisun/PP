package com.sun.view.employee;

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
import javax.swing.table.DefaultTableCellRenderer;

import com.sun.dao.DeptDao;
import com.sun.dao.EmployeeDao;
import com.sun.pojo.CallBack;
import com.sun.pojo.Dept;
import com.sun.pojo.Employee;

/**
 * @author ����:Chaoguo Sun
 * @createDate ����ʱ�䣺2018��8��7�� ����2:01:59
 */
public class EmployeeView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// �����
	BoxLayout boxLayout;// ���ӿؼ�
	JLabel nameLable, sexLable, ageLable, deptLable;// lable�ؼ�
	JTextField nameField, sexField, ageField, deptField;// �ı��ĵ�
	JButton serchBt, addBt, updataBt, deleteBt, flushBt;// ��ť
	JTable jTable;// ���
	EmpTableModle modle1;// ���ģ��
	JComboBox<Object> box;
	List<Employee> list = new ArrayList<>();
	List<Dept> listDept = new ArrayList<>();
	EmployeeDao employeeDao = new EmployeeDao();
	DeptDao deptDao = new DeptDao();

	public void init() {

		// -------------------- java swing ��� -----------------------
		JFrame frame = new JFrame("Ա������ϵͳ");
		frame.setSize(600, 500);// ���ÿ�Ⱥ͸߶�
		frame.setLocation(50, 20);
		// frame.setLocationRelativeTo(null);// ����
		frame.setResizable(false);// ���ɷŴ�

		jPanel = (JPanel) frame.getContentPane();// ��������
		boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);// y��ֿ�
		jPanel.setLayout(boxLayout);
		// ��һ���

		nameLable = new JLabel("����:");
		nameLable.setPreferredSize(new Dimension(30, 30));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(60, 30));
		sexLable = new JLabel("�Ա�:");
		sexLable.setPreferredSize(new Dimension(30, 30));
		sexField = new JTextField();
		sexField.setPreferredSize(new Dimension(60, 30));
		ageLable = new JLabel("����:");
		ageLable.setPreferredSize(new Dimension(30, 30));
		ageField = new JTextField();
		ageField.setPreferredSize(new Dimension(60, 30));
		deptLable = new JLabel("����:");
		deptLable.setPreferredSize(new Dimension(30, 30));
		// ������
		box = new JComboBox<Object>();
		box.addItem("��ѡ����");
		listDept = deptDao.selectAll();
		for (int i = 0; i < listDept.size(); i++) {
			box.addItem(listDept.get(i).getName());
		}
		serchBt = new JButton("����");
		serchBt.setPreferredSize(new Dimension(60, 30));
		serchBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String name = nameField.getText();
				String sex = sexField.getText();
				int age = -1;
				try {
					age = Integer.parseInt(ageField.getText());
				} catch (Exception e2) {
					e2.getStackTrace();
				}

				Employee emp = new Employee();
				emp.setName(name);
				emp.setSex(sex);
				emp.setAge(age);

				Dept dept = new Dept();
				int index = box.getSelectedIndex();
				if (index == 0) {
					dept.setId(-1);
				} else {
					dept = listDept.get(index - 1);
				}

				emp.setDept(dept);

				list = employeeDao.selectByCondition(emp);
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(null, "û�в�ѯ����Ϣ��");
					flush();
				} else {
					flush(list);
				}

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
		jPanel2.add(sexLable);
		jPanel2.add(sexField);
		jPanel2.add(ageLable);
		jPanel2.add(ageField);
		jPanel2.add(deptLable);
		jPanel2.add(box);
		jPanel2.add(serchBt);
		jPanel2.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		jPanel.add(jPanel2);
		// ����Ϣ��ʾ����
		/*
		 * 1���Ƚ���Ϣ��ʾ���� ��ѯ�����ŵ�list�� ��list�ŵ�tablemodle��
		 */
		list = employeeDao.selectAll();
		modle1 = new EmpTableModle(list);
		jTable = new JTable();
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// ��Ԫ����Ⱦ��
		tcr.setHorizontalAlignment(JLabel.CENTER);// ������ʾ

		jTable.setDefaultRenderer(Object.class, tcr);//������Ⱦ��
		jTable.setModel(modle1);
		// ��table�����������
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

				AddEmployeeView add = AddEmployeeView.getInstance(new CallBack() {

					@Override
					public void call() {

						flush();// ˢ��
						// ���֮��ѡ�������
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
				int selectId = list.get(selectIndex).getId();
				if (selectIndex == -1) {
					JOptionPane.showMessageDialog(frame, "����ѡ��Ա����", "����", JOptionPane.WARNING_MESSAGE);
				} else {
					Employee selectEmp = list.get(selectIndex);

					UpdateEmployee updateEmployee = UpdateEmployee.getInstance(selectEmp, new CallBack() {

						@Override
						public void call() {
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
				// int selectIndex = jTable.getSelectedRow();
				// if (selectIndex == -1) {
				// JOptionPane.showMessageDialog(frame, "����ѡ��Ա����", "����",
				// JOptionPane.WARNING_MESSAGE);
				// }else {
				//
				// boolean flag = employeeDao.deleteEmployee(list.get(selectIndex).getId());
				// if (flag) {
				// JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
				// }
				// else {
				// JOptionPane.showMessageDialog(null, "ɾ��ʧ�ܣ�");
				// }
				// }
				// flush();
				deleteBach();
				flush();
			}
		});

		flushBt = new JButton("ˢ��");
		flushBt.setPreferredSize(new Dimension(60, 30));
		flushBt.setMnemonic(java.awt.event.KeyEvent.VK_F);
		flushBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				flush();

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
		list.clear();
		list = employeeDao.selectAll();
		modle1.setList(list);
		jTable.updateUI();

	}

	public void flush(List<Employee> list) {

		modle1.setList(list);
		jTable.updateUI();
		jTable.clearSelection();
	}

	// ����ɾ��
	public void deleteBach() {
		//�������� ��ѡ�е�������������
		int[] index = jTable.getSelectedRows();

		if (index.length > 0) {
			//��ȡID ��Id�����ַ�����
			String deleteIds = "";
			for (int i = index.length - 1; i >= 0; i--) {
				deleteIds += list.get(index[i]).getId() + ",";

			}
			deleteIds = deleteIds.substring(0, deleteIds.length() - 1);
			boolean flag = employeeDao.deleteEmployee1(deleteIds);
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