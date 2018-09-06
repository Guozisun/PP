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
 * @author ����:Chaoguo Sun
 * @createDate ����ʱ�䣺2018��8��7�� ����3:34:35
 */
public class UpdateDept extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel jPanel;// �����
	BoxLayout boxLayout;// ���ӿؼ�
	JLabel nameLable, numLable;// lable�ؼ�
	JTextField nameField, numField;// �ı��ĵ�
	JButton saveBt;// ��ť
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
		numField = new JTextField();
		numField.setPreferredSize(new Dimension(90, 30));
		JPanel jPanel3 = new JPanel();
		jPanel3.add(numLable);
		jPanel3.add(numField);
		nameField.setText(selectDept.getName());
		numField.setText(String.valueOf(selectDept.getE_count()));

		saveBt = new JButton("����");
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
		jPanel.add(jPanel6);
		frame.setVisible(true);

	}

}
