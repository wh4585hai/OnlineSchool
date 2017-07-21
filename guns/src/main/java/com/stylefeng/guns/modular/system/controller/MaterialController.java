package com.stylefeng.guns.modular.system.controller;

import com.stylefeng.guns.common.annotion.Permission;
import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Dict;
import com.stylefeng.guns.common.constant.factory.ConstantFactory;
import com.stylefeng.guns.common.controller.BaseController;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.common.persistence.dao.CourseMapper;
import com.stylefeng.guns.common.persistence.dao.MaterialMapper;
import com.stylefeng.guns.common.persistence.model.Course;
import com.stylefeng.guns.common.persistence.model.Material;
import com.stylefeng.guns.common.persistence.model.Shuffling;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.log.LogObjectHolder;
import com.stylefeng.guns.core.util.FileUtil;
import com.stylefeng.guns.modular.system.dao.CourseDao;
import com.stylefeng.guns.modular.system.dao.MaterialDao;
import com.stylefeng.guns.modular.system.warpper.CourseWarpper;
import com.stylefeng.guns.modular.system.warpper.MaterialWarpper;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * material控制器
 *
 * @author fengshuonan
 * @Date 2017-07-21 09:52:03
 */
@Controller
@RequestMapping("/material")
public class MaterialController extends BaseController {

    private String PREFIX = "/system/material/";
    @Resource
    private MaterialDao materialDao;
    @Resource
    MaterialMapper materialMapper;
    @Resource
    private GunsProperties gunsProperties;
    /**
     * 跳转到material首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "material.html";
    }

    /**
     * 跳转到添加material
     */
    @RequestMapping("/material_add")
    public String materialAdd() {
        return PREFIX + "material_add.html";
    }

    /**
     * 跳转到修改material
     */
    @RequestMapping("/material_update/{materialId}")
    public String materialUpdate(@PathVariable Integer materialId, Model model) {
    	Material material = this.materialMapper.selectById(materialId);
          model.addAttribute("material",material);
          LogObjectHolder.me().set(material);
        return PREFIX + "material_edit.html";
    }

    /**
     * 获取material列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
    	 List<Map<String, Object>> list = this.materialDao.list(condition);
         return super.warpObject(new MaterialWarpper(list));
    }

    /**
     * 新增material
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    @Permission
    @BussinessLog(value = "新增教材",key = "name",dict = Dict.MaterialMap)
    public Object add(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
    	 String pdfName = UUID.randomUUID().toString() + ".pdf";
    	 String path = "";
         try {
             String fileSavePath = gunsProperties.getFileUploadPath();
             path= fileSavePath+"pdf/"+pdfName;
             file.transferTo(new File(path));
         } catch (Exception e) {
        	 e.printStackTrace();
             throw new BussinessException(BizExceptionEnum.UPLOAD_FILE_ERROR);
         }
    	path ="pdf/"+pdfName;
    	String name =request.getParameter("name");
    	Material material = new Material();
    	material.setName(name);
    	material.setPath(path);
    	return this.materialMapper.insert(material);
    }

    /**
     * 删除material
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    @Permission
    @BussinessLog(value = "删除教材",key = "materialId",dict = Dict.DeleteDict)
    public Object delete(@RequestParam Integer materialId) {
    	 //缓存通知名称
        LogObjectHolder.me().set(ConstantFactory.me().getNoticeTitle(materialId));

        this.materialMapper.deleteById(materialId);
        return SUCCESS_TIP;
    }


    /**
     * 修改material
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    @Permission
    @BussinessLog(value = "修改教材",key = "id",dict = Dict.MaterialMap)
    public Object update(@RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest request) {
    	String materialId=request.getParameter("id");
    	String name =request.getParameter("name");
    	if(materialId==null || materialId.equals("")){
    		 throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
    	}
    	Material material = materialMapper.selectById(Integer.parseInt(materialId));
    	material.setName(name);
    	 if(file!=null){
    		 String pdfName = UUID.randomUUID().toString() + ".pdf";
        	 String path = "";
             try {
                 String fileSavePath = gunsProperties.getFileUploadPath();
                 path= fileSavePath+"pdf/"+pdfName;
                 file.transferTo(new File(path));
             } catch (Exception e) {
            	 e.printStackTrace();
                 throw new BussinessException(BizExceptionEnum.UPLOAD_FILE_ERROR);
             }
        	path ="pdf/"+pdfName;
        	material.setPath(path);
        	materialMapper.updateById(material);
    	 }else{
    		 materialMapper.updateById(material);
    	 }
    	return super.SUCCESS_TIP;
    }

    /**
     * material详情
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
        String path = gunsProperties.getFileUploadPath() +"material/"+filename;
        try {
            byte[] bytes = FileUtil.toByteArray(path);
            response.getOutputStream().write(bytes);
        }catch (Exception e){
            //如果找不到图片就返回一个默认图片
        	e.printStackTrace();
        }
    }
}
