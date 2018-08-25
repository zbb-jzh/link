package com.future.link.base.service;

import java.util.List;

import com.future.link.base.model.Menu;
import com.future.link.base.model.MenuBtn;
import com.future.link.common.Result;
import com.future.link.utils.CommonUtil;
import com.future.link.utils.Constant;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Db;

public class MenuService {
	
	public static final MenuService service = Enhancer.enhance(MenuService.class);
	
	/**
	 * 新增
	 * @param menu
	 * @return
	 */
	public Result add(Menu menu)
	{
		menu.setId(CommonUtil.getUUID());
		menu.setStatus(Constant.UN_DELETE);
		
		menu.save();
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 修改
	 * @param menu
	 * @return
	 */
	public Result update(Menu menu)
	{
		menu.update();
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public Result delete(String id)
	{
		Db.update("update base_menu set status = ? where id = ?", Constant.DELETEED, id);
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 查询所有菜单
	 * @return
	 */
	public Result queryList()
	{
		List<Menu> list = Menu.dao.find("select * from base_menu where status = ? and isLeaf = ? and  parentId is ? order by orderNum desc", Constant.UN_DELETE, Constant.IS_NOT_LEAF, Constant.ISNULL);
		if(list != null && list.size() > 0){
			for(Menu menu : list)
			{
				menu.setSubList(getChildrenRecursive(menu.getId()));
				menu.setBtnList(searchMenuBtn(menu.getId()));
			}
		}
		return new Result(Constant.SUCCESS, list);
	}
	
	/**
	 * 查询二级菜单
	 * @param parentId
	 * @return
	 */
	public List<Menu> getChildrenRecursive(String parentId)
	{
		List<Menu> list = Menu.dao.find("select * from base_menu where status = ? and parentId = ? order by orderNum desc", Constant.UN_DELETE, parentId);
		if(list != null && list.size() > 0){
			for(Menu menu : list)
			{
				menu.setSubList(getChildrenRecursive(menu.getId()));
				menu.setBtnList(searchMenuBtn(menu.getId()));
			}
		}
		return list;
	}
	
	/**
	 * 查询菜单，以及菜单关联的按钮
	 * @return
	 */
	public Result searchAllMenu()
	{
		List<Menu> list = Menu.dao.find("select * from base_menu where status = ? and isLeaf = ? order by orderNum desc", Constant.UN_DELETE, Constant.IS_NOT_LEAF);
		if(list != null && list.size() > 0){
			for(Menu menu : list){
				menu.setBtnList(searchMenuBtn(menu.getId()));
			}
		}
		return new Result(Constant.SUCCESS, list);
	}
	
	/**
	 * 查询菜单下的功能按钮
	 * @param menuId
	 * @return
	 */
	public List<MenuBtn> searchMenuBtn(String menuId)
	{
		List<MenuBtn> list = MenuBtn.dao.find("select * from base_menu_btn where status = ? and menuId = ? ", Constant.UN_DELETE, menuId);
		return list;
	}
	
	/**
	 * 新增菜单按钮
	 * @param btn
	 * @return
	 */
	public Result addBtn(MenuBtn btn)
	{
		btn.setId(CommonUtil.getUUID());
		btn.setStatus(Constant.UN_DELETE);
		
		btn.save();
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 更新菜单
	 * @param btn
	 * @return
	 */
	public Result updateBtn(MenuBtn btn)
	{
		btn.update();
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public Result deleteBtn(String id)
	{
		Db.update("update base_menu_btn set status = ? where id = ?", Constant.DELETEED, id);
		return new Result(Constant.SUCCESS);
	}
}
