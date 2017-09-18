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

		  Integer creater = (Integer) map.get("userId");
	        map.put("classtimeList", ConstantFactory.me().getDictList("课时价格"));
	       // map.put("sexName", ConstantFactory.me().getSexName((Integer) map.get("sex")));
	       // map.put("countryName", ConstantFactory.me().getCountryName((Integer) map.get("country")));
	       // map.put("isshowName", ConstantFactory.me().getIsShowName((Integer) map.get("isshow")));
		
	}

}
