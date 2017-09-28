package com.stylefeng.guns.modular.system.controller;

import static com.stylefeng.guns.core.support.HttpKit.getIp;

import java.util.Date;
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

import com.stylefeng.guns.common.constant.state.ManagerStatus;
import com.stylefeng.guns.common.constant.tips.ErrorTip;
import com.stylefeng.guns.common.constant.tips.Tip;
import com.stylefeng.guns.common.constant.tips.ValTip;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.CourseMapper;
import com.stylefeng.guns.common.persistence.dao.MaterialMapper;
import com.stylefeng.guns.common.persistence.dao.ShufflingMapper;
import com.stylefeng.guns.common.persistence.dao.StudentMapper;
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
import com.stylefeng.guns.modular.system.dao.CourseDao;
import com.stylefeng.guns.modular.system.dao.MaterialDao;
import com.stylefeng.guns.modular.system.dao.ShufflingDao;
import com.stylefeng.guns.modular.system.dao.StudentDao;
import com.stylefeng.guns.modular.system.dao.TeacherDao;
import com.stylefeng.guns.modular.system.dao.UserMgrDao;
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
	private CourseDao courseDao;
	@Resource
	private CourseMapper courseMapper;
	

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
	 @RequestMapping("/to_new_order")
	 public String to_new_order(String id,Model model){
		 super.setAttr("id", id);
		 return PREFIX +  "order.html";
	 }
	 @RequestMapping("/to_my_order")
	 public String to_my_order(String id,Model model){
		 super.setAttr("id", id);
		 return PREFIX +  "orderList.html";
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
}
