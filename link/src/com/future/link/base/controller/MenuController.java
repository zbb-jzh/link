package com.future.link.base.controller;

import org.apache.commons.lang3.StringUtils;

import com.future.link.base.model.Menu;
import com.future.link.base.model.MenuBtn;
import com.future.link.base.service.MenuService;
import com.future.link.common.Result;
import com.future.link.utils.Constant;
import com.jfinal.core.Controller;

public class MenuController extends Controller{
	
	/**
	 * 新增
	 */
	public void doAdd()
	{
		Menu menu = this.getModel(Menu.class);
		if(menu == null)
		{
			renderJson(Result.flomErrorData(Constant.OBJECT_IS_NULL));
			return;
		}
		renderJson(MenuService.service.add(menu));
	}
	
	/**
	 * 修改
	 */
	public void doUpdate()
	{
		Menu menu = this.getModel(Menu.class);
		if(menu == null)
		{
			renderJson(Result.flomErrorData(Constant.OBJECT_IS_NULL));
			return;
		}
		renderJson(MenuService.service.update(menu));
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
		renderJson(MenuService.service.delete(id));
	}
	
	/**
	 * 查询,树形结构
	 */
	public void queryList()
	{
		renderJson(MenuService.service.queryList());
	}
	
	/**
	 * 查询所有菜单
	 */
	public void searchAllMenu()
	{
		renderJson(MenuService.service.searchAllMenu());
	}
	
	/**
	 * 添加按钮
	 */
	public void doAddBtn()
	{
		MenuBtn menuBtn = this.getModel(MenuBtn.class);
		if(menuBtn == null)
		{
			renderJson(Result.flomErrorData(Constant.OBJECT_IS_NULL));
			return;
		}
		renderJson(MenuService.service.addBtn(menuBtn));
	}
	
	/**
	 * 更新按钮
	 */
	public void doUpdateBtn()
	{
		MenuBtn menuBtn = this.getModel(MenuBtn.class);
		if(menuBtn == null)
		{
			renderJson(Result.flomErrorData(Constant.OBJECT_IS_NULL));
			return;
		}
		renderJson(MenuService.service.updateBtn(menuBtn));
	}
	
	/**
	 * 删除按钮
	 */
	public void doDeleteBtn()
	{
		String id = this.getPara("id");
		if(StringUtils.isBlank(id) || StringUtils.isEmpty(id))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		renderJson(MenuService.service.deleteBtn(id));
	}
	
}
