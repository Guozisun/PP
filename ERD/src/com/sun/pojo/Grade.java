package com.sun.pojo;
/**
* @author 作者:Chaoguo Sun
* @createDate 创建时间：2018年8月13日 上午9:20:38
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
