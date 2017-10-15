package com.stylefeng.guns.modular.system.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.stylefeng.guns.common.annotion.log.BussinessLog;
import com.stylefeng.guns.common.constant.Dict;
import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import com.stylefeng.guns.config.properties.GunsProperties;
import com.stylefeng.guns.core.util.FileUtil;

@Controller
@RequestMapping("/editor")
public class EditorPictureController {
	  @Resource
	    private GunsProperties gunsProperties;
	  
	  
	  /**
		 * 跳转到404页面
		 *
		 * @author fengshuonan
		 */
		@BussinessLog(value = "上传文件", key = "title", dict = Dict.DocumentUploadDict)
		@RequestMapping(path = "/upload")
		@ResponseBody
		public void upload(@RequestParam(value = "upload", required = false) MultipartFile upload,
				HttpServletRequest request, HttpServletResponse response) {
			String fileName = upload.getOriginalFilename();
			try {
				String fileSavePath = gunsProperties.getFileUploadPath() + "editor/";
				upload.transferTo(new File(fileSavePath + fileName));
				// 将上传的图片的url返回给ckeditor
				String callback = request.getParameter("CKEditorFuncNum");
				String script = "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction(" + callback
						+ ", '" + "../../editor/" + fileName + "');</script>";
				response.setContentType("text/html; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("X-Frame-Options", "SAMEORIGIN");
				PrintWriter out = response.getWriter();
				out.println(script);
				out.flush();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new BussinessException(BizExceptionEnum.UPLOAD_ERROR);
			}
		}
	  
	  
	/**
     * 返回图片
     *
     * @author stylefeng
     * @Date 2017/5/24 23:00
     */
    @RequestMapping("/{filename:.+}")
    public void renderPicture(@PathVariable("filename") String filename, HttpServletResponse response) {
        String path = gunsProperties.getFileUploadPath() +"editor/"+filename;
        try {
            byte[] bytes = FileUtil.toByteArray(path);
            response.getOutputStream().write(bytes);
        }catch (Exception e){
            //如果找不到图片就返回一个默认图片
        	e.printStackTrace();
        }
    }
}
