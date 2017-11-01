package com.stylefeng.guns.modular.system.controller;

import static com.stylefeng.guns.core.support.HttpKit.getIp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.constant.state.ManagerStatus;
import com.stylefeng.guns.common.constant.tips.ErrorTip;
import com.stylefeng.guns.common.constant.tips.Tip;
import com.stylefeng.guns.common.constant.tips.ValTip;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.ClassScheduleMapper;
import com.stylefeng.guns.common.persistence.dao.CourseMapper;
import com.stylefeng.guns.common.persistence.dao.MaterialMapper;
import com.stylefeng.guns.common.persistence.dao.ShufflingMapper;
import com.stylefeng.guns.common.persistence.dao.StudentMapper;
import com.stylefeng.guns.common.persistence.dao.TeacherMapper;
import com.stylefeng.guns.common.persistence.model.ClassSchedule;
import com.stylefeng.guns.common.persistence.model.Course;
import com.stylefeng.guns.common.persistence.model.Material;
import com.stylefeng.guns.common.persistence.model.Shuffling;
import com.stylefeng.guns.common.persistence.model.Student;
import com.stylefeng.guns.common.persistence.model.Teacher;
import com.stylefeng.guns.common.persistence.model.User;
import com.stylefeng.guns.core.log.LogManager;
import com.stylefeng.guns.core.log.factory.LogTaskFactory;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.shiro.ShiroUser;
import com.stylefeng.guns.core.shiro.UsernamePasswordUsertypeToken;
import com.stylefeng.guns.core.shiro.factory.ShiroFactroy;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.modular.system.dao.ClassscheduleDao;
import com.stylefeng.guns.modular.system.dao.CourseDao;
import com.stylefeng.guns.modular.system.dao.DicUtilDao;
import com.stylefeng.guns.modular.system.dao.MaterialDao;
import com.stylefeng.guns.modular.system.dao.OrdermanageDao;
import com.stylefeng.guns.modular.system.dao.ShufflingDao;
import com.stylefeng.guns.modular.system.dao.StudentDao;
import com.stylefeng.guns.modular.system.dao.TeacherDao;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
import com.stylefeng.guns.modular.system.warpper.ClassScheduleWrapper;
import com.stylefeng.guns.modular.system.warpper.OrderWrapper;
import com.stylefeng.guns.modular.system.warpper.TeacherWrapper;

@Controller
@RequestMapping("/front")
public class FrontController extends BaseController {

	@Resource
	private ShufflingDao shufflingDao;
	@Resource
	private MaterialDao materialDao;
	@Resource
	private ShufflingMapper shufflingMapper;
	@Resource
	private MaterialMapper materialMapper;
	@Resource
	private StudentMapper studentMapper;
	@Resource
	private StudentDao studentDao;
	@Resource
	private UserMgrDao userDao;
	@Resource
	private TeacherDao teacherDao;
	@Resource
	private TeacherMapper teacherMapper;
	@Resource
	private CourseDao courseDao;
	@Resource
	private CourseMapper courseMapper;
	@Resource
    private DicUtilDao dicUtilDao;
	@Resource
    private ClassscheduleDao classscheduleDao;
	@Resource
    private ClassScheduleMapper classscheduleMapper;
	@Resource
	private OrdermanageDao ordermanageDao;

