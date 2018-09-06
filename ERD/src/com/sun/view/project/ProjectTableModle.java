package com.sun.view.project;
/**
* @author ����:Chaoguo Sun
* @createDate ����ʱ�䣺2018��8��7�� ����2:23:55
*/

import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.sun.pojo.Project;


public class ProjectTableModle extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	List<Project> listProject;

	public ProjectTableModle(List<Project> listProject) {
		this.listProject = listProject;
	}

	String[] valus = { "ID", "��Ŀ����"};

	@Override
	public int getRowCount() {

		return listProject.size();// ����
	}

	@Override
	public int getColumnCount() {

		return valus.length;
		// ����
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		if (columnIndex == 0) {
			return listProject.get(rowIndex).getId();
		}
		if (columnIndex == 1) {
			return listProject.get(rowIndex).getName();
		}
		else {
			return "";
		}
	}

	public String getColumnName(int column) {
		return valus[column];
	}

	public void setList(List<Project> listProject) {
		// TODO Auto-generated method stub
		this.listProject=listProject;
	}

}
