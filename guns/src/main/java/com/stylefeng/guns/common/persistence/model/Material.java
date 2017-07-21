package com.stylefeng.guns.common.persistence.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
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
	     * 简称
	     */
		private String name;
		
	    /**
	     * 全称
	     */
		private String path;

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

		public String getPath() {
			return path;
		}

		public void setPath(String path) {
			this.path = path;
		}
		
}
