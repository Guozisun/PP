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
import com.sun.dao.EmployeeDao;
import com.sun.pojo.CallBack;
import com.sun.pojo.Dept;
import com.sun.pojo.Employee;

/**
 * @author ����:Chaoguo Sun
 * @createDate ����ʱ�䣺2018��8��7�� ����3:34:35
 */
public class AddDeptView extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// �����
	BoxLayout boxLayout;// ���ӿؼ�
	JLabel nameLable, numLable;// lable�ؼ�
	JTextField nameField,numField;// �ı��ĵ�
	JButton saveBt;// ��ť
	List<Dept> listDept = new ArrayList<>();
	DeptDao deptDao = new DeptDao();
	List<Employee> list = new ArrayList<>();
	EmployeeDao employeeDao = new EmployeeDao();
	CallBack callBack;
	JFrame frame;

	private static AddDeptView instance;

	public static AddDeptView getInstance(CallBack callBack) {
		if (instance == null) {
			instance = new AddDeptView(callBack);
		}
		return instance;
	}

	public void fun() {
		nameField.setText("");
		numField.setText("0");
	}

	public void openFrame() {
		if (frame == null) {
			init();
		} else {
			fun();
			frame.setVisible(true);
		}
	}

	public AddDeptView(CallBack callBack) {

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

		numLable = new JLabel("Ա������");
		numLable.setPreferredSize(new Dimension(70, 30));
		numField = new JTextField("Ĭ��Ϊ0");
		numField.setPreferredSize(new Dimension(90, 30));
		JPanel jPanel3 = new JPanel();
		jPanel3.add(numLable);
		jPanel3.add(numField);
		saveBt = new JButton("����");
		saveBt.setPreferredSize(new Dimension(60, 30));
		saveBt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (nameField.getText().equals("") && numField.getText().equals("") ) {
					JOptionPane.showMessageDialog(null, "������������Ϣ��");

				} else {
					numField.setText("��ֹ���룬Ĭ��Ϊ0");
					String name = nameField.getText();
					Dept dept = new Dept();
					dept.setName(name);
					dept.setE_count(0);
					

					boolean flag = deptDao.addDept(dept);
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
		jPanel.add(jPanel3);
		
		jPanel.add(jPanel6);
		frame.setVisible(true);
		callBack.call();
	}

}
