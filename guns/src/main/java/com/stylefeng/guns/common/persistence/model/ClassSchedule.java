package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.activerecord.Model;

public class ClassSchedule extends Model<ClassSchedule>{
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	
	private int studentid;
	
	private int teacherid;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	private String starttime;
	
	private String coursetime;
	
	private int materialid; 
	
	private String file;
	
	private int status;
	
	private int isdelay;
	
	private String delayreason;
	
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getStudentid() {
		return studentid;
	}


	public void setStudentid(Integer studentid) {
		this.studentid = studentid;
	}



	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}




	public int getTeacherid() {
		return teacherid;
	}


	public void setTeacherid(int teacherid) {
		this.teacherid = teacherid;
	}


	public String getStarttime() {
		return starttime;
	}


	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}


	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}


	public String getCoursetime() {
		return coursetime;
	}


	public void setCoursetime(String coursetime) {
		this.coursetime = coursetime;
	}


	public int getMaterialid() {
		return materialid;
	}


	public void setMaterialid(int materialid) {
		this.materialid = materialid;
	}


	public String getFile() {
		return file;
	}


	public void setFile(String file) {
		this.file = file;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public int getIsdelay() {
		return isdelay;
	}


	public void setIsdelay(int isdelay) {
		this.isdelay = isdelay;
	}


	public String getDelayreason() {
		return delayreason;
	}


	public void setDelayreason(String delayreason) {
		this.delayreason = delayreason;
	}


	

	
	
}
