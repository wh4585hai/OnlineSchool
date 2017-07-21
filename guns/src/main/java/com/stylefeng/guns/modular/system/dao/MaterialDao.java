package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * materialDao
 *
 * @author fengshuonan
 * @Date 2017-07-21 09:52:03
 */
public interface MaterialDao {

	List<Map<String, Object>> list(@Param("condition") String condition);
}
