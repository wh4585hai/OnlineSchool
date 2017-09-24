package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.ClassScheduleMapper;
import com.stylefeng.guns.common.persistence.model.ClassSchedule;
import com.stylefeng.guns.common.persistence.model.ClassScheduleModel;
import com.stylefeng.guns.common.persistence.model.Student;
import com.stylefeng.guns.common.persistence.model.Teacher;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.ClassscheduleDao;
import com.stylefeng.guns.modular.system.dao.DicUtilDao;
import com.stylefeng.guns.modular.system.dao.TeacherDao;
import com.stylefeng.guns.modular.system.warpper.ClassScheduleWrapper;
import com.stylefeng.guns.modular.system.warpper.TeacherWrapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
 * 课程表控制器
 *
 * @author fengshuonan
 * @Date 2017-09-04 22:01:21
 */
@Controller
@RequestMapping("/classschedule")
public class ClassscheduleController extends BaseController {

    private String PREFIX = "/system/classschedule/";
    @Resource
    private ClassscheduleDao classscheduleDao;
    @Resource
    private DicUtilDao dicUtilDao;
    @Resource
    private ClassScheduleMapper classScheduleMapper;

    /**
     * 跳转到课程表首页
     */
    @RequestMapping("")
    public String index(Model model) {
        return PREFIX + "classschedule.html";
    }

    /**
     * 跳转到课程表首页
     */
    @RequestMapping("/manager")
    public String classScheduleManager(Model model) {
    	Map dicMap = new HashMap();
    	dicMap.put("useridname", dicUtilDao.getTeacherName());
    	model.addAttribute("dicMap",dicMap);
        return PREFIX + "classschedulemanager.html";
    }
    /**
     * 跳转到添加课程表
     */
    @RequestMapping("/classschedule_add")
    public String classscheduleAdd(Model model) {
    	
    	
    	Map dicMap = new HashMap();
    	dicMap.put("calsstime", ConstantFactory.me().getDictList("课时价格"));
    	dicMap.put("useridname", dicUtilDao.getTeacherName());
    	dicMap.put("meterialname", dicUtilDao.getMeterialName());
        model.addAttribute("dicMap",dicMap); 
        return PREFIX + "classschedule_add.html";
    }

