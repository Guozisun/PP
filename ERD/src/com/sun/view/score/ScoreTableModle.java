package com.sun.view.score;
/**
* @author ����:Chaoguo Sun
* @createDate ����ʱ�䣺2018��8��7�� ����2:23:55
*/

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.table.AbstractTableModel;

import com.sun.pojo.Score;

public class ScoreTableModle extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	List<Score> listScore;
	Set<Score> listScore1 = new HashSet<>();

	public ScoreTableModle(List<Score> listScore) {

		this.listScore = listScore;
	}

	String[] valus = { "ID","Ա������", "��������", "��Ŀ����", "�ɼ�", "�ȼ�" };

	@Override
	public int getRowCount() {

		return listScore.size();// ����
	}

	@Override
	public int getColumnCount() {

		return valus.length;
		// ����
	}

	// ���޸�
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		if (columnIndex == 4) {
			if (!listScore.get(rowIndex).getProject().getName().equals(" ")) {
				return true;
			}
			
		
		}

		
			return false;
	

	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		listScore.get(rowIndex).setValue(Integer.parseInt((String) aValue));
		listScore1.add(listScore.get(rowIndex));

	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return listScore.get(rowIndex).getId();
		}
		if (columnIndex == 1) {
			return listScore.get(rowIndex).getEmployee().getName();
		}
		if (columnIndex == 2) {
			return listScore.get(rowIndex).getEmployee().getDept().getName();
		}
		if (columnIndex == 3) {
			return listScore.get(rowIndex).getProject().getName();
		}
		if (columnIndex == 4) {
			return listScore.get(rowIndex).getValue();
		}
		if (columnIndex == 5) {

			return listScore.get(rowIndex).getGrade();
		} else {
			return "";
		}
	}

	public String getColumnName(int column) {
		return valus[column];
	}

	public Set<Score> getListScore() {
		return listScore1;
	}

	public void setListScore(List<Score> listScore) {
		this.listScore = listScore;
	}

}
