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
		String pdf_path = (String) map.get("pdf_path");
		if (ToolUtil.isEmpty(pdf_path) || pdf_path.equals("")) {
            map.put("pdf_path", pdf_path);
        } else {
        	String url = "http://47.94.96.156/onlineSchool-1.5.3.RELEASE/"+pdf_path;
        	String pdf = "<a target='_blank' href='/generic/web/viewer.html?file="+url+"'>点击查看PDF</a>";
            map.put("pdf_path", pdf);
        }
		String img_path = (String) map.get("img_path");
		if (ToolUtil.isEmpty(img_path) || img_path.equals("")) {
            map.put("img_path", img_path);
        } else {
        	String img = "<img style='width:160px;height:160px;' src='"+img_path+"'/>";
            map.put("img_path", img);
        }
	}

}
