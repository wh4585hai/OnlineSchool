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
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.shiro.ShiroKit;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.DicUtilDao;
import com.stylefeng.guns.modular.system.dao.TeacherDao;
import com.stylefeng.guns.modular.system.warpper.TeacherWrapper;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
    private GunsProperties gunsProperties;
    @Resource
    private TeacherDao teacherDao;
    @Resource
    private DicUtilDao dicUtilDao;
    
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
    	Map dicMap = new HashMap();
    	dicMap.put("useridname", dicUtilDao.getTeacherName());
    	dicMap.put("country", ConstantFactory.me().getDictList("国籍"));
    	dicMap.put("language", ConstantFactory.me().getDictList("语言"));
    	model.addAttribute("dicMap",dicMap);
        return PREFIX + "teacher_add.html";
    }
    /**
     * 跳转到添加teacher
     */
    @RequestMapping("/teacher_upload/{id}")
    public String teacherUpload(@PathVariable Integer id,Model model) {
    	if (ToolUtil.isEmpty(id)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
    	Teacher teacher = this.teacherMapper.selectById(id);
        model.addAttribute(teacher);      
        LogObjectHolder.me().set(teacher);
       
        return PREFIX + "teacher_upload.html";
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
        Map dicMap = new HashMap();
    	dicMap.put("useridname", dicUtilDao.getTeacherName());
    	dicMap.put("country", ConstantFactory.me().getDictList("国籍"));
    	dicMap.put("language", ConstantFactory.me().getDictList("语言"));
    	model.addAttribute("dicMap",dicMap);
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
    @RequestMapping(value = "/upload")
    @Permission
    @ResponseBody
    @BussinessLog(value = "上传教师视频",key = "video",dict = Dict.TeacherMap)
    public Object upload(@RequestParam(value = "file", required = false) MultipartFile file,
    		@RequestParam(value = "file1", required = false) MultipartFile file1,
    		@RequestParam(value = "file2", required = false) MultipartFile file2,
    		@RequestParam(value = "file3", required = false) MultipartFile file3,HttpServletRequest request) {
    	String teacherId=request.getParameter("id");
    	Teacher teacher = teacherMapper.selectById(Integer.parseInt(teacherId));
    	if(file!=null){
      		 String picName = UUID.randomUUID().toString() + ".jpg";
          	 String path = "";
               try {
                   String fileSavePath = gunsProperties.getFileUploadPath();
                   path= fileSavePath+"teacher/"+picName;
                   file.transferTo(new File(path));
               } catch (Exception e) {
              	 e.printStackTrace();
                   throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
               }
          	path ="teacher/"+picName;
          	teacher.setPicture(path);
      	 }
    	if(file1!=null){
   		 String videoName = UUID.randomUUID().toString() + ".mp4";
       	 String path = "";
            try {
                String fileSavePath = gunsProperties.getFileUploadPath();
                path= fileSavePath+"teacher/"+videoName;
                file1.transferTo(new File(path));
            } catch (Exception e) {
           	 e.printStackTrace();
                throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
            }
       	path ="teacher/"+videoName;
       	teacher.setPath1(path);
   	 }
    	if(file2!=null){
      		 String videoName = UUID.randomUUID().toString() + ".mp4";
          	 String path = "";
               try {
                   String fileSavePath = gunsProperties.getFileUploadPath();
                   path= fileSavePath+"teacher/"+videoName;
                   file2.transferTo(new File(path));
               } catch (Exception e) {
              	 e.printStackTrace();
                   throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
               }
          	path ="teacher/"+videoName;
          	teacher.setPath2(path);
      	 }
    	if(file3!=null){
      		 String videoName = UUID.randomUUID().toString() + ".mp4";
          	 String path = "";
               try {
                   String fileSavePath = gunsProperties.getFileUploadPath();
                   path= fileSavePath+"teacher/"+videoName;
                   file3.transferTo(new File(path));
               } catch (Exception e) {
              	 e.printStackTrace();
                   throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
               }
          	path ="teacher/"+videoName;
          	teacher.setPath3(path);
      	 }
    	teacherMapper.updateById(teacher);
    	
        return super.SUCCESS_TIP;
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
    	//String introduction = request.getParameter("introduction");
   
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
    
    /**
     * 返回图片
     *
     * @author stylefeng
     * @Date 2017/5/24 23:00
     */
    @RequestMapping("/{filename:.+}")
    public void renderPicture(@PathVariable("filename") String filename, HttpServletResponse response) {
        String path = gunsProperties.getFileUploadPath() +"teacher/"+filename;
        try {
            byte[] bytes = FileUtil.toByteArray(path);
            response.getOutputStream().write(bytes);
        }catch (Exception e){
            //如果找不到图片就返回一个默认图片
        	e.printStackTrace();
        }
    }
}
