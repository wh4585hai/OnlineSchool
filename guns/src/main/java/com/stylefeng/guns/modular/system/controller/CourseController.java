package com.stylefeng.guns.modular.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.HtmlUtils;

import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Dict;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.CourseMapper;
import com.stylefeng.guns.common.persistence.model.Course;
import com.stylefeng.guns.common.persistence.model.Notice;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.core.util.xss.XssHttpServletRequestWrapper;
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
    	  Course course = this.courseMapper.selectById(courseId);
          model.addAttribute("course",course);
          LogObjectHolder.me().set(course);
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
    @BussinessLog(value = "新增课程",key = "title",dict = Dict.CourseMap)
    public Object add(Course course,HttpServletRequest request) {
    	if (ToolUtil.isOneEmpty(course, course.getTitle(), course.getContent())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	String content = request.getParameter("content");
    	course.setContent(content);
    	course.setUserId(ShiroKit.getUser().getId());
    	course.setCreateTime(new Date());
    	course.insert();
        return super.SUCCESS_TIP;
    }

    /**
     * 删除course
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    @BussinessLog(value = "删除课程",key = "courseId",dict = Dict.DeleteDict)
    public Object delete(@RequestParam Integer courseId) {
    	 //缓存通知名称
        LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(courseId));

        this.courseMapper.deleteById(courseId);

        return SUCCESS_TIP;
    }


    /**
     * 修改course
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    @BussinessLog(value = "修改课程",key = "title",dict = Dict.CourseMap)
    public Object update(Course course) {
        if (ToolUtil.isOneEmpty(course, course.getId(), course.getTitle(), course.getContent())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        Course old = this.courseMapper.selectById(course.getId());
        old.setTitle(course.getTitle());
        old.setContent(course.getContent());
        old.updateById();
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