    /**
     * 跳转到修改课程表
     */
    @RequestMapping("/classschedule_update/{id}")
    public String classscheduleUpdate(@PathVariable Integer id, Model model) {
    	
    	if (ToolUtil.isEmpty(id)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	ClassSchedule classSchedule = this.classScheduleMapper.selectById(id);
    	Map dicMap = new HashMap();
    	dicMap.put("calsstime", ConstantFactory.me().getDictList("课时价格"));
    	dicMap.put("useridname", dicUtilDao.getTeacherName());
    	dicMap.put("calssstatus", ConstantFactory.me().getDictList("课程状态"));
    	dicMap.put("meterialname", dicUtilDao.getMeterialName());
        model.addAttribute("dicMap",dicMap);
        model.addAttribute(classSchedule);      
        LogObjectHolder.me().set(classSchedule);
    	
        
        return PREFIX + "classschedule_edit.html";
    }
    
    /**
     * 跳转到修改课程表
     */
    @RequestMapping("/classschedule_checkin/{id}")
    public String classscheduleCheckIn(@PathVariable Integer id, Model model) {
    	
    	if (ToolUtil.isEmpty(id)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	ClassSchedule classSchedule = this.classScheduleMapper.selectById(id);
    	Map dicMap = new HashMap();
    	dicMap.put("calsstime", ConstantFactory.me().getDictList("课时价格"));
    	dicMap.put("calssstatus", ConstantFactory.me().getDictList("课程状态"));
    	dicMap.put("useridname", dicUtilDao.getTeacherName());
    	dicMap.put("meterialname", dicUtilDao.getMeterialName());
        model.addAttribute("dicMap",dicMap);
        model.addAttribute(classSchedule);      
        LogObjectHolder.me().set(classSchedule);
    	
        
        return PREFIX + "classscheduleCheckIn.html";
    }

    /**
     * 获取课程表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String studentname,Integer teachername,String date) {
    	
    	
    	 List<Map<String, Object>> list = this.classscheduleDao.list(studentname, teachername, date);
         return super.warpObject(new ClassScheduleWrapper(list));
    }
    /**
     * 教师获取课程表列表
     */
    @RequestMapping(value = "/listforteacher")
    @ResponseBody
    public Object listForTeacher(String studentname,String date) {
    	
    	 int teachername = ShiroKit.getUser().getId();
    	 List<Map<String, Object>> list = this.classscheduleDao.list(studentname, teachername, date);
         return super.warpObject(new ClassScheduleWrapper(list));
    }

    /**
     * 新增课程表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(@Valid ClassScheduleModel classScheduleModel,BindingResult result) {
    	/*Student student = new Student();
    	student.setNickname(classScheduleModel.getStudentName());
    	Integer studentId = ConstantFactory.me().getStudentId(student);
    	if(studentId==null) {
    	return	super.ERROR;
    		
    	}*/
    	
    	ClassSchedule classSchedule = new ClassSchedule();
    	
    	classSchedule.setStudentid(classScheduleModel.getStudentid());
    	classSchedule.setTeacherid(classScheduleModel.getTeacherid());
    	classSchedule.setStarttime(classScheduleModel.getStarttime());
    	classSchedule.setCoursetime(classScheduleModel.getCoursetime());
    	classSchedule.setMaterialid(classScheduleModel.getMaterialid());
    	classSchedule.setOrderid(classScheduleModel.getOrderid());
    	classSchedule.setStatus(0);
  	
    	int months = classScheduleModel.getMaterialid();
    	Date startDate = classScheduleModel.getDate();
    	String weeks = classScheduleModel.getWeeks();
    	String[] timesPerWeek = weeks.split(",");
    	int weekcount = months*4;
    	Date classdate = null ;
    	for(int i = 0;i<weekcount;i++) {
    		
    		for(int j=0;j<timesPerWeek.length;j++) {
    			if(j==0) {
    				
    				if(i==0) {
    					
    					classdate = setdate(startDate,Integer.parseInt(timesPerWeek[0])-1);
    				}else {
    					
    					classdate = setdate(classdate,Integer.parseInt(timesPerWeek[0])-1);
    				}   				
    				classSchedule.setDate(classdate);  				
    				classSchedule.insert();
    			
    			}else {
    				classdate = setdate(classdate,Integer.parseInt(timesPerWeek[j])-Integer.parseInt(timesPerWeek[j-1]));
    				classSchedule.setDate(classdate);
    				
    				classSchedule.insert();
    			}  			
    		}
    		 classdate = getNextMonday(classdate);
    	}  	
        return super.SUCCESS_TIP;
    }
    
 // 获得当前日期与本周日相差的天数  
    private static int getMondayPlus(Date gmtCreate) {  
        Calendar cd = Calendar.getInstance();  
        cd.setTime(gmtCreate);  
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......  
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1  
        if (dayOfWeek == 1) {  
            return 0;  
        } else {  
            return 1 - dayOfWeek;  
        }  
    }  
      
    // 获得下周星期一的日期  
    public static Date getNextMonday(Date date) { 
    	Calendar c = Calendar.getInstance();
    	c.setTime(date);    	   	   	
        int mondayPlus = getMondayPlus(date);  
        c.add(c.DATE, mondayPlus + 7);  
        Date monday = c.getTime();  
        return monday;  
    }  
    
    private Date setdate(Date date,int n) {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyMMdd");      	
    	Calendar c = Calendar.getInstance();  
    	c.setTime(date);  
    	c.add(Calendar.DATE, n);
    	System.out.println(sdf.format(c.getTime())); 		
    	return c.getTime();
    }

    /**
     * 删除课程表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer classscheduleId) {
    	 this.classScheduleMapper.deleteById(classscheduleId);
        return SUCCESS_TIP;
    }


    /**
     * 修改课程表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(@Valid ClassSchedule classSchedule, BindingResult result) {
    	 if (result.hasErrors()) {
             throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
         }
   	 if (ToolUtil.isOneEmpty(classSchedule, classSchedule.getId())) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
   	ClassSchedule nClassSchedule = new ClassSchedule();
   	 BeanUtils.copyProperties(classSchedule,nClassSchedule);                       
   	nClassSchedule.updateById();
        return SUCCESS_TIP;
    }

    /**
     * 课程表详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
    
    
    
    
    
}