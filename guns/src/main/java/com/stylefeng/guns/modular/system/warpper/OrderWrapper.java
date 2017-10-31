package com.stylefeng.guns.modular.system.warpper;

import java.util.Date;
import java.util.Map;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.DateUtil;

public class OrderWrapper extends BaseControllerWarpper{

	public OrderWrapper(Object list) {
		super(list);
	}

	@Override
	protected void warpTheMap(Map<String, Object> map) {

        map.put("studentName", ConstantFactory.me().getStudentName((Integer) map.get("studentid")));
        map.put("meterialName", ConstantFactory.me().getMeterialName((Integer) map.get("materialid")));
        map.put("courseName", ConstantFactory.me().getCourselName((Integer) map.get("courseid")));
        map.put("statusName", ConstantFactory.me().getDictsByName("支付状态",(Integer) map.get("status"))); 
        map.put("courseTimeName", ConstantFactory.me().getDictsByName("课时价格",(Integer) map.get("coursetime")));
        map.put("classapproachName",ConstantFactory.me().getDictsByName("上课方式",(Integer) map.get("classapproach")));
        map.put("sexName", ConstantFactory.me().getDictsByName("性别",(Integer) map.get("sex")));
        map.put("formatecreatedate",DateUtil.getDay((Date) map.get("createdate")));
        map.put("formatedate",DateUtil.getDay((Date) map.get("date")));
        map.put("formatebirthday", DateUtil.getDay((Date) map.get("birthday"))); 


		
	}

}
