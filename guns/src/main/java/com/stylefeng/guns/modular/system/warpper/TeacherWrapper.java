package com.stylefeng.guns.modular.system.warpper;

import java.util.Map;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.warpper.BaseControllerWarpper;

public class TeacherWrapper extends BaseControllerWarpper{

	public TeacherWrapper(Object list) {
		super(list);
	
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {

		  Integer creater = (Integer) map.get("userId");
	        map.put("createrName", ConstantFactory.me().getUserNameById(creater));
		
	}

}
