package com.future.link.common;

import java.util.Map;
import com.jfinal.kit.PropKit;
import com.future.link.utils.ToolDateTime;

public class Result {
	public static final int SUCCESS_STATUS = 1;

    public Result(){
        super();
    }
    /**
     * 生成返回对象
     *
     * @param status 状态、错误码
     */
    public Result(int status) {
        super();
        this.status = status;
        this.timestamp = ToolDateTime.getDateByTime();
    }

    /**
     * 生成带数据的返回对象
     *
     * @param status 状态、错误码
     * @param data   封装的数据
     */
    public Result(int status, Object data) {
        super();
        this.timestamp = ToolDateTime.getDateByTime();
        this.status = status;
        this.data = data;
    }

    /**
     * 生成带数据和拓展信息的返回对象
     *
     * @param status 状态、错误码
     * @param data   封装的数据
     * @param extend 拓展信息
     */
    public Result(int status, Object data, Map extend) {
        super();
        this.timestamp = ToolDateTime.getDateByTime();
        this.status = status;
        this.data = data;
        this.extend = extend;
    }

    /**
     * 生成错误返回数据
     * @param status 错误码
     * @return FlomData
     */
    public static Result flomErrorData(int status)
    {
        String info = PropKit.use("Code.properties").get(String.valueOf(status));
        Result result = new Result(status,info);
        return result;
    }
    public static Result flomErrorData(int status,String data){
    	Result result = new Result(status,data);
        return result;
    }
    /**
     * 快速返回成功数据和成功状态
     *
     * @param data
     * @return
     */
    public static Result flomSuccessData(Object data)
    {
    	Result result=new Result(SUCCESS_STATUS,data);
        return result;
    }
    /**
     * 快速返回成功状态
     *
     * @return
     */
    public static Result flomSuccess()
    {
    	Result result=new Result(SUCCESS_STATUS);
        return result;
    }

    /**
     * 判断当前结果是否是成功的
     * @return
     */
    public boolean isSuccess(){
        return SUCCESS_STATUS==this.status;
    }
    //请求的状态、错误码
    private int status;
    //数据或列表
    private Object data;
    // 时间戳，对象返回的时间
    private long timestamp;
    //扩展信息，map结构，可存放额外信息，如分页数，数据总量等
    private Map extend;

    public Map getExtend() {
        return extend;
    }

    public void setExtend(Map extend) {
        this.extend = extend;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
