package com.sun.pojo;
/**
* @author ����:Chaoguo Sun
* @createDate ����ʱ�䣺2018��8��13�� ����9:20:38
*/
public class Grade {
	private int gid;
	private String gname;
	
	public Grade() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Grade(int gid, String gname) {
		super();
		this.gid = gid;
		this.gname = gname;
	}

	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getGname() {
		return gname;
	}
	public void setGname(String gname) {
		this.gname = gname;
	}
	

}
