package com.future.link.base.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.future.link.base.service.ImageManagerService;
import com.future.link.common.Result;
import com.future.link.utils.CommonUtil;
import com.future.link.utils.Constant;
import com.future.link.utils.FileUtil;
import com.jfinal.core.Controller;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.upload.UploadFile;

public class FileUploadController extends Controller{
	
	/**
     * 上传文件apk
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void uploadFile() throws Exception{

        UploadFile uploadFile = this.getFile(); //"file", "/data/bm/temp"
        File file = uploadFile.getFile();

        if(file== null) {
            this.renderJson(Result.flomErrorData(Constant.FILE_IS_NULL));
            return;
        }
        String type= FileUtil.getFileType(file.getName());
        String newName = file.getParent() + '/'+ CommonUtil.getUUID()+ '.'+ type;

        File f = new File(newName);
        //创建临时文件
        FileUtil.copyFile(file, f);

        String key = this.getPara("key");

        if(StrKit.isBlank(key)){
            //默认
            key = "system.version_path";
        }
        String dir = "app";
        String[]rUrl  =  FileUtil.uploadLocalAPP(f,key,dir,f.getName());

        Map obj = new HashMap();
        obj.put("error", 0);
        obj.put("relative", rUrl[0]);
        obj.put("absolute", rUrl[1]);
        this.renderJson(obj);
    }
    /**
     * 上传tup
     */
    @SuppressWarnings("unchecked")
    public void uploadImg() throws Exception {

        UploadFile uploadFile = this.getFile();//("upfile", "d:/");
        File file = uploadFile.getFile();

        if(file==null) {
            this.renderJson(Result.flomErrorData(Constant.DELETEED));
            return;
        }

        String type = null;
        /**
         * "localUrl"是kindEditor独有的参数，内容是用户上传的文件在用户电脑中的路径
         * 该方法，用该参数判断图片是普通上传，还是使用kindEditor，从而得到文件后缀名和返回不同的json对象
         */
        String localUrl = this.getPara("localUrl");
        if(StrKit.isBlank(localUrl)) {
            type = FileUtil.getFileType(file.getName());
        } else {
            type= FileUtil.getFileType(this.getPara("localUrl"));
        }
        String newName = file.getParent()+ '/'+ CommonUtil.getUUID()+ '.'+ type;
        File f = new File(newName);
        file.renameTo(f);
//			String rUrl = OpenPlatformManager.zoomJpg(f, bucket, path);
        String[] rUrl = null;
        String key = this.getPara("key");

        if(StrKit.isBlank(key)){
            //本地默认key   "system.image.localprocessor"
            key = Constant.SYSTEM_IMAGE_LOCALPROCESSOR;
            //云存储默认key
//				key = "system.image.processor";
        }

        rUrl = ImageManagerService.service.processImage(f , key);
        Map obj = new HashMap();
        if(StrKit.isBlank(localUrl)) {
            obj.put("error", 0);
            obj.put("relative", rUrl[0]);
            obj.put("absolute", rUrl[1]);
        } else {
            obj.put("error", 0);
            obj.put("url", rUrl[1]);
        }
        this.renderJson(obj);
    }

    /**
     * 获取上传url
     * @return
     * @throws Exception
     */
    public void getUploadUrl() throws Exception{
        String url = ImageManagerService.service.getUploadUrl();
        if(url!= null)  {
            this.renderJson(new Result(Constant.SUCCESS, url));
        } else {
            this.renderJson(Result.flomErrorData(Constant.PATH_IS_NULL));
        }
    }

    //返回app访问的接口 单独服务器
    public void getUploadAppUrl() throws Exception{

        String systemUrl = PropKit.use("fileUploadConfig.properties").get("system.app.url"); //InitParam.get(InitParam.SYSTEM_APP_URL);
        String prefix = PropKit.use("fileUploadConfig.properties").get("system.app.uploadUrlPrefix"); //InitParam.get(InitParam.SYSTEM_APP_UPLOADURLPREFIX);
        String rst = null;
        if(!systemUrl.endsWith("/") && !prefix.startsWith("/")) {
            rst= systemUrl+ '/'+ prefix;
        } else {
            rst = systemUrl+ prefix;
        }

        this.renderJson(new Result(Constant.SUCCESS, rst));
    }

    //返回图片处理对象
    @SuppressWarnings("unchecked")
    public void getparamCof() throws Exception{

        String key = this.getPara("key");
        if(StrKit.isBlank(key)){
            //本地默认key
            key = "system.image.qiniuprocessor";
        }
        String type = PropKit.use("fileUploadConfig.properties").get("system.image.processorType");
        if(StrKit.notBlank(type)){
            Map obj = new HashMap();
            //生成七牛配置文件
            if(type.equals("2")){
                obj.put("type", type);
                Map<String,String> map = ImageManagerService.service.processParamCof(key);
                obj.put("param", map);
            }else{
                obj.put("type", type);
                obj.put("param", "");

            }
            this.renderJson(new Result(Constant.SUCCESS, obj));
        }else{
            this.renderJson(Result.flomErrorData(Constant.OBJECT_IS_NULL));
        }
    }

}
