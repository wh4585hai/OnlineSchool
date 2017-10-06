package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 订单管理Dao
 *
 * @author fengshuonan
 * @Date 2017-09-20 20:11:35
 */
public interface OrdermanageDao {
	List<Map<String, Object>> list(@Param("studentid") String studentid,  @Param("createDate") String createDate);


}
