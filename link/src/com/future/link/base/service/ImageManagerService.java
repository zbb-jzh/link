package com.future.link.base.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.future.link.base.model.ImageCfg;
import com.future.link.base.model.ImageCfgItem;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;

/**
 * Created by zhengbingbing on 2016/3/22.
 */
public class ImageManagerService{

    public static final ImageManagerService service = Enhancer.enhance(ImageManagerService.class);

    private Map<String, ImageCfg> cfgCacheMap = new HashMap<String, ImageCfg>();

    private ImageCfg getCfg(String key) {

        String originalStr = PropKit.use("fileUploadConfig.properties").get(key);
        //获取配置为空，返回null
        if(StrKit.isBlank(originalStr)){
            return null;
        }
        ImageCfg  cfg = cfgCacheMap.get(key);
        if(cfg!= null) {
            if(cfg.getOriginalCfg().equals(originalStr)) {
                return cfg;
            }
        }
        cfg= new ImageCfg(originalStr);

        //解析图片
        String[] cfgStrArr = originalStr.split("\\|");

        if(cfgStrArr==null){
            return null;
        }
        String path = null;
        path=cfgStrArr[0];

        //路径
        cfg.setPath(path);

        int lastIndex = path.lastIndexOf('/');
        if(lastIndex== -1) {
            return null;
        }

        String bucket = path.substring(0, lastIndex);
        String subPath = path.substring(lastIndex+1);
        //百度为bucket
        cfg.setBucket(bucket);
        //子路径
        cfg.setSubPath(subPath);

        //图片信息
        List<ImageCfgItem> cfgItemList = new ArrayList<ImageCfgItem>();

        for(int i = 1;i<cfgStrArr.length;i++){
            cfgItemList.add(this.processCfgItem(cfgStrArr[i]));
        }

        cfg.setList(cfgItemList);

        return cfg;
    }

    /**
     *
     * @param item
     * @return cfgItem
     */
    private ImageCfgItem processCfgItem(String item) {

        ImageCfgItem cfgItem = new ImageCfgItem();
        String[] itemArr = item.split(":");

        String size = itemArr[0];
        //图片大小
        String[] sizeArr = size.split(",");
        int width = 0;//默认宽度为0
        int height = 0;//默认高度为0
        //判断sizeArr[0]为*，
        if("*".equals(sizeArr[0])){
            width = 0;
        }else{
            width = Integer.parseInt(sizeArr[0].trim());
        }

        //判断sizeArr[1]为*,高度默0，在后面进行处理
        if("*".equals(sizeArr[1])){
            height = 0;
        }else{
            height = Integer.parseInt(sizeArr[1].trim());
        }

        String subPathAndQiniuParam = itemArr[1];//图片路径或路径和 七牛图片处理参数
        String []spAqnpArr =  subPathAndQiniuParam.split("\\[");

        //图片路径
        String subPath = spAqnpArr[0];
        String paramCof = null;

        //七牛参数
        if(spAqnpArr.length>1){
            String tempParamCof = spAqnpArr[1];
            //删除最后一个字符‘]’
            paramCof = tempParamCof.substring(0, tempParamCof.length()-1);
        }

        //图片是否返回
        if(itemArr.length>2) {
            Boolean isReturn = false ;
            String returnValue = itemArr[2];
            if(returnValue.equals("1")){
                isReturn = true;
            }
            cfgItem.setReturn(isReturn);
        }

        cfgItem.setWidth(width);
        cfgItem.setHeight(height);
        cfgItem.setSubPath(subPath);
        cfgItem.setParamCof(paramCof);

        return cfgItem;
    }

    public String processAbsUrl(String relUr) {
        //return ImagePorcessQcloudService.service.processAbsUrl(relUr);
        return ImagePorcessManagerService.processAbsUrl(relUr);
    }

    public String getUploadUrl() {
        return ImagePorcessManagerService.getUploadUrl();
    }

    public String getWXUploadUrl() {

        return ImagePorcessManagerService.getWXUploadUrl();
    }

    public String[] processImage(File source, String key) {

        String[] rst= null;

        try{
            ImageCfg cfg = this.getCfg(key);
            if(cfg!= null) {
                //rst = ImagePorcessQcloudService.service.process(source, cfg);
                
                rst = ImagePorcessManagerService.process(source, cfg);
            }
        }
        finally{
            source.delete();
        }
        return rst;
    }

    //封装路径:七牛或万象优图配置参数map
    public Map<String, String> processParamCof(String key) {
        Map<String, String> map = new HashMap<String, String>();

        ImageCfg cfg = this.getCfg(key);

        List<ImageCfgItem> lst = cfg.getList();
        for(int i = 0;i<lst.size();i++){
            ImageCfgItem item =lst.get(i);
            map.put(item.getSubPath(), item.getParamCof());
        }
        return map;
    }
}
