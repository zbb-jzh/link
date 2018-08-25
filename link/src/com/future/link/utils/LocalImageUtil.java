package com.future.link.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.im4java.core.GraphicsMagickCmd;
import org.im4java.core.IMOperation;

/**
 * 当地存储图片工具
 * @author qss
 *
 */
public class LocalImageUtil {
	
	
	/**
	 * 原始图压缩源图，
	 * @param srcPath 原始图路径
	 * @param desPath 源图路径
	 * @param width  原始图片宽度
	 * @param height  高度
	 * @param targetWidth  目标宽度
	 * @param targetHeight  目标高度
	 * @return
	 * @throws Exception
	 */
	public static LocalImageUtil.ImageInfo resizeImag(String srcPath, String desPath, int width, int height, int targetWidth, int targetHeight) throws Exception {
	/*	//处理配置宽度*参数
		if(targetWidth == 0){
			float ratioh = (float) targetHeight/height;//高度压缩比
			targetWidth = (int)(ratioh*width);//设置等比宽度
		}
		//处理配置高度*参数
		if(targetHeight == 0){
			float ratiow= (float) targetWidth / width; //高度压缩比
			targetHeight = (int)(ratiow * height);//设置等比高度
		}
		*/
		if (width <= 0 || height <= 0)
 			return null;
		
		ImageInfo imageInfo = new ImageInfo();
		
		IMOperation op = new IMOperation();
		op.addImage(srcPath);
		op.background("none");
		
		float ratiow = (float) targetWidth / width; // 宽度压缩比
		float ratioh = (float) targetHeight / height; // 高度压缩比
	
		//判断高度为*，或者宽度压缩比大于高度压缩比
		if(targetHeight == 0||ratiow >= ratioh){
			//算等比高度
			if(targetHeight == 0){
				targetHeight = (int)(ratiow * height);//设置等比高度
			}
			op.resize(targetWidth, null).gravity("center");  // 按目标宽度压缩图片
		
		//判断宽度为*，高度为某一特定的值，或者高度压缩比大于宽度压缩比
		}else if(targetWidth == 0||ratiow < ratioh){
			 
			if(targetWidth == 0){
				targetWidth = (int)(ratioh*width);//设置等比宽度
			}
			
			op.resize(null, targetHeight).gravity("center");  // 按高度进行压缩
		}
			
		
		imageInfo.setWidth(targetWidth);
		imageInfo.setHeight(targetHeight);
		
		op.extent(targetWidth, targetHeight);
		op.addImage(desPath);
 		GraphicsMagickCmd convert = new GraphicsMagickCmd("convert");
 		convert.run(op); 
 		
		return imageInfo;
	}
    /**
 	 * 居中切割图片(不支持gif图片切割) 1、如果源图宽高都小于目标宽高，则只压缩图片，不做切割
 	 * 2、如果源图宽高都大于目标宽度，则根据宽度等比压缩后再居中切割 3、其它条件下，则压缩图片（不做缩放）后再居中切割
 	 * 
 	 * 该方法在知道源图宽度（sw）和高度（sh）的情况下使用
 	 * 
 	 * @param srcPath
 	 *            源图路径
 	 * @param desPath
 	 *            切割图片保存路径
 	 * @param width
 	 * 
 	 *            图宽度
 	 * @param height
 	 *            图高度
 	 * @param targetWidth
 	 *            目标宽度
 	 * @param targetHeight
 	 *            切割目标高度
 	 * @throws Exception
 	 */
	public static LocalImageUtil.ImageInfo cropImageResize(String srcPath, String desPath, int width, int height, int targetWidth, int targetHeight) throws Exception {

		//处理配置宽度*参数
		if(targetWidth == 0){
			float ratioh = (float) targetHeight/height;//高度压缩比
			targetWidth = (int)(ratioh*width);//设置等比宽度
		}
		//处理配置高度*参数
		if(targetHeight == 0){
			float ratiow= (float) targetWidth / width; //高度压缩比
			targetHeight = (int)(ratiow * height);//设置等比高度
		}
		
		if (width <= 0 || height <= 0)
 			return null;
 		ImageInfo imageInfo = new ImageInfo(); 
 		IMOperation op = new IMOperation();
 		op.addImage();
 		
 		float ratiow = (float) targetWidth / width; // 宽度压缩比
		float ratioh = (float) targetHeight / height; // 高度压缩比
		//处理配置宽度*参数,仅压缩
 		if(targetWidth==0){
 			if(targetWidth == 0){
				targetWidth = (int)(ratioh*width);//设置等比宽度
			}
			
			op.resize(null, targetHeight); // 按高度进行压缩
 		//处理配置高度*参数，仅压缩
 		}else if(targetHeight == 0){
 			//算等比高度
			if(targetHeight == 0){
				targetHeight = (int)(ratiow * height);//设置等比高度
			}
			op.resize(targetWidth, null); // 按目标宽度压缩图片
		//处理宽度和高度都的参数
 		}else{
 			
 			if(width < targetWidth || height < targetHeight) {
 	 			if(ratiow > ratioh) {
 	 				width = (int)(width*ratiow);
 	 	 			height = (int)(height*ratiow);
 	 			} else {
 	 				width = (int)(width*ratioh);
 	 	 			height = (int)(height*ratioh);
 	 			}
 	 			op.resize(width, height);
 	 			//更新比例
 	 			ratiow = (float) targetWidth / width;
 	 			ratioh = (float) targetHeight / height;
 	 			if (ratiow >= ratioh) // 宽度压缩比小（等）于高度压缩比（是宽小于高的图片）
 	 			{
 	 				int ch = (int) (ratiow * height); // 压缩后的图片高度
 	 				op.append().crop(targetWidth, targetHeight, 0, (ch > targetHeight) ? ((ch - targetHeight) / 2) : 0); // 根据高度居中切割压缩后的图片
 	 			} else // （宽大于高的图片）
 	 			{
 	 				int cw = (int) (ratioh * width); // 压缩后的图片宽度
 	 				op.append().crop(targetWidth, targetHeight, (cw > targetWidth) ? ((cw - targetWidth) / 2) : 0, 0); // 根据宽度居中切割压缩后的图片
 	 			}
 	 		} else  {
 	 			if (ratiow >= ratioh) // 宽度压缩比小（等）于高度压缩比（是宽小于高的图片）
 	 			{
 	 				int ch = (int) (ratiow * height); // 压缩后的图片高度

 	 				op.resize(targetWidth, null); // 按目标宽度压缩图片
 	 				op.append().crop(targetWidth, targetHeight, 0, (ch > targetHeight) ? ((ch - targetHeight) / 2) : 0); // 根据高度居中切割压缩后的图片
 	 			} else{ // （宽大于高的图片）
 	 				int cw = (int) (ratioh * width); // 压缩后的图片宽度
 	 				op.resize(cw, null); // 按计算的宽度进行压缩
 	 				op.append().crop(targetWidth, targetHeight, (cw > targetWidth) ? ((cw - targetWidth) / 2) : 0, 0); // 根据宽度居中切割压缩后的图片
 	 				
 	 			}
 	 		}
 		}
 		
 		
 		imageInfo.setWidth(targetWidth);
		imageInfo.setHeight(targetHeight);
		
 		op.background("none");
 		op.extent(targetWidth, targetHeight);
 		op.addImage();
 		GraphicsMagickCmd convert = new GraphicsMagickCmd("convert");
 		convert.run(op, srcPath, desPath);
 		
		return imageInfo;
 	}
	
	/**
	 * 当原始图和源图相同时，图片复制给源图
	 */
	public static void copyImage(String srcPath, String desPath) {

		try {
			FileChannel srcChannel = new FileInputStream(srcPath).getChannel();
			FileChannel dstChannel = new FileOutputStream(desPath).getChannel();
			  
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			srcChannel.close();
			dstChannel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static class ImageInfo {
		
		
		private int height;//原图压缩后的高度
		
		private int width;//原图压缩后的宽度

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}
	}
}
