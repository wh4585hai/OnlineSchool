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
	
	private int coursetime;
	
	private int materialid; 
	
	private String file;
	
	private int status;
	
	private int isdelay;
	
	private String delayreason;
	
	private String remark;
	
	private int orderid;
	
	private String comment;
	
	private int teachercharge;

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


	public int getStudentid() {
		return studentid;
	}


	public void setStudentid(int studentid) {
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




	public int getCoursetime() {
		return coursetime;
	}


	public void setCoursetime(int coursetime) {
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


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public int getOrderid() {
		return orderid;
	}


	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public int getTeachercharge() {
		return teachercharge;
	}


	public void setTeachercharge(int teachercharge) {
		this.teachercharge = teachercharge;
	}


	


	

	
	
}