	private String PREFIX = "/front/";
	private void setStudentForRequest(Model model){
		ShiroUser shiroUser=ShiroKit.getUser();
		if(shiroUser!=null){
			String account = ShiroKit.getUser().getAccount();
			System.out.println("account="+account);
			if(account!=null&&!account.equals("")){
				Student student = studentDao.getByAccount(account);
				System.out.println("student="+student);
				User user =userDao.getByAccount(account);
				if(user!=null){
					model.addAttribute("user", user);
				}
				if(student!=null){
					System.out.println("student="+student);
					 model.addAttribute("student", student);
				}
			}
		}
	}
	@RequestMapping("")
	public String index(Model model) {
		List<Shuffling> shuffling_list = shufflingDao.listforFront();
		List<Material> material_list = materialDao.listForFront();
		
		List<Map<String, Object>> list = this.teacherDao.listforFront();
		
		
		List<Course> course_list=courseDao.listForFront();
		for (Material m : material_list) {
			System.out.println("this=" + m.getImgPath());
		}
		setStudentForRequest(model);
		super.setAttr("shuffling_list", shuffling_list);
		super.setAttr("teacher_list", new TeacherWrapper(list).warp());
		super.setAttr("material_list", material_list);
		super.setAttr("course_list", course_list);
		return PREFIX + "index.html";
	}
	 @RequestMapping("/existStudent")
	    @ResponseBody
	    public ValTip exits(String account) {
	    	Student st = studentDao.getByAccount(account);
	    	ValTip vTip = new ValTip();
	    	if(st==null){
	    		vTip.setValid(true);
	    		return vTip;
	    	}
	        return vTip;
	    }
	 @RequestMapping("/to_modify_pwd")
	 public String to_update_pwd(String id,Model model){
		 super.setAttr("id", id);
		 return PREFIX +  "passWord.html";
	 }
	 @RequestMapping("/resetPWD")
	 @ResponseBody
	 public Tip update_pwd(String id,String oldpassword,String password,Model model){
		 System.out.println(id);
		 Student student = studentMapper.selectById(id);
		 Subject currentUser = ShiroKit.getSubject();
		 UsernamePasswordUsertypeToken token = new UsernamePasswordUsertypeToken(student.getAccount(), oldpassword,"student");
		 token.setRememberMe(true);
	        try {
	        	currentUser.login(token);
	        	// 完善账号信息
	            student.setSalt(ShiroKit.getRandomSalt(5));
	            student.setPassword(ShiroKit.md5(password, student.getSalt()));
	            this.studentMapper.updateById(student);
	        	
			} catch (AuthenticationException e) {
				// TODO: handle exception
				return new ErrorTip(500, "AuthenticationException");
			}
		System.out.println(token);
		return SUCCESS_TIP;
	 }
	@RequestMapping("/acivityDetail/{shufflingId}")
	public String acivityDetail(@PathVariable("shufflingId") Integer shufflingId,Model model) {
		Shuffling shuffling = shufflingMapper.selectById(shufflingId);
		setStudentForRequest(model);
		super.setAttr("shuffling", shuffling);
		return PREFIX + "acivityDetail.html";
	}

	@RequestMapping("/materialDetail/{materialId}")
	public String materialDetail(@PathVariable("materialId") Integer materialId,Model model) {
		List<Material> material_list = materialDao.listForAll();
		Material material = materialMapper.selectById(materialId);
		setStudentForRequest(model);
		String url = "http://47.94.96.156/onlineSchool-1.5.3.RELEASE/"+material.getPdfPath();
		String pdf = "<a target='_blank' href='/generic/web/viewer.html?file="+url+"'>PDF预览</a>";
		super.setAttr("pdf", pdf);
		super.setAttr("material", material);
		super.setAttr("material_list", material_list);
		return PREFIX + "materialDetail.html";
	}
	@RequestMapping("/courseDetail/{courseId}")
	public String courseDetail(@PathVariable("courseId") Integer courseId,Model model) {
		List<Course> course_list = courseDao.listForAll();
		Course course = courseMapper.selectById(courseId);
		setStudentForRequest(model);
		super.setAttr("course", course);
		super.setAttr("course_list", course_list);
		return PREFIX + "courseDetail.html";
	}
	
	@RequestMapping("/teacherDetail/{teacherId}")
	public String teacherDetail(@PathVariable("teacherId") Integer teacherId,Model model) {
		List<Map<String, Object>> list = teacherDao.listforAll();
		Teacher teacher = teacherMapper.selectById(teacherId);
		setStudentForRequest(model);
		super.setAttr("teacher", teacher);
		super.setAttr("teacherName", ConstantFactory.me().getUserNameById(teacher.getUserId()));
		super.setAttr("languageName", ConstantFactory.me().getDictsByName("语言",teacher.getLanguage()));
		super.setAttr("teacher_list", new TeacherWrapper(list).warp());
		return PREFIX + "teacherDetail.html";
	}
	
	
	
