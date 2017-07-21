package com.stylefeng.guns.modular.system.warpper;

import java.util.Map;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.ToolUtil;

public class MaterialWarpper extends BaseControllerWarpper{
	 public MaterialWarpper(Object list) {
	        super(list);
	    }
	@Override
	protected void warpTheMap(Map<String, Object> map) {
		// TODO Auto-generated method stub
		String path = (String) map.get("path");
		if (ToolUtil.isEmpty(path) || path.equals("")) {
            map.put("path", path);
        } else {
        	String url = "http://localhost:8080/guns/"+path;
        	String pdf = "<a target='_blank' href='/generic/web/viewer.html?file="+url+"'>点击查看PDF</a>";
            map.put("path", pdf);
        }
	}

}
