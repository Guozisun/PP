package com.sun.view.dept1;
/**
* @author ����:Chaoguo Sun
* @createDate ����ʱ�䣺2018��8��7�� ����2:23:55
*/

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.sun.pojo.Dept;


public class DeptTableModle extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	List<Dept> list;

	public DeptTableModle(List<Dept> list) {
		this.list = list;
	}

	String[] valus = { "ID", "��������", "Ա������"};

	@Override
	public int getRowCount() {

		return list.size();// ����
	}

	@Override
	public int getColumnCount() {

		return valus.length;
		// ����
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		if (columnIndex == 0) {
			return list.get(rowIndex).getId();
		}
		if (columnIndex == 1) {
			return list.get(rowIndex).getName();
		}
		if (columnIndex == 2) {
			return list.get(rowIndex).getE_count();
		}
		 else {
			return "";
		}
	}

	public String getColumnName(int column) {
		return valus[column];
	}

	public void setList(List<Dept> list) {
		// TODO Auto-generated method stub
		this.list=list;
	}

}
