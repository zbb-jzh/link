package com.future.link.base.model;

public class ImageCfgItem {
	
	/**
     * 图片高度
     */
    private int height;

    /**
     * 图片宽度
     */
    private int width;

    /**
     * 图片存储子路径
     */
    private String subPath;

    /**
     * 是否作为返回的路径
     */
    private boolean isReturn;

    /**
     * 七牛参数配置
     *
     */
    private String paramCof;

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

    public String getSubPath() {
        return subPath;
    }

    public void setSubPath(String subPath) {
        this.subPath = subPath;
    }

    public boolean isReturn() {
        return isReturn;
    }

    public void setReturn(boolean isReturn) {
        this.isReturn = isReturn;
    }

    public String getParamCof() {
        return paramCof;
    }

    public void setParamCof(String paramCof) {
        this.paramCof = paramCof;
    }

}
