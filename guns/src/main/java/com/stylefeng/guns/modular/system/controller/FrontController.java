package com.stylefeng.guns.modular.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.dao.ShufflingMapper;
import com.stylefeng.guns.common.persistence.model.Shuffling;
import com.stylefeng.guns.modular.system.dao.ShufflingDao;

@Controller
@RequestMapping("/front")
public class FrontController extends BaseController{
	
	@Resource
    private ShufflingDao shufflingDao;
	
	@Resource
    private ShufflingMapper shufflingMapper;
	
	private String PREFIX = "/front/";
	
	 @RequestMapping("")
	    public String index(Model model) {
		 List<Shuffling> shuffling_list = shufflingDao.listforFront();
		 super.setAttr("shuffling_list",shuffling_list);
	        return PREFIX + "index.html";
	    }
	 @RequestMapping("/acivityDetail/{shufflingId}")
	    public String acivityDetail(@PathVariable("shufflingId") Integer shufflingId) {
		 Shuffling shuffling=shufflingMapper.selectById(shufflingId);
		 super.setAttr("shuffling",shuffling);
	      return PREFIX + "acivityDetail.html";
	    }
}
