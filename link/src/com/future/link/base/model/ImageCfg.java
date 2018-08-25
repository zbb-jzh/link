package com.future.link.base.model;

import java.util.List;

public class ImageCfg {
	
	/**
     * 配置的原始路径
     */
    private String path;

    /**
     * 百度的bucket
     */
    private String bucket;

    /**
     * bucket下的路径
     */
    private String subPath;

    /**
     * 原始配置的字符串，用来判断配置是否改变
     */
    private String originalCfg;

    private List<ImageCfgItem> list;

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<ImageCfgItem> getList() {
        return list;
    }

    public void setList(List<ImageCfgItem> list) {
        this.list = list;
    }

    public String getOriginalCfg() {
        return originalCfg;
    }


    public String getSubPath() {
        return subPath;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    private ImageCfg(){}

    public ImageCfg(String originalCfg){
        this.originalCfg = originalCfg;
    }

}
