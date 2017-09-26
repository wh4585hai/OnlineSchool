package com.stylefeng.guns.modular.system.warpper;

import java.util.Map;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.warpper.BaseControllerWarpper;

public class ClassScheduleWrapper extends BaseControllerWarpper{

	public ClassScheduleWrapper(Object list) {
		super(list);
	
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {

		  //Integer creater = (Integer) map.get("userId");
	        

	        map.put("studentName", ConstantFactory.me().getStudentName((Integer) map.get("studentid")));
	        map.put("teacherName", ConstantFactory.me().getUserNameById((Integer) map.get("teacherid")));
	        map.put("meterialName", ConstantFactory.me().getUserNameById((Integer) map.get("materialid")));
	        map.put("statusName", ConstantFactory.me().getDictsByName("课程状态",(Integer) map.get("status")));
	        map.put("isdelayName", ConstantFactory.me().getDictsByName("是否延期",(Integer) map.get("isdelay")));
	        map.put("classapproach",ConstantFactory.me().getDictsByName("上课方式",ConstantFactory.me().getClassApproach((Integer) map.get("orderid"))));
	        map.put("classnumber", ConstantFactory.me().getClassNumber((Integer) map.get("orderid")));

	       
	}
}
