package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;

public class Orders extends Model<Orders>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private int studentid;
	
	private int teacherid;
	
	private int materialid;
	
	private int months;
	
	private String coursetime;
	
	private int timesperweek;
	
	private String amount;
	
	private int subjectid;
	
	@TableField("createdate")
	private Date createDate;
	
	private int status;
	
	private int classapproach;
	
	private String classnumber;
	
	@Override
	protected Serializable pkVal() {
		// TODO Auto-generated method stub
		return null;
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

	public int getTeacherid() {
		return teacherid;
	}

	public void setTeacherid(int teacherid) {
		this.teacherid = teacherid;
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

	public String getCoursetime() {
		return coursetime;
	}

	public void setCoursetime(String coursetime) {
		this.coursetime = coursetime;
	}

	public int getTimesperweek() {
		return timesperweek;
	}

	public void setTimesperweek(int timesperweek) {
		this.timesperweek = timesperweek;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public int getSubjectid() {
		return subjectid;
	}

	public void setSubjectid(int subjectid) {
		this.subjectid = subjectid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getClassapproach() {
		return classapproach;
	}

	public void setClassapproach(int classapproach) {
		this.classapproach = classapproach;
	}

	public String getClassnumber() {
		return classnumber;
	}

	public void setClassnumber(String classnumber) {
		this.classnumber = classnumber;
	}
	
	

}
