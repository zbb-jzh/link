package com.future.link.goods.controller;

import org.apache.commons.lang3.StringUtils;

import com.future.link.base.interceptor.AuthorityInterceptor;
import com.future.link.common.Result;
import com.future.link.goods.model.Category;
import com.future.link.goods.service.CategoryService;
import com.future.link.utils.Constant;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

@Before(AuthorityInterceptor.class)
public class CategoryController extends Controller{
	
	/**
	 * 新增
	 */
	public void doAdd()
	{
		Category category = this.getModel(Category.class);
		
		if(category == null)
		{
			renderJson(Result.flomErrorData(Constant.OBJECT_IS_NULL));
			return;
		}
		renderJson(CategoryService.service.add(category));
	}
	
	/**
	 * 修改
	 */
	public void doUpdate()
	{
		Category category = this.getModel(Category.class);
		
		if(category == null)
		{
			renderJson(Result.flomErrorData(Constant.OBJECT_IS_NULL));
			return;
		}
		renderJson(CategoryService.service.update(category));
	}
	
	/**
	 * 删除
	 */
	public void doDelete()
	{
		String id = this.getPara("id");
		if(StringUtils.isBlank(id) || StringUtils.isEmpty(id))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		renderJson(CategoryService.service.delete(Long.parseLong(id)));
	}
	
	/**
	 * 获取一条数据
	 */
	public void doGet()
	{
		String id = this.getPara("id");
		if(StringUtils.isBlank(id) || StringUtils.isEmpty(id))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		renderJson(CategoryService.service.getById(id));
	}
	
	/**
	 * 根据商品id获取商品关联的分类
	 */
	public void doSearchGoodsCategory()
	{
		String goodsId = this.getPara("goodsId");
		if(StringUtils.isBlank(goodsId) || StringUtils.isEmpty(goodsId))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		renderJson(new Result(Constant.SUCCESS, CategoryService.service.searchCategoryByGoodsId(goodsId)));
	}
	
	/**
	 * 获取父类
	 */
	public void doGetParent()
	{
		renderJson(new Result(Constant.SUCCESS, CategoryService.service.getParent()));
	}
	
	/**
	 * 获取商品分类，树形结构
	 */
	public void doTree()
	{
		renderJson(CategoryService.service.tree());
	}
	
	/**
     * 查询二级分类
     */
    public void dosearchSec(){
        this.renderJson(new Result(Constant.SUCCESS, CategoryService.service.searchSec()));
    }
}
