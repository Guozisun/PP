package com.sun.view.score;
/**
* @author 作者:Chaoguo Sun
* @createDate 创建时间：2018年8月7日 下午2:23:55
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

	String[] valus = { "ID","员工名称", "部门名称", "项目名称", "成绩", "等级" };

	@Override
	public int getRowCount() {

		return listScore.size();// 行数
	}

	@Override
	public int getColumnCount() {

		return valus.length;
		// 列数
	}

	// 可修改
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
