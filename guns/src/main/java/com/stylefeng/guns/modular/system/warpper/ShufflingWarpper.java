package com.stylefeng.guns.modular.system.warpper;

import java.util.Map;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.warpper.BaseControllerWarpper;
import com.stylefeng.guns.core.util.ToolUtil;

/**
 * 部门列表的包装
 *
 * @author fengshuonan
 * @date 2017年4月25日 18:10:31
 */
public class ShufflingWarpper extends BaseControllerWarpper {

    public ShufflingWarpper(Object list) {
        super(list);
    }

    @Override
    public void warpTheMap(Map<String, Object> map) {
    		String path = (String) map.get("path");
    		if (ToolUtil.isEmpty(path) || path.equals("")) {
                map.put("path", path);
            } else {
            	String img = "<img style='width:160px;height:160px;' src='"+path+"'/>";
                map.put("path", img);
            }
    }

}
