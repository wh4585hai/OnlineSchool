package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.stylefeng.guns.common.persistence.model.Student;

/**
 * studentDao
 *
 * @author fengshuonan
 * @Date 2017-08-22 14:00:04
 */
public interface StudentDao {

	/**
	 * 通过账号获取用户
	 *
	 * @param account
	 * @return
	 * @date 2017年2月17日 下午11:07:46
	 */
	Student getByAccount(@Param("account") String account);
	 List<Map<String, Object>> list(@Param("condition") String condition);
}
