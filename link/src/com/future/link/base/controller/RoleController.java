package com.future.link.base.controller;

import org.apache.commons.lang3.StringUtils;

import com.future.link.base.model.Role;
import com.future.link.base.service.RoleService;
import com.future.link.common.Result;
import com.future.link.utils.Constant;
import com.jfinal.core.Controller;

public class RoleController extends Controller{
	
	/**
	 * 新增
	 */
	public void doAdd()
	{
		Role role = new Role();
		
		String permissionIds = this.getPara("permissionIds");
        String name = this.getPara("name");
        String description = this.getPara("description");
        
        role.setName(name);
        role.setDescription(description);
		
		renderJson(RoleService.service.add(role, permissionIds));
	}
	
	/**
	 * 修改
	 */
	public void doUpdate()
	{
		Role role = new Role();
		
		String permissionIds = this.getPara("permissionIds");
		String id = this.getPara("id");
        String name = this.getPara("name");
        String description = this.getPara("description");
        role.setId(id);
        role.setName(name);
        role.setDescription(description);
        
		renderJson(RoleService.service.update(role, permissionIds));
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
		renderJson(RoleService.service.delete(id));
	}
	
	/**
	 * 查询角色列表
	 */
	public void queryList()
	{
		renderJson(RoleService.service.queryList());
	}
	
	/**
	 * 跟进角色id查询角色关联的菜单
	 */
	public void searchRoleMenu()
	{
		String id = this.getPara("id");
		if(StringUtils.isBlank(id) || StringUtils.isEmpty(id))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		renderJson(RoleService.service.searchRoleMenu(id));
	}
}
