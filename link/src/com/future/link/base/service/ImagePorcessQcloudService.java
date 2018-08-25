package com.future.link.base.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.future.link.base.model.ImageCfg;
import com.future.link.utils.QcloudUtil;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.PropKit;

/**
 * Created by zhengbingbing on 2016/3/22.
 */
public class ImagePorcessQcloudService{

    public static final ImagePorcessQcloudService service = Enhancer.enhance(ImagePorcessQcloudService.class);

    public String[] process(File source, ImageCfg cfg) {
        String []rst = new String[2];

        String RelOrigUrl = null;//原始图相对路径
        String AbsOrigUrl = null;//原始图绝对路径

        String subPath = cfg.getSubPath();
        //若子路径subPath的第一个字符为/ 则不需要加/
        if(subPath.indexOf("/") != 0){
            subPath = "/"+subPath;
        }
        try {

            FileInputStream fileStream = null;
            fileStream = new FileInputStream(source.getPath());

            RelOrigUrl = QcloudUtil.upload(fileStream, System.currentTimeMillis()+ "");//相对路径
            AbsOrigUrl = QcloudUtil.transformToAbsolute(RelOrigUrl);//绝对路径

            //压缩图片无返回的路径，则返回返回源图片
            if(rst[0] == null){
                rst[0] = RelOrigUrl;
                rst[1] = AbsOrigUrl;
            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } finally {
        }
        return rst;
    }

    public String processAbsUrl(String relUr) {
        if(relUr == null || relUr.equals("")) {
            return null;
        }
        String bsUrl = PropKit.use("fileUploadConfig.properties").get("qcloud_down_url");
        String desUrl = relUr.replace(",", ","+ bsUrl);

        return bsUrl+ desUrl;
    }

    public String getUploadUrl() {
        String systemUrl = PropKit.use("fileUploadConfig.properties").get("system.image.url"); //InitParam.get(InitParam.SYSTEM_IMAGE_URL);
        String prefix = PropKit.use("fileUploadConfig.properties").get("system.image.uploadUrlPrefix");    //InitParam.get(InitParam.SYSTEM_IMAGE_UPLOADURLPREFIX);
        String rst = null;
        if(!systemUrl.endsWith("/") && !prefix.startsWith("/")) {
            rst= systemUrl+ '/'+ prefix;
        } else {
            rst = systemUrl+ prefix;
        }

        return rst;
    }

    public String getWXUploadUrl() {
        String systemUrl = PropKit.use("fileUploadConfig.properties").get("system.image.url"); //InitParam.get(InitParam.SYSTEM_IMAGE_URL);
        String prefix = PropKit.use("fileUploadConfig.properties").get("system.image.wxuploadUrlPrefix");    //InitParam.get(InitParam.SYSTEM_IMAGE_WXPREFIX);
        String rst = null;
        if(!systemUrl.endsWith("/") && !prefix.startsWith("/")) {
            rst= systemUrl+ '/'+ prefix;
        } else {
            rst = systemUrl+ prefix;
        }

        return rst;
    }
}
