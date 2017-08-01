package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * teacherDao
 *
 * @author fengshuonan
 * @Date 2017-07-20 21:57:31
 */
public interface TeacherDao {
	List<Map<String, Object>> list(@Param("condition") String condition);


}
