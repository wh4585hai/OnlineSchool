package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Dict;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.TeacherMapper;
import com.stylefeng.guns.common.persistence.model.Teacher;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.TeacherDao;
import com.stylefeng.guns.modular.system.warpper.TeacherWrapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * teacher控制器
 *
 * @author fengshuonan
 * @Date 2017-07-20 21:57:31
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController extends BaseController {

    private String PREFIX = "/system/teacher/";
    @Resource
    private TeacherDao teacherDao;
    
    @Resource
    private TeacherMapper teacherMapper;

    /**
     * 跳转到teacher首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "teacher.html";
    }

    /**
     * 跳转到添加teacher
     */
    @RequestMapping("/teacher_add")
    public String teacherAdd(Model model) {
    	Map countryMap = new HashMap();    	 	
        return PREFIX + "teacher_add.html";
    }

    /**
     * 跳转到修改teacher
     */
    @RequestMapping("/teacher_update/{id}")
    public String teacherUpdate(@PathVariable Integer id, Model model) {
    	
    	
    	if (ToolUtil.isEmpty(id)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	Teacher teacher = this.teacherMapper.selectById(id);
        model.addAttribute(teacher);      
        LogObjectHolder.me().set(teacher);
    	
        return PREFIX + "teacher_edit.html";
    }

    /**
     * 获取teacher列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	 List<Map<String, Object>> list = this.teacherDao.list(condition);
         return super.warpObject(new TeacherWrapper(list));
    }

    /**
     * 新增teacher
     */
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    @BussinessLog(value = "新增教师",key = "name",dict = Dict.TeacherMap)
    public Object add(@Valid Teacher teacher,BindingResult result) {
    	   if (result.hasErrors()) {
               throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
           }
    	if (ToolUtil.isOneEmpty(teacher, teacher.getName())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	//String introduction = request.getParameter("introduction");
    
    	teacher.setSalt(ShiroKit.getRandomSalt(5));
    	teacher.setPassword(ShiroKit.md5(teacher.getPassword(), teacher.getSalt()));         	
    	teacher.setUserId(ShiroKit.getUser().getId());
    	teacher.setCreateDate(new Date());
    	teacher.insert();   
        return super.SUCCESS_TIP;
    }

    /**
     * 删除teacher
     */
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer teacherId) {
    	 LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(teacherId));

         this.teacherMapper.deleteById(teacherId);
        return SUCCESS_TIP;
    }


    /**
     * 修改teacher
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    @BussinessLog(value = "修改教师",key = "name",dict = Dict.TeacherMap)
    public Object update(@Valid Teacher teacher, BindingResult result) {
    	  if (result.hasErrors()) {
              throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
          }
    	 if (ToolUtil.isOneEmpty(teacher, teacher.getName())) {
             throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
         }
    	 Teacher nTeacher = new Teacher();
    	 BeanUtils.copyProperties(teacher,nTeacher);                       
    	 nTeacher.updateById();
         return SUCCESS_TIP;
    }

    /**
     * teacher详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
