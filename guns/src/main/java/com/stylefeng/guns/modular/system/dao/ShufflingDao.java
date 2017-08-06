package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.stylefeng.guns.common.persistence.model.Shuffling;

/**
 * shufflingDao
 *
 * @author fengshuonan
 * @Date 2017-07-10 14:37:24
 */
public interface ShufflingDao {

	List<Map<String, Object>> list(@Param("condition") String condition);
	
	List<Shuffling> listforFront();
}