	@RequestMapping("/sign")
	public String sign(Model model) {
		return PREFIX + "sign.html";
	}
//	@RequestMapping("/login_s")
//	public String login_s(Model model) {
//		return PREFIX + "login.html";
//	}
	 /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login_s", method = RequestMethod.GET)
    public String login(@ModelAttribute("tips") String str) {
    	System.out.println(ShiroKit.isAuthenticated());
    	System.out.println(ShiroKit.getUser());
        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
        	System.out.println("not user");
            return REDIRECT +"/front";
        } else {
        	super.setAttr("tips", str);
        	return PREFIX + "login.html";
        }
    }
	/**
     * 点击登录执行的动作
     */
    @RequestMapping(value = "/login_s", method = RequestMethod.POST)
    public String loginVali(RedirectAttributes attributes) {

        String username = super.getPara("account").trim();
        String password = super.getPara("password").trim();
        
        //验证验证码是否正确
//        if(ToolUtil.getKaptchaOnOff()){
//            String kaptcha = super.getPara("kaptcha").trim();
//            String code = (String) super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
//            if(ToolUtil.isEmpty(kaptcha) || !kaptcha.equals(code)){
//                throw new InvalidKaptchaException();
//            }
//        }

        Subject currentUser = ShiroKit.getSubject();
        UsernamePasswordUsertypeToken token = new UsernamePasswordUsertypeToken(username, password,"student");
        //UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray());
        token.setRememberMe(true);
        try {
        	currentUser.login(token);
		} catch (AuthenticationException e) {
			// TODO: handle exception
			attributes.addAttribute("tips", "用户名或者密码错误");
			 return REDIRECT +"/front/login_s";
		}
        

        ShiroUser shiroUser = ShiroKit.getUser();
        super.getSession().setAttribute("shiroUser", shiroUser);
        super.getSession().setAttribute("username", shiroUser.getAccount());

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

        ShiroKit.getSession().setAttribute("sessionFlag",true);

        return REDIRECT +"/front";
    }
	@RequestMapping("/registerStudnet")
	@ResponseBody
	public Tip registerStudnet(Model model, Student student) {
		
		  // 判断账号是否重复
		Student theStudent = studentDao.getByAccount(student.getAccount());
        if (theStudent != null) {
            throw new BussinessException(BizExceptionEnum.USER_ALREADY_REG);
        }
        // 完善账号信息
        student.setSalt(ShiroKit.getRandomSalt(5));
        student.setPassword(ShiroKit.md5(student.getPassword(), student.getSalt()));
        student.setStatus(ManagerStatus.OK.getCode());
        student.setCreatetime(new Date());
        this.studentMapper.insert(student);
		//return PREFIX + "sign.html";
		 return SUCCESS_TIP;
	}
	 /**
     * 退出登录
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut() {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        return REDIRECT + "/front/login_s";
    }
    
    @RequestMapping("/to_new_order")
	 public String to_new_order(String id,Model model){
		 super.setAttr("id", id);
		 Map dicMap = new HashMap();
		 dicMap.put("meterialname", dicUtilDao.getMeterialName());
		 dicMap.put("coursename", dicUtilDao.getCourseName());
		 dicMap.put("calsstime", ConstantFactory.me().getDictList("课时价格"));
		 model.addAttribute("dicMap",dicMap); 
		 setStudentForRequest(model);
		 return PREFIX +  "order.html";
	 }
	 @RequestMapping("/to_my_order")
	 public String to_my_order(String id,String date,Model model){
		 super.setAttr("id", id);		 
		
		/* Map dicMap = new HashMap();
	    	dicMap.put("calsstime", ConstantFactory.me().getDictList("课时价格"));
	    	dicMap.put("useridname", dicUtilDao.getTeacherName());
	    	dicMap.put("meterialname", dicUtilDao.getMeterialName());
	        model.addAttribute("dicMap",dicMap);*/ 
	        List<Map<String, Object>> list = this.ordermanageDao.list(id, date);
	        List<Map<String, Object>> orders=  (List<Map<String, Object>>) super.warpObject(new OrderWrapper(list));
	        model.addAttribute("orders",orders);
	        setStudentForRequest(model);
		 return PREFIX +  "orderList.html";
	 }
	 
	 @RequestMapping("/")
	 public String indexTo(Model model) {
	 List<Shuffling> shuffling_list = shufflingDao.listforFront();
	 List<Material> material_list = materialDao.listForFront();

	 List<Map<String, Object>> list = this.teacherDao.listforFront();


	 List<Course> course_list=courseDao.listForFront();
	 for (Material m : material_list) {
	 System.out.println("this=" + m.getImgPath());
	 }
	 setStudentForRequest(model);
	 super.setAttr("shuffling_list", shuffling_list);
	 super.setAttr("teacher_list", new TeacherWrapper(list).warp());
	 super.setAttr("material_list", material_list);
	 super.setAttr("course_list", course_list);
	 return PREFIX + "index.html";
	 }
	 @RequestMapping("/to_my_lesson")	
	 public String to_my_lesson(String id,String datefrom,String dateto,Model model){	
		 super.setAttr("id", id);
		// Map dicMap = new HashMap();
	    	/*dicMap.put("useridname", dicUtilDao.getTeacherName());
	    	model.addAttribute("dicMap",dicMap);*/
	    	model.addAttribute("studentid",id);
	    	model.addAttribute("datefrom",datefrom);
	    	model.addAttribute("dateto",dateto);
	    	List<Map<String, Object>> list = this.classscheduleDao.listForStudent(Integer.valueOf(id), datefrom,dateto);
	    	List<Map<String, Object>> classschedules=  (List<Map<String, Object>>) super.warpObject(new ClassScheduleWrapper(list));
	    	 model.addAttribute("classschedules",classschedules);
	    	 setStudentForRequest(model);
	        return PREFIX + "myLesson.html";
		 
	 }
	 @RequestMapping("/to_my_lesson_comment")	
	 public String to_my_lesson_comment(String id,Model model){	
		 ClassSchedule classschedule =classscheduleMapper.selectById(id);
	    	 model.addAttribute("classschedule", classschedule);
	    	 model.addAttribute("studentName", ConstantFactory.me().getStudentName(classschedule.getStudentid()));
	    	 model.addAttribute("teacherName", ConstantFactory.me().getUserNameById(classschedule.getTeacherid()));
	    	 model.addAttribute("meterialName", ConstantFactory.me().getMeterialName(classschedule.getMaterialid()));
	    	 model.addAttribute("statusName", ConstantFactory.me().getDictsByName("课程状态",classschedule.getStatus()));
	    	 model.addAttribute("isdelayName", ConstantFactory.me().getDictsByName("是否延期",classschedule.getIsdelay()));
	    	 model.addAttribute("courseTimeName", ConstantFactory.me().getDictsByName("课时价格",classschedule.getCoursetime()));
	    	 model.addAttribute("classapproach",ConstantFactory.me().getDictsByName("上课方式",ConstantFactory.me().getClassApproach(classschedule.getOrderid())));
	    	 model.addAttribute("classnumber", ConstantFactory.me().getClassNumber(classschedule.getOrderid()));
	    	 model.addAttribute("count", ConstantFactory.me().getclassCoont(classschedule.getCoursetime(),classschedule.getStatus()));
	    	 model.addAttribute("startDate", DateUtil.getDay(classschedule.getDate()));
	    	 setStudentForRequest(model);
	        return PREFIX + "comment.html";
		 
	 }	 
	 @RequestMapping("/submit_my_lesson_comment")
	 public String submit_my_lesson_comment(String id,Model model,String comment){	
		 ClassSchedule classschedule =classscheduleMapper.selectById(id);
		 classschedule.setComment(comment);
		 //classschedule.setStartDate(DateUtil.getDay(classschedule.getDate()));
		 this.classscheduleMapper.updateById(classschedule);
	        return REDIRECT+"/front/to_my_lesson_comment.html?id="+classschedule.getId();
		 
	 }
}
