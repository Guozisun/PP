package com.sun.pojo;
/**
* @author 作者:Chaoguo Sun
* @createDate 创建时间：2018年8月7日 上午10:21:24
*/
public class Dept {
	private int id;
	private String name;
	private int e_count;
	private Project project;
	
	

	public Dept() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Dept(int id, String name, int e_count) {
		super();
		this.id = id;
		this.name = name;
		this.e_count = e_count;
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
	public int getE_count() {
		return e_count;
	}
	public void setE_count(int e_count) {
		this.e_count = e_count;
	}
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	

}
