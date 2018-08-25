package com.future.link.base.service;

import java.util.ArrayList;
import java.util.List;

import com.future.link.base.model.Menu;
import com.future.link.base.model.Role;
import com.future.link.base.model.RoleMenu;
import com.future.link.common.Result;
import com.future.link.utils.CommonUtil;
import com.future.link.utils.Constant;
import com.future.link.utils.ToolDateTime;
import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.tx.Tx;

public class RoleService {
	
	public static final RoleService service = Enhancer.enhance(RoleService.class);
	
	/**
	 * 新增
	 * @param menu
	 * @return
	 */
	@Before(Tx.class)
	public Result add(Role role, String menuIds)
	{
		role.setId(CommonUtil.getUUID());
		role.setStatus(Constant.UN_DELETE);
		role.setCreateTime(ToolDateTime.getDateByTime());
		role.save();
		
		addRolePermission(role.getId(), menuIds);
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 修改
	 * @param menu
	 * @return
	 */
	@Before(Tx.class)
	public Result update(Role role, String menuIds)
	{
		addRolePermission(role.getId(), menuIds);
		role.update();
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public Result delete(String id)
	{
		Db.update("update base_role set status = ? where id = ?", Constant.DELETEED, id);
		return new Result(Constant.SUCCESS);
	}
	
	/**
	 * 查询角色列表
	 * @return
	 */
	public Result queryList()
	{
		List<Role> list = Role.dao.find("select * from base_role where status = ?", Constant.UN_DELETE);
		return new Result(Constant.SUCCESS, list);
	}
	
	/**
	 * 跟进角色id查询角色关联的菜单
	 * @param roleId
	 * @return
	 */
	public Result searchRoleMenu(String roleId)
	{
		List<Menu> list = Menu.dao.find("select * from base_menu_btn bm where bm.id " +
                " in (select brm.menuId from base_role_menu brm where roleId = ?)", roleId);
		
		return new Result(Constant.SUCCESS, list);
	}
	
	/**
     * 添加角色权限
     *
     * @param roleId
     * @param permissionIds 英文逗号隔开的权限Id
     * @return
     */
    public List<RoleMenu> addRolePermission(String roleId, String permissionIds) {
        String permissionId[]=permissionIds.split(",");
        Db.update("delete from base_role_menu where roleId=?",roleId);
        List<RoleMenu> rolePermissions=new ArrayList<>();
        for(String id:permissionId){
        	RoleMenu rp = new RoleMenu();
            String uuid = CommonUtil.getUUID();
            rp.setId(uuid);
            rp.setRoleId(roleId);
            rp.setMenuId(id);
            rolePermissions.add(rp);
        }
        Db.batchSave(rolePermissions,rolePermissions.size());
        return rolePermissions;
    }

}
