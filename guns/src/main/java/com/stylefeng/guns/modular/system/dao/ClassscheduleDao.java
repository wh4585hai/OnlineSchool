package com.stylefeng.guns.modular.system.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.persistence.model.ClassSchedule;
import com.stylefeng.guns.common.persistence.model.OperationLog;

/**
 * 课程表Dao
 *
 * @author caojianchao
 * @Date 2017-09-04 22:01:21
 */
public interface ClassscheduleDao {
	int deleteBathById(String[] ids);
	List<Map<String, Object>> list(@Param("page") Page<ClassSchedule> page,@Param("studentname") String studentname, @Param("teachername") Integer teachername, @Param("datefrom") String datefrom,@Param("dateto") String dateto);
	List<Map<String, Object>> listforcount(@Param("teachername") Integer teachername, @Param("datefrom") String datefrom,@Param("dateto") String dateto);
	List<Map<String, Object>> listForStudent(@Param("studentid") Integer studentid, @Param("datefrom") String datefrom,@Param("dateto") String dateto);
}
