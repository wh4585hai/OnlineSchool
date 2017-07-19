package com.stylefeng.guns.common.controller;

import java.io.File;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Dict;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.config.properties.GunsProperties;

/**
 * 全局的控制器
 *
 * @author fengshuonan
 * @date 2016年11月13日 下午11:04:45
 */
@Controller
@RequestMapping("/global")
public class GlobalController {
	public final static String PATH = "/data/documents/files/";
	@Resource
	private GunsProperties gunsProperties;

	/**
	 * 跳转到404页面
	 *
	 * @author fengshuonan
	 */
	@RequestMapping(path = "/error")
	public String errorPage() {
		return "/404.html";
	}

	/**
	 * 跳转到session超时页面
	 *
	 * @author fengshuonan
	 */
	@RequestMapping(path = "/sessionError")
	public String errorPageInfo(Model model) {
		model.addAttribute("tips", "session超时");
		return "/login.html";
	}
}
