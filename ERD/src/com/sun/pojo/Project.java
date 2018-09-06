package com.sun.pojo;
/**
* @author 作者:Chaoguo Sun
* @createDate 创建时间：2018年8月8日 下午12:28:47
*/
public class Project {
	private int id;
	private String name;
	
	public Project() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Project(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
