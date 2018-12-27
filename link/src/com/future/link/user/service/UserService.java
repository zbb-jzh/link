package com.future.link.user.service;

import java.util.ArrayList;
import java.util.List;

import com.future.link.base.model.Menu;
import com.future.link.base.model.MenuBtn;
import com.future.link.base.model.Role;
import com.future.link.base.model.RoleMenu;
import com.future.link.base.model.UserRole;
import com.future.link.common.Result;
import com.future.link.user.model.User;
import com.future.link.utils.CommonUtil;
import com.future.link.utils.Constant;
import com.future.link.utils.MD5Util;
import com.future.link.utils.ToolDateTime;
import com.future.link.utils.ToolString;
import com.jfinal.aop.Before;
import com.jfinal.aop.Enhancer;
import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.tx.Tx;

public class UserService{
	public static final UserService service = Enhancer.enhance(UserService.class);
	
	/**
	 * 新增
	 * @param user
	 * @return
	 */
	@Before(Tx.class)
	public Result add(User user, String roleIds)
	{
		Result result = isExit(user.getName());
		if(result.getStatus() != Constant.SUCCESS)
		{
			return result;
		}
		//密码加密
		user.setPassword(MD5Util.generatePassword(user.getPassword()));
		user.setId(CommonUtil.getUUID());
		user.setStatus(Constant.UN_DELETE);
		user.setCreateTime(ToolDateTime.getDateByTime());
		user.setType(1);
		user.save();
		
		if(StrKit.notBlank(roleIds)){
			addUserRole(user.getId(), roleIds);
		}
		return new Result(Constant.SUCCESS, "添加成功");
	}
	
	/**
	 * 添加用户和角色关系
	 * @param userId
	 * @param roleIds
	 */
	public void addUserRole(String userId, String roleIds)
	{
		String roleArr [] = roleIds.split(",");
		Db.update("delete from base_user_role where userId = ?", userId);
        List<UserRole> userRoles = new ArrayList<>();
        for(String id : roleArr){
        	UserRole ur = new UserRole();
            String uuid = CommonUtil.getUUID();
            ur.setId(uuid);
            ur.setRoleId(id);
            ur.setUserId(userId);
            userRoles.add(ur);
        }
        Db.batchSave(userRoles,userRoles.size());
	}
	
