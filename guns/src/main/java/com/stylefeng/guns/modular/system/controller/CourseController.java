package com.stylefeng.guns.modular.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.persistence.dao.CourseMapper;
import com.stylefeng.guns.modular.system.dao.CourseDao;
import com.stylefeng.guns.modular.system.warpper.CourseWarpper;

/**
 * course控制器
 *
 * @author fengshuonan
 * @Date 2017-07-12 15:24:25
 */
@Controller
@RequestMapping("/course")
public class CourseController extends BaseController {

    private String PREFIX = "/system/course/";
    @Resource
    private CourseDao courseDao;
    @Resource
    CourseMapper courseMapper;
    /**
     * 跳转到course首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "course.html";
    }

    /**
     * 跳转到添加course
     */
    @RequestMapping("/course_add")
    public String courseAdd() {
        return PREFIX + "course_add.html";
    }

    /**
     * 跳转到修改course
     */
    @RequestMapping("/course_update/{courseId}")
    public String courseUpdate(@PathVariable Integer courseId, Model model) {
        return PREFIX + "course_edit.html";
    }

    /**
     * 获取course列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	 List<Map<String, Object>> list = this.courseDao.list(condition);
         return super.warpObject(new CourseWarpper(list));
    }

    /**
     * 新增course
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add() {
        return super.SUCCESS_TIP;
    }

    /**
     * 删除course
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改course
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * course详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
