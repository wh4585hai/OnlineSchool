package com.stylefeng.guns.modular.system.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.constant.state.ManagerStatus;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.StudentMapper;
import com.stylefeng.guns.common.persistence.model.Course;
import com.stylefeng.guns.common.persistence.model.Student;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.StudentDao;
import com.stylefeng.guns.modular.system.warpper.CourseWarpper;
import com.stylefeng.guns.modular.system.warpper.StudentWarpper;

/**
 * student控制器
 *
 * @author fengshuonan
 * @Date 2017-08-22 14:00:04
 */
@Controller
@RequestMapping("/student")
public class StudentController extends BaseController {

	private String PREFIX = "/system/student/";
	@Resource
	private StudentDao studentDao;
	@Resource
	private StudentMapper studentMapper;
	/**
	 * 跳转到student首页
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "student.html";
	}

	/**
	 * 跳转到添加student
	 */
	@RequestMapping("/student_add")
	public String studentAdd() {
		return PREFIX + "student_add.html";
	}

	/**
	 * 跳转到修改student
	 */
	@RequestMapping("/student_update/{studentId}")
	public String studentUpdate(@PathVariable Integer studentId, Model model) {
		Student student = this.studentMapper.selectById(studentId);
         model.addAttribute("student",student);
         LogObjectHolder.me().set(student);
		return PREFIX + "student_edit.html";
	}

	/**
	 * 获取student列表
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public Object list(String condition) {
		 List<Map<String, Object>> list = this.studentDao.list(condition);
         return super.warpObject(new StudentWarpper(list));
	}

	/**
	 * 新增student
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	@Permission
	public Object add(Student student) {
		if (ToolUtil.isEmpty(student) || student.getId() == null) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
		 // 完善账号信息
		student.setSalt(ShiroKit.getRandomSalt(5));
		student.setPassword(ShiroKit.md5(student.getPassword(), student.getSalt()));
		student.setStatus(ManagerStatus.OK.getCode());
		studentMapper.updateById(student);
		return super.SUCCESS_TIP;
	}

	/**
	 * 删除student
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	@Permission
	public Object delete(@RequestParam Integer studentId) {

		studentMapper.deleteById(studentId);

		return SUCCESS_TIP;
	}

	/**
	 * 修改student
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	@Permission
	public Object update(Student student) {
		if (ToolUtil.isEmpty(student) || student.getId() == null) {
			throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
		}
        Student studentOld = studentDao.getByAccount(student.getAccount());
        if(!(student.getPassword()).equals(studentOld.getPassword())){
        	String oldSalt= studentOld.getSalt();
            String newMd5 = ShiroKit.md5(student.getPassword(),oldSalt);
        	student.setPassword(newMd5);
        }
		studentMapper.updateById(student);
		return super.SUCCESS_TIP;
	}

	/**
	 * student详情
	 */
	@RequestMapping(value = "/detail")
	@ResponseBody
	public Object detail() {
		return null;
	}
}