	/**
	 * 根据ID获取数据
	 * @param id
	 * @return
	 */
	public Result getById(String id)
	{
		return new Result(Constant.SUCCESS, User.dao.findById(id));
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public Result deleteById(String id)
	{
		Db.update("update user_user set status = ? where id = ?", Constant.DELETEED, id);
		return new Result(Constant.SUCCESS, "删除成功");
	}
	
	/**
	 * 更新
	 * @param user
	 * @return
	 */
	@Before(Tx.class)
	public Result update(User user, String roleIds)
	{
//		Result result = isExit(user.getName());
//		if(result.getStatus() != Constant.SUCCESS)
//		{
//			return result;
//		}
		user.update();
		if(StrKit.notBlank(roleIds)){
			addUserRole(user.getId(), roleIds);
		}
		return new Result(Constant.SUCCESS, "修改成功");
	}
	
	/**
	 * 判断用户是否存在
	 * @param name
	 * @return
	 */
	public Result isExit(String name)
	{
		List<User> list = User.dao.find("select * from user_user where name = ? and status = 1", name);
		if(list != null && list.size() > 0 )
		{
			return new Result(Constant.HAS_EXIT, "已存在");
		}
		return new Result(Constant.SUCCESS, "不存在");
	}
	
	/**
     * 分页查询
     * @return
     */
    public Page<User> page(int pageNumber, int pageSize, String name){

        StringBuffer sql = new StringBuffer(" from user_user where status = ? ");
        List<Object> params = new ArrayList<>();
        params.add(Constant.UN_DELETE);
        if(StrKit.notBlank(name)){
            sql.append(" and name like ?");
            params.add("%" + name + "%");
        }
        sql.append(" order by createTime desc");
        Page<User> page = User.dao.paginate(pageNumber, pageSize, "select * ", sql.toString(), params.toArray());
        return page;
    }
    
    /**
     * 用户登录
     * @param name
     * @param password
     * @return
     */
    public Result login(String name, String password)
    {
    	if(isExit(name).getStatus() == Constant.SUCCESS)
    	{
    		return new Result(Constant.HAS_NOT_EXIT, "用户不存在");
    	}
    	Result result = verifyPassword(name, password);
    	
    	return result;
    }
    
    /**
     * 用户登录
     * @param name
     * @param password
     * @return
     */
    public Result checkTWoPwd(User user, String password)
    {
    	if(!MD5Util.validatePassword(user.getTwoPassword(), password))
    	{
    		return new Result(Constant.PWD_IS_ERROR, "密码错误");
    	}
    	
    	return  new Result(Constant.SUCCESS, user);
    }
    
    /**
     * 校验密码
     * @param name
     * @param password
     * @return
     */
    public Result verifyPassword(String name, String password)
    {
    	User user = User.dao.findFirst("select * from user_user where name = ? and status = 1", name);
    	if(MD5Util.validatePassword(user.getPassword(), password)){
    		return new Result(Constant.SUCCESS, user);
    	}else{
    		return new Result(Constant.PWD_IS_ERROR, "密码错误");
    	}
    }
    
    /**
     * 查询用户角色
     * @param userId
     * @return
     */
    public Result searchUserRole(String userId)
    {
    	List<Role> list = Role.dao.find("select * from base_role br where br.id " +
                " in (select bur.roleId from base_user_role bur where userId = ?)", userId);
    	
    	searchUserAuthority(userId);
		
		return new Result(Constant.SUCCESS, list);
    }
    
    /**
     * 查询用户权限
     * @return
     */
    public Result searchUserAuthority(String userId)
    {
    	//获取角色
    	List<RoleMenu> list = RoleMenu.dao.find("select * from base_role_menu brm where brm.roleId " +
                " in (select bur.roleId from base_user_role bur where userId = ?)", userId);
    	
    	//获取菜单按钮
    	List<MenuBtn> menuBtns = MenuBtn.dao.find("select * from base_menu_btn where id in (" + ToolString.idToSqlIn(list, "menuId", true) + ")" );
    	List<String> menuIds = new ArrayList<>();
    	List<Menu> menus = new ArrayList<>();
    	List<Menu> rootMenus = new ArrayList<>();
    	for(MenuBtn mb : menuBtns){
    		String menuId = mb.getMenuId();
    		if(!menuIds.contains(menuId)){
    			menuIds.add(menuId);
    			menus.add(Menu.dao.findById(menuId));
    		}
    	}
    	if(menus.size() > 0){
    		for(int i=0; i<menus.size(); i++){
    			List<MenuBtn> btnList = new ArrayList<>();
        		for(int j=0; j<menuBtns.size(); j++){
        			if(menus.get(i).getId().equals(menuBtns.get(j).getMenuId())){
        				btnList.add(menuBtns.get(j));
        			}
        		}
        		menus.get(i).setBtnList(btnList);
        		
        		Menu menu = Menu.dao.findById(menus.get(i).getParentId());
        		if(!rootMenus.contains(menu)){
        			rootMenus.add(menu);
        		}
        	}
    		
    		for(int i=0; i<rootMenus.size(); i++){
    			List<Menu> subList = new ArrayList<>();
    			for(int j=0; j<menus.size(); j++){
    				if(rootMenus.get(i).getId().equals(menus.get(j).getParentId())){
    					subList.add(menus.get(j));
    				}
    			}
    			rootMenus.get(i).setSubList(subList);
        	}
    	}
    	return new Result(Constant.SUCCESS, rootMenus);
    }
    
    /**
     * 查询叶子菜单
     * @return
     */
    public Result searchLeafMenu(){
    	List<Menu> leafMenus = Menu.dao.find("select * from base_menu  where status = ? and isLeaf = ?", Constant.UN_DELETE, Constant.IS_LEAF);
    	return new Result(Constant.SUCCESS, leafMenus);
    }
    
    /**
     * 修改密码
     * @param newPwd
     * @param oldPwd
     * @return
     */
    public Result modifyPwd(User user, String newPwd, String oldPwd){
    	
    	if(!MD5Util.validatePassword(user.getPassword(), oldPwd)){
    		return Result.flomErrorData(Constant.OLDPWD_IS_ERROR);
    	}
    	Db.update("update user_user set password = ? where id = ?", MD5Util.generatePassword(newPwd), user.getId());
    	return new Result(Constant.SUCCESS);
    }
    
    /**
     * 修改二级密码
     * @param newPwd
     * @param oldPwd
     * @return
     */
    public Result modifyTwoPwd(User user, String newPwd, String oldPwd){
    	
    	if(!MD5Util.validatePassword(user.getTwoPassword(), oldPwd)){
    		return Result.flomErrorData(Constant.OLDPWD_IS_ERROR);
    	}
    	Db.update("update user_user set twoPassword = ? where id = ?", MD5Util.generatePassword(newPwd), user.getId());
    	user.setTwoPassword(MD5Util.generatePassword(newPwd));
    	return new Result(Constant.SUCCESS, user);
    }
    
    
}
