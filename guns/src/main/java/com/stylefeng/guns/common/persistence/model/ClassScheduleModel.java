package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.activerecord.Model;

public class ClassScheduleModel extends Model<ClassScheduleModel>{
	
	private static final long serialVersionUID = 1L;


	
	private int studentid;
	
	private int teacherid;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	private String starttime;
	
	private int coursetime;
	
	private int materialid; 
	
	private int months;
		
	private String weeks;

	private int orderid;
	
	private int teachercharge;
	
	@Override
	protected Serializable pkVal() {
		return this.studentid;
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


	public int getMaterialid() {
		return materialid;
	}

	public void setMaterialid(int materialid) {
		this.materialid = materialid;
	}



	public int getMonths() {
		return months;
	}

	public void setMonths(int months) {
		this.months = months;
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

	public String getWeeks() {
		return weeks;
	}

	public void setWeeks(String weeks) {
		this.weeks = weeks;
	}

	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}



	public int getCoursetime() {
		return coursetime;
	}

	public void setCoursetime(int coursetime) {
		this.coursetime = coursetime;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getTeachercharge() {
		return teachercharge;
	}

	public void setTeachercharge(int teachercharge) {
		this.teachercharge = teachercharge;
	}



	
	

}
