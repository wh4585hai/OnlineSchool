package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 课程表Dao
 *
 * @author caojianchao
 * @Date 2017-09-04 22:01:21
 */
public interface ClassscheduleDao {

	List<Map<String, Object>> list(@Param("studentname") String studentname, @Param("teachername") Integer teachername, @Param("classdata") String classdata);
}
