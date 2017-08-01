package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class Teacher extends Model<Teacher>{
	private static final long serialVersionUID = 1L;
	
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	
	@TableField("user_id")
	private Integer userId;
	
	private String name;
	
	private int country;
	
	private int language;
	
	private String picture;
	
	private int age; 
	
	private String sex;
	
	private String introduction;
	
	private String phone;
	
	private String email;
	
	private int isshow;
	
	private String qq;
	
	private String skype;
	
	private String salt;
	private String password;
	
	private String account;
	
	@TableField("createdate")
	private Date createDate;
	
	@TableField("updatedate")
	private Date updateDate;
	
	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public Integer getUserId() {
		return userId;
	}



	public void setUserId(Integer userId) {
		this.userId = userId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public int getCountry() {
		return country;
	}



	public void setCountry(int country) {
		this.country = country;
	}



	public int getLanguage() {
		return language;
	}



	public void setLanguage(int language) {
		this.language = language;
	}



	public String getPicture() {
		return picture;
	}



	public void setPicture(String picture) {
		this.picture = picture;
	}



	public int getAge() {
		return age;
	}



	public void setAge(int age) {
		this.age = age;
	}



	public String getIntroduction() {
		return introduction;
	}



	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public int getIsshow() {
		return isshow;
	}



	public void setIsshow(int isshow) {
		this.isshow = isshow;
	}



	public String getQq() {
		return qq;
	}



	public void setQq(String qq) {
		this.qq = qq;
	}



	public String getSkype() {
		return skype;
	}



	public void setSkype(String skype) {
		this.skype = skype;
	}



	public Date getCreateDate() {
		return createDate;
	}



	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}



	public Date getUpdateDate() {
		return updateDate;
	}



	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}



	public String getSalt() {
		return salt;
	}



	public void setSalt(String salt) {
		this.salt = salt;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getAccount() {
		return account;
	}



	public void setAccount(String account) {
		this.account = account;
	}



	public String getSex() {
		return sex;
	}



	public void setSex(String sex) {
		this.sex = sex;
	}



	@Override
	protected Serializable pkVal() {		
		return this.id;
	}
	

}
