package com.stylefeng.guns.modular.system.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.ShufflingMapper;
import com.stylefeng.guns.common.persistence.model.Shuffling;
import com.stylefeng.guns.config.properties.GunsProperties;
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

    /**
     * 新增shuffling
     */
    @BussinessLog(value = "添加轮播图")
    @RequestMapping(value = "/add")
    @Permission
    @ResponseBody
    public Object add(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
//    	MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
 //   	MultipartHttpServletRequest multipartRequest = resolver.resolveMultipart(request);
    	gunsProperties.setFileUploadPath("webapp/static/upload");
    	 String pictureName = UUID.randomUUID().toString() + ".jpg";
    	 String path = "";
         try {
             String fileSavePath = gunsProperties.getFileUploadPath();
             path= fileSavePath+pictureName;
             System.out.println("path="+path);
             file.transferTo(new File(path));
         } catch (Exception e) {
        	 e.printStackTrace();
             throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
         }
    	
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
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete() {
        return SUCCESS_TIP;
    }


    /**
     * 修改shuffling
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update() {
        return super.SUCCESS_TIP;
    }

    /**
     * shuffling详情
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Object detail() {
        return null;
    }
}
