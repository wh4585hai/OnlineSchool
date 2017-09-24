package com.stylefeng.guns.modular.system.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Dict;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.CourseMapper;
import com.stylefeng.guns.common.persistence.model.Course;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.FileUtil;
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
	@Resource
	private GunsProperties gunsProperties;

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
		model.addAttribute("course", course);
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
	@BussinessLog(value = "新增课程", key = "title", dict = Dict.CourseMap)
	public Object add(@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
			HttpServletRequest request) {
		Course course = new Course();
		if (imgFile != null) {
			String imgName = UUID.randomUUID().toString() + ".jpg";
			String path = "";
			try {
				String fileSavePath = gunsProperties.getFileUploadPath();
				path = fileSavePath + "course/" + imgName;
				imgFile.transferTo(new File(path));
			} catch (Exception e) {
				e.printStackTrace();
				throw new BussinessException(BizExceptionEnum.UPLOAD_FILE_ERROR);
			}
			path = "course/" + imgName;
			course.setImgPath(path);
		}
		String name = request.getParameter("name");
		String num = request.getParameter("num");
		String description = request.getParameter("description");
		String summary = request.getParameter("summary");
		String content = request.getParameter("content");
		course.setName(name);
		course.setNum(Integer.parseInt(num));
		course.setDescription(description);
		course.setSummary(summary);
		course.setContent(content);
		return this.courseMapper.insert(course);
	}

	/**
	 * 删除course
	 */
	@RequestMapping(value = "/delete")
	@Permission
	@ResponseBody
	@BussinessLog(value = "删除课程", key = "courseId", dict = Dict.DeleteDict)
	public Object delete(@RequestParam Integer courseId) {
		// 缓存通知名称
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
	@BussinessLog(value = "修改课程", key = "title", dict = Dict.CourseMap)
	public Object update(@RequestParam(value = "imgFile", required = false) MultipartFile imgFile,
			HttpServletRequest request) {
		String courseId = request.getParameter("id");
		String name = request.getParameter("name");
		String num = request.getParameter("num");
		String description = request.getParameter("description");
		String summary = request.getParameter("summary");
		String content = request.getParameter("content");
		if (courseId == null || courseId.equals("")) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		Course course = courseMapper.selectById(Integer.parseInt(courseId));
		course.setName(name);
		course.setNum(Integer.parseInt(num));
		course.setDescription(description);
		course.setSummary(summary);
		course.setContent(content);
		if (imgFile != null) {
			String imgName = UUID.randomUUID().toString() + ".jpg";
			String path = "";
			try {
				String fileSavePath = gunsProperties.getFileUploadPath();
				path = fileSavePath + "course/" + imgName;
				imgFile.transferTo(new File(path));
			} catch (Exception e) {
				e.printStackTrace();
				throw new BussinessException(BizExceptionEnum.UPLOAD_FILE_ERROR);
			}
			path = "course/" + imgName;
			course.setImgPath(path);
		}
		courseMapper.updateById(course);
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
	/**
     * 返回图片
     *
     * @author stylefeng
     * @Date 2017/5/24 23:00
     */
    @RequestMapping("/{filename:.+}")
    public void renderPDF(@PathVariable("filename") String filename, HttpServletResponse response) {
        String path = gunsProperties.getFileUploadPath() +"course/"+filename;
        try {
            byte[] bytes = FileUtil.toByteArray(path);
            response.getOutputStream().write(bytes);
        }catch (Exception e){
            //如果找不到图片就返回一个默认图片
        	e.printStackTrace();
        }
    }
}
