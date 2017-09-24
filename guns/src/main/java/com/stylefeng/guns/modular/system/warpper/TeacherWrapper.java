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
	        map.put("countryName", ConstantFactory.me().getCountryName((Integer) map.get("country")));
	        map.put("teacherName", ConstantFactory.me().getUserNameById((Integer) map.get("userid")));
	        map.put("languageName", ConstantFactory.me().getDictsByName("语言",(Integer) map.get("language")));
	       
	        map.put("isshowName", ConstantFactory.me().getIsShowName((Integer) map.get("isshow")));
		
	}

}
