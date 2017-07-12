package com.stylefeng.guns.core.util;

import com.stylefeng.guns.common.exception.BizExceptionEnum;
import com.stylefeng.guns.common.exception.BussinessException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil {
	
	private static Logger log = Logger.getLogger(FileUtil.class);
	
	/**
	 * NIO way
	 */
	public static byte[] toByteArray(String filename) {

		File f = new File(filename);
		if (!f.exists()) {
			log.error("文件未找到！" + filename);
			throw new BussinessException(BizExceptionEnum.FILE_NOT_FOUND);
		}
		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			throw new BussinessException(BizExceptionEnum.FILE_READING_ERROR);
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				throw new BussinessException(BizExceptionEnum.FILE_READING_ERROR);
			}
			try {
				fs.close();
			} catch (IOException e) {
				throw new BussinessException(BizExceptionEnum.FILE_READING_ERROR);
			}
		}
	}
	public static boolean isImage(File file){  
        if(file.isDirectory()){  
            return false;  
        }  
        String fileName = file.getName();  
        int len = fileName.indexOf(".");  
        String imagesPattern  = fileName.substring(len+1,fileName.length()).toLowerCase();  
        if("jpg".equals(imagesPattern)){  
            return true;  
        }else if("bmp".equals(imagesPattern)){  
            return true;  
        }else if("gif".equals(imagesPattern)){  
            return true;  
        }else if("psd".equals(imagesPattern)){  
            return true;  
        }else if("pcx".equals(imagesPattern)){  
            return true;  
        }else if("png".equals(imagesPattern)){  
            return true;  
        }else if("dxf".equals(imagesPattern)){  
            return true;  
        }else if("cdr".equals(imagesPattern)){  
            return true;  
        }else if("ico".equals(imagesPattern)){  
            return true;  
        }else if("bmp".equals(imagesPattern)){  
            return true;  
        }else if("jpeg".equals(imagesPattern)){  
            return true;  
        }else if("svg".equals(imagesPattern)){  
            return true;  
        }else if("wmf".equals(imagesPattern)){  
            return true;  
        }else if("emf".equals(imagesPattern)){  
            return true;  
        }else if("eps".equals(imagesPattern)){  
            return true;  
        }else if("tga".equals(imagesPattern)){  
            return true;  
        }else if("nef".equals(imagesPattern)){  
            return true;  
        }else if("tif".equals(imagesPattern)){  
            return true;  
        }else if("tiff".equals(imagesPattern)){  
            return true;  
        }else{  
            return false;  
        } 
        }
}