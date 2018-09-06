package com.sun.view.employee;

import java.awt.Dimension;

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

import javax.swing.JTextField;

import com.sun.dao.DeptDao;
import com.sun.dao.EmployeeDao;
import com.sun.pojo.CallBack;
import com.sun.pojo.Dept;
import com.sun.pojo.Employee;


/**
 * @author ����:Chaoguo Sun
 * @createDate ����ʱ�䣺2018��8��7�� ����3:34:35
 */
public class UpdateEmployee extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// �����
	BoxLayout boxLayout;// ���ӿؼ�
	JLabel nameLable, sexLable, ageLable, deptLable;// lable�ؼ�
	JTextField nameField, ageField;// �ı��ĵ�
	JButton saveBt;// ��ť
	JComboBox<Object> box;
	JComboBox<String> box1;
	List<Dept> listDept = new ArrayList<>();
	DeptDao deptDao = new DeptDao();
	List<Employee> list = new ArrayList<>();
	EmployeeDao employeeDao = new EmployeeDao();
	CallBack callBack;
	JFrame frame;
	Employee selectEmp;
	private int selectId;
	

	public int getSelectId() {
		return selectId;
	}

	public void setSelectId(int selectId) {
		this.selectId = selectId;
	}

	private static UpdateEmployee instance;

	public static UpdateEmployee getInstance(Employee selectEmp, CallBack callBack) {
		if (instance == null) {
			instance = new UpdateEmployee(selectEmp, callBack);
		}
		return instance;
	}

	public void fun() {
		Employee employee = employeeDao.selectOne(selectId);
		nameField.setText(employee.getName());
		// sexField.setText(selectEmp.getSex());
		box1.setSelectedItem(employee.getSex());
		ageField.setText(String.valueOf(employee.getAge()));
		box.setSelectedItem(employee.getDept().getName());
		
	}

	public void openFrame() {
		if (frame == null) {
			init();
		} else {
			fun();
			frame.setVisible(true);
		}
	}

	public UpdateEmployee(Employee selectEmp, CallBack callBack) {
		this.selectEmp = selectEmp;
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

		nameLable = new JLabel("����:");
		nameLable.setPreferredSize(new Dimension(30, 30));
		nameField = new JTextField();
		nameField.setPreferredSize(new Dimension(60, 30));
		JPanel jPanel2 = new JPanel();
		jPanel2.add(nameLable);
		jPanel2.add(nameField);

		sexLable = new JLabel("�Ա�:");
		sexLable.setPreferredSize(new Dimension(30, 30));
		box1 = new JComboBox<String>();
		box1.addItem("��");
		box1.addItem("Ů");
		box1.setPreferredSize(new Dimension(60, 30));
		JPanel jPanel3 = new JPanel();
		jPanel3.add(sexLable);
		jPanel3.add(box1);

		ageLable = new JLabel("����:");
		ageLable.setPreferredSize(new Dimension(30, 30));
		ageField = new JTextField();
		ageField.setPreferredSize(new Dimension(60, 30));
		JPanel jPanel4 = new JPanel();
		jPanel4.add(ageLable);
		jPanel4.add(ageField);

		deptLable = new JLabel("����:");
		deptLable.setPreferredSize(new Dimension(30, 30));
		box = new JComboBox<Object>();
		box.addItem("��ѡ����");
		listDept = deptDao.selectAll();
		for (int i = 0; i < listDept.size(); i++) {
			box.addItem(listDept.get(i).getName());
		}
		JPanel jPanel5 = new JPanel();
		jPanel5.add(deptLable);
		jPanel5.add(box);

		nameField.setText(selectEmp.getName());
		box1.setSelectedItem(selectEmp.getSex());
		ageField.setText(String.valueOf(selectEmp.getAge()));
		box.setSelectedItem(selectEmp.getDept().getName());

		saveBt = new JButton("����");
		saveBt.setPreferredSize(new Dimension(60, 30));
		saveBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = nameField.getText();
				int sexIndex = box1.getSelectedIndex();
				int age = -1;
				try {
					age = Integer.parseInt(ageField.getText());
				} catch (Exception e2) {

				}
				int index = box.getSelectedIndex();
				selectEmp.setName(name);
				selectEmp.setSex(box1.getItemAt(sexIndex));
				selectEmp.setAge(age);
				selectEmp.setD_id(listDept.get(index - 1).getId());

				boolean flag = employeeDao.updateEmployees(selectEmp);
				if (flag) {
					JOptionPane.showMessageDialog(null, "����ɹ���");
				} else {
					JOptionPane.showMessageDialog(null, "����ʧ�ܣ�");
				}
				frame.dispose();
				callBack.call();
			}
		});
		JPanel jPanel6 = new JPanel();
		jPanel6.add(saveBt);

		jPanel.add(jPanel2);
		jPanel.add(jPanel3);
		jPanel.add(jPanel4);
		jPanel.add(jPanel5);
		jPanel.add(jPanel6);
		frame.setVisible(true);

	}

}
