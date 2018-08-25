package com.future.link.goods.controller;

import java.io.BufferedReader;
import java.io.IOException;

import com.future.link.base.interceptor.AuthorityInterceptor;
import com.future.link.common.Result;
import com.future.link.goods.model.Goods;
import com.future.link.goods.model.GoodsSeach;
import com.future.link.goods.service.GoodsService;
import com.future.link.utils.Constant;
import com.future.link.utils.JsonUtil;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

@Before(AuthorityInterceptor.class)
public class GoodsController extends Controller{
	
	/**
	 * 新增
	 */
	public void doAdd() throws IOException
	{
		/**
         * 获取请求中的json
         */
        StringBuilder json = new StringBuilder();
        BufferedReader reader = this.getRequest().getReader();
        String line = null;
        while((line = reader.readLine()) != null){
            json.append(line);
        }
        reader.close();
        Goods goods = JsonUtil.parseJsonToObject(json.toString(), Goods.class);
		renderJson(GoodsService.service.add(goods));
	}
	
	/**
	 * 更新
	 */
	public void doUpdate() throws IOException
	{
		/**
         * 获取请求中的json
         */
        StringBuilder json = new StringBuilder();
        BufferedReader reader = this.getRequest().getReader();
        String line = null;
        while((line = reader.readLine()) != null){
            json.append(line);
        }
        reader.close();
        Goods goods = JsonUtil.parseJsonToObject(json.toString(), Goods.class);
		renderJson(GoodsService.service.update(goods));
	}
	
	/**
	 * 获取商品数据
	 */
	public void doGet()
	{
		String id = this.getPara("id");
		
		if(StrKit.isBlank(id))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		renderJson(GoodsService.service.getById(id));
	}
	
	/**
	 * 删除
	 */
	public void doDelete()
	{
		String id = this.getPara("id");
		
		if(StrKit.isBlank(id))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		renderJson(GoodsService.service.deleteById(id));
	}
	
	/**
	 * 上下架
	 */
	public void changeSellStatus()
	{
		String id = this.getPara("id");
		String sellStatus = this.getPara("sellStatus");
		if(StrKit.isBlank(id))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		if(StrKit.isBlank(sellStatus))
		{
			renderJson(Result.flomErrorData(Constant.STATUS_IS_NULL));
			return;
		}
		renderJson(GoodsService.service.changeSellStatus(id, sellStatus));
	}
	
	/**
	 * 分页查询
	 */
	public void doPage() throws IOException
	{
		/**
         * 获取请求中的json
         */
        StringBuilder json = new StringBuilder();
        BufferedReader reader = this.getRequest().getReader();
        String line = null;
        while((line = reader.readLine()) != null){
            json.append(line);
        }
        reader.close();
        GoodsSeach seach = JsonUtil.parseJsonToObject(json.toString(), GoodsSeach.class);

        this.renderJson(new Result(Constant.SUCCESS, GoodsService.service.page(seach)));
	}

}
