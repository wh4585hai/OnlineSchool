package com.stylefeng.guns.common.constant.dictmap;

import com.stylefeng.guns.common.constant.dictmap.base.AbstractDictMap;

public class ShufflingDict extends AbstractDictMap{

	@Override
	public void init() {
		// TODO Auto-generated method stub
		 put("title","标题");
	        put("shufflingId", "主键Id");
	}

	@Override
	protected void initBeWrapped() {
		// TODO Auto-generated method stub
	}

}
