package com.stylefeng.guns.common.constant.dictmap;

import com.stylefeng.guns.common.constant.dictmap.base.AbstractDictMap;

public class MaterialMap extends AbstractDictMap {

    @Override
    public void init() {
        put("name", "教材名称");
        put("path", "教材路径");
    }

    @Override
    protected void initBeWrapped() {
    }
}
