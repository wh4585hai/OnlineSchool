package com.stylefeng.guns.modular.system.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
import com.stylefeng.guns.common.persistence.dao.ShufflingMapper;
import com.stylefeng.guns.common.persistence.model.Dept;
import com.stylefeng.guns.common.persistence.model.Shuffling;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.core.util.ToolUtil;
import com.stylefeng.guns.modular.system.dao.ShufflingDao;
import com.stylefeng.guns.modular.system.warpper.ShufflingWarpper;


/**
 * shuffling控制器
 *
 * @author fengshuonan
 * @Date 2017-07-10 14:37:24
 */
@Controller
@RequestMapping("/shuffling")
public class ShufflingController extends BaseController {

    private String PREFIX = "/system/shuffling/";
    @Resource
    private GunsProperties gunsProperties;
    @Resource
    private ShufflingDao shufflingDao;
    @Resource
    ShufflingMapper shufflingMapper;
    @Resource
    private ResourceLoader resourceLoader; 
    /**
     * 跳转到shuffling首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "shuffling.html";
    }

    /**
     * 跳转到添加shuffling
     */
    @RequestMapping("/shuffling_add")
    public String shufflingAdd() {
        return PREFIX + "shuffling_add.html";
    }

    /**
     * 跳转到修改shuffling
     */
    @RequestMapping("/shuffling_update/{shufflingId}")
    public String shufflingUpdate(@PathVariable Integer shufflingId, Model model) {
    	 Shuffling shuffling = shufflingMapper.selectById(shufflingId);
         model.addAttribute(shuffling);
         LogObjectHolder.me().set(shuffling);
        return PREFIX + "shuffling_edit.html";
    }
    /**
     * 获取shuffling列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	 List<Map<String, Object>> list = this.shufflingDao.list(condition);
         return super.warpObject(new ShufflingWarpper(list));
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")  
    @ResponseBody  
    public String getFile(HttpServletRequest request,  
            HttpServletResponse response, @PathVariable String filename) {  
    	String ROOT = gunsProperties.getFileUploadPath()+"shuffling/";
    	 // response.setContentType("image/*");  
        FileInputStream fis = null;  
        OutputStream os = null;  
        File file = new File(ROOT+filename);
       if(!file.exists()){
    	   return "ok";
       }
//       byte[] bytes = FileUtil.toByteArray(path);
//       response.getOutputStream().write(bytes);
        try {  
        	if(FileUtil.isImage(file)){
            fis = new FileInputStream(file);  
            os = response.getOutputStream();  
            int count = 0;  
            byte[] buffer = new byte[1024 * 8];  
            while ((count = fis.read(buffer)) != -1) {  
                os.write(buffer, 0, count);  
                os.flush();  
            }  
            }
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        try {  
            fis.close();  
            os.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return "ok";  
//        try {  
//            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));  
//        } catch (Exception e) {  
//            return ResponseEntity.notFound().build();  
//        }  
    }  
    /**
     * 新增shuffling
     */
    @BussinessLog(value = "添加轮播图", key = "title", dict = Dict.ShufflingDict)
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
    	 String pictureName = UUID.randomUUID().toString() + ".jpg";
    	 String path = "";
         try {
             String fileSavePath = gunsProperties.getFileUploadPath();
             path= fileSavePath+"shuffling/"+pictureName;
             file.transferTo(new File(path));
         } catch (Exception e) {
        	 e.printStackTrace();
             throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
         }
    	path ="shuffling/"+pictureName;
    	String title =request.getParameter("title");
    	String num =request.getParameter("num");
    	Shuffling sf = new Shuffling();
    	sf.setNum(Integer.parseInt(num));
    	sf.setTitle(title);
    	sf.setPath(path);
    	 return this.shufflingMapper.insert(sf);
    }

    /**
     * 删除shuffling
     */
    @BussinessLog(value = "删除轮播图", key = "shufflingId", dict = Dict.ShufflingDict)
    @RequestMapping(value = "/delete")
    @Permission
    @ResponseBody
    public Object delete(@RequestParam Integer shufflingId) {
    	this.shufflingMapper.deleteById(shufflingId);
        return SUCCESS_TIP;
    }


    /**
     * 修改shuffling
     */
    @RequestMapping(value = "/update")
    @Permission
    @ResponseBody
    public Object update(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
    	String shufflingId=request.getParameter("id");
    	String title =request.getParameter("title");
    	String num =request.getParameter("num");
    	if(shufflingId==null || shufflingId.equals("")){
    		 throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
    	}
    	 Shuffling shuffling = shufflingMapper.selectById(Integer.parseInt(shufflingId));
    	 shuffling.setTitle(title);
    	 shuffling.setNum(Integer.parseInt(num));
    	 if(file!=null){
    		 String pictureName = UUID.randomUUID().toString() + ".jpg";
        	 String path = "";
             try {
                 String fileSavePath = gunsProperties.getFileUploadPath();
                 path= fileSavePath+"shuffling/"+pictureName;
                 file.transferTo(new File(path));
             } catch (Exception e) {
            	 e.printStackTrace();
                 throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
             }
        	path ="shuffling/"+pictureName;
        	shuffling.setPath(path);
        	shufflingMapper.updateById(shuffling);
    	 }else{
    		 shufflingMapper.updateById(shuffling);
    	 }
    	return super.SUCCESS_TIP;
    }

    /**
     * shuffling详情
     */
    @RequestMapping(value = "/detail/{shufflingId}")
    @ResponseBody
    public Object detail(@PathVariable("shufflingId") Integer shufflingId) {
        return shufflingMapper.selectById(shufflingId);
    }
    
}
