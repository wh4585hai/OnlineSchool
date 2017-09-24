package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.stylefeng.guns.common.persistence.model.Course;
import com.stylefeng.guns.common.persistence.model.Material;

/**
 * courseDao
 *
 * @author fengshuonan
 * @Date 2017-07-12 15:24:25
 */
public interface CourseDao {
	List<Map<String, Object>> list(@Param("condition") String condition);
	List<Course> listForFront();
	List<Course> listForAll();
}
