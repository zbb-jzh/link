package com.future.link.base.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;

import com.future.link.base.model.ImageCfg;
import com.future.link.base.model.ImageCfgItem;
import com.future.link.utils.Constant;
import com.future.link.utils.LocalImageUtil;
import com.jfinal.kit.PropKit;

public class ImagePorcessManagerService {
	
	/**
	 * 
	 * 压缩文件先下载文件，然后得到像素在压缩，
	 * @param source 图片本地文件
	 * @param cfg 图片裁剪的配置
	 * 
	 */
	public static String[] process(File source, ImageCfg cfg) {
		
		//图片本地访问地址的前缀
		String systemUrl =  PropKit.use("fileUploadConfig.properties").get(Constant.SYSTEM_IMAGE_URL);//InitParam.get(InitParam.SYSTEM_IMAGE_URL);
		
		FileInputStream fis = null;
		BufferedImage buff;
		String []rst = new String[2];
		
		String bucket = cfg.getBucket();//图片路径
		String subPath = cfg.getSubPath();
		String imageName = source.getName();
		//若子路径subPath的第一个字符为/ 则不需要加/
		if(subPath.indexOf("/") != 0 ){
			subPath = "/"+subPath;
		}
		try {
			fis = new FileInputStream(source);
			buff = ImageIO.read(fis);
			int width = buff.getWidth();
			int height = buff.getHeight(); 
			
			String localPath = source.getAbsolutePath();	//上传图片绝对地址
			//1.原始图片 压缩成源图
			ImageCfgItem orignItem = cfg.getList().get(0);
			
			int orignWidth = orignItem.getWidth();
			int orignHeight = orignItem.getHeight();
		/*	//兼容kindeditor图片处理
			if(orignHeight == 0){
				float ratiow = (float) orignWidth / width; // 宽度压缩比
				orignHeight = (int)(ratiow * height);//设置等比宽度
			}*/
			
			//1.1新建源图目录
			String orgPathStr = bucket+subPath + "/"+orignItem.getSubPath() ; 
			orgPathStr = orgPathStr.toString();
			File orignFile = new File(orgPathStr);
			if(!orignFile.exists()){
				orignFile.mkdirs();
			}
			orgPathStr += "/" + imageName;
			
			//1.2  处理图片 
			LocalImageUtil.ImageInfo pic;
			try {
				if((orignWidth >= width)||(orignHeight >= height)){
					LocalImageUtil.copyImage(localPath, orgPathStr);
					orignWidth = width;
					orignHeight = height;
					
				}else{
					pic = LocalImageUtil.resizeImag(localPath,orgPathStr,width,height,orignWidth,orignHeight);
					orignWidth = pic.getWidth();
					orignHeight = pic.getHeight();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			//prefixUrl 为根目录    则为空
			String relOrignUrl =  subPath + "/" + orignItem.getSubPath()  + "/" + imageName;//返回前台图片相对url
			
			if(relOrignUrl.indexOf("/") == 0){
				relOrignUrl = relOrignUrl.substring(1);
			}
			if(!systemUrl.endsWith("/")) {
				systemUrl+= '/';
			}
			systemUrl += PropKit.use("fileUploadConfig.properties").get(Constant.SYSTEM_FILE_SHOWURLPREFIX);//InitParam.get(InitParam.SYSTEM_FILE_SHOWURLPREFIX);
			
			if(!systemUrl.endsWith("/")) {
				systemUrl+= '/';
			}
			
			String absOrignUrl = systemUrl + relOrignUrl;//返回前台图片绝对url
			
			//将处理好的图片上传到万象优图
//			FileInputStream temp = new FileInputStream(orgPathStr);
//			String result = QcloudUtil.upload(temp, relOrignUrl);
			
			//2.源图压缩或者裁剪大、中、小、缩略和其他图片
			if(cfg.getList().size()>1){
				rst = processItem(orgPathStr,cfg,bucket,subPath,imageName,orignWidth,orignHeight,systemUrl);
			}
			//若无其他图片返回 ，则默认返回源图
			if(rst[0] == null){
				rst[0] = relOrignUrl;
				rst[1] = absOrignUrl;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fis);
		}
		return rst;
	}
	/**
	 * 按宽度和高度压缩大、中或者小三种，如果需要缩略
	 * 根据宽度高度压缩一个缩略图以及其他格式图片
	 * 
	 * @param orgPathStr 源图url
	 * @param cfg 图片裁剪的配置
	 * @param bucket 
	 * @param subPath
	 * @param orignWidth 源图宽度
	 * @param orignHeight 源图高度
	 * @param iamgeName 图片名称 
	 * @return
	 */
	private static String[] processItem(String orgPathStr, ImageCfg cfg,String bucket,String subPath,String imageName,int orignWidth,int orignHeight, String systemUrl ){
		
		String []subRst = new String[2]; 
		String temPath = orgPathStr; 
	
		for(int i=1; i<cfg.getList().size();i++){//遍历配置图片信息
			ImageCfgItem item = cfg.getList().get(i);
			String imagePathStr = bucket+subPath + "/"+item.getSubPath() ;
			//新建目录
			imagePathStr = imagePathStr.toString(); 
			File imageFile = new File(imagePathStr);
			if(!imageFile.exists()){
				imageFile.mkdirs();
			}
			imagePathStr += "/" + imageName;
			
			LocalImageUtil.ImageInfo subPic;
			try {
//				if((item.getWidth()>=orignWidth)&&(item.getHeight() >= orignHeight)){
//					LocalImageUtil.copyImage(temPath, imagePathStr);
//				}else{
					subPic = LocalImageUtil.cropImageResize(temPath,imagePathStr,orignWidth,orignHeight,item.getWidth(),item.getHeight());
					orignWidth = subPic.getWidth();
					orignHeight = subPic.getHeight();
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			temPath = imagePathStr;
			
			if(item.isReturn()){
				
				String relSubUrl = subPath +"/"+item.getSubPath()+"/"+ imageName;

				if(relSubUrl.indexOf("/") == 0){
					relSubUrl = relSubUrl.substring(1, relSubUrl.length());
				}

				if(!systemUrl.endsWith("/")) {
					systemUrl+= '/';
				}
				
				String absSubUrl = systemUrl + relSubUrl;
				
				subRst[0] = relSubUrl;
				subRst[1] = absSubUrl;
			}
			
			//将处理好的图片上传到万象优图
//			FileInputStream temp;
//			try {
//				temp = new FileInputStream(imagePathStr);
//				String result = QcloudUtil.upload(temp, (subPath +"/"+item.getSubPath()+"/"+ imageName).substring(1));
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
		}
		return subRst;
	}
	 
	public static String processAbsUrl(String relUr) {
		
		if(relUr == null || relUr.equals("")) {
			return null;
		}
		//图片本地访问地址的前缀
		String systemUrl = PropKit.use("fileUploadConfig.properties").get(Constant.SYSTEM_IMAGE_URL);
		
		
		if(!systemUrl.endsWith("/")) {
			systemUrl+= '/';
		}
		systemUrl+= PropKit.use("fileUploadConfig.properties").get(Constant.SYSTEM_FILE_SHOWURLPREFIX);
		if(!systemUrl.endsWith("/")) {
			systemUrl+= '/';
		}
		String imageAbsUrl = relUr.replace(",", ","+ systemUrl);
		
		return systemUrl+imageAbsUrl;
	}
	
	public static String getUploadUrl() {
		String systemUrl = PropKit.use("fileUploadConfig.properties").get(Constant.SYSTEM_IMAGE_URL);
		String prefix = PropKit.use("fileUploadConfig.properties").get(Constant.SYSTEM_IMAGE_UPLOADURLPREFIX);
		String rst = null;
		if(!systemUrl.endsWith("/") && !prefix.startsWith("/")) {
			rst= systemUrl+ '/'+ prefix;
		} else {
			rst = systemUrl+ prefix;
		}
		
		return rst;
	}
	
	public static String getWXUploadUrl() {
		String systemUrl = PropKit.use("fileUploadConfig.properties").get(Constant.SYSTEM_IMAGE_URL);
		String prefix = PropKit.use("fileUploadConfig.properties").get(Constant.SYSTEM_IMAGE_WXPREFIX);
		String rst = null;
		if(!systemUrl.endsWith("/") && !prefix.startsWith("/")) {
			rst= systemUrl+ '/'+ prefix;
		} else {
			rst = systemUrl+ prefix;
		}
		return rst;
	}
}
