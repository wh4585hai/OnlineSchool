package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;

public class Material  extends Model<Material> {
	 private static final long serialVersionUID = 1L;

	    /**
	     * 主键id
	     */
		@TableId(value="id", type= IdType.AUTO)
		private Integer id;
		/**
	     * 排序
	     */
		private Integer num;
	    /**
	     * 简称
	     */
		private String name;
		
	    /**
	     * 全称
	     */
		@TableField("img_path")
		private String imgPath;
		@TableField("pdf_path")
		private String pdfPath;
		
		private String summary;
		private String description;
		private String content;

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

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Integer getNum() {
			return num;
		}

		public void setNum(Integer num) {
			this.num = num;
		}


		public String getImgPath() {
			return imgPath;
		}

		public void setImgPath(String imgPath) {
			this.imgPath = imgPath;
		}

		public String getPdfPath() {
			return pdfPath;
		}

		public void setPdfPath(String pdfPath) {
			this.pdfPath = pdfPath;
		}

		public String getSummary() {
			return summary;
		}

		public void setSummary(String summary) {
			this.summary = summary;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		
}
