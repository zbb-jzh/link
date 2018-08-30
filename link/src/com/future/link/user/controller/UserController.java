package com.future.link.user.controller;

import org.apache.commons.lang3.StringUtils;

import com.future.link.base.interceptor.AuthorityInterceptor;
import com.future.link.common.Result;
import com.future.link.user.model.User;
import com.future.link.user.service.UserService;
import com.future.link.utils.Constant;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.StrKit;

public class UserController extends Controller{
	
	/**
	 * 新增
	 */
	@Before(AuthorityInterceptor.class)
	public void doAdd()
	{
		User user = new User();
		String name = this.getPara("name");
		int sex = this.getParaToInt("sex");
		String password = this.getPara("password");
		String phone = this.getPara("phone");
		String email = this.getPara("email");
		String address = this.getPara("address");
		String roleIds = this.getPara("roleIds");
		user.setName(name);
		user.setSex(sex);
		user.setPassword(password);
		user.setPhone(phone);
		user.setEmail(email);
		user.setAddress(address);
		
		renderJson(UserService.service.add(user, roleIds));
	}
	
	/**
	 * 根据id获取
	 */
	@Before(AuthorityInterceptor.class)
	public void doGetById()
	{
		String id = this.getPara("id");
		
		if(StringUtils.isBlank(id) || StringUtils.isEmpty(id))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		
		renderJson(UserService.service.getById(id));
	}
	
	/**
	 * 更新
	 */
	@Before(AuthorityInterceptor.class)
	public void doUpdate()
	{
		User user = new User();//this.getModel(User.class);
		String id = this.getPara("id");
		String name = this.getPara("name");
		int sex = this.getParaToInt("sex");
		String password = this.getPara("password");
		String phone = this.getPara("phone");
		int salary = this.getParaToInt("salary");
		String post = this.getPara("post");
		String email = this.getPara("email");
		String address = this.getPara("address");
		String roleIds = this.getPara("roleIds");
		user.setId(id);
		user.setName(name);
		user.setSex(sex);
		user.setPassword(password);
		user.setPhone(phone);
		user.setSalary(salary);
		user.setPost(post);
		user.setEmail(email);
		user.setAddress(address);
		renderJson(UserService.service.update(user, roleIds));
	}
	
	/**
	 * 删除
	 */
	@Before(AuthorityInterceptor.class)
	public void doDelete()
	{
		String id = this.getPara("id");
		if(StringUtils.isBlank(id) || StringUtils.isEmpty(id))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		renderJson(UserService.service.deleteById(id));
	}
	
	/**
	 * 分页查询
	 */
	@Before(AuthorityInterceptor.class)
	public void doPage()
	{
		
		int pageNumber = this.getParaToInt("pageNumber");
        int pageSize = this.getParaToInt("pageSize");
        if(pageNumber == 0 || pageSize == 0){
            this.renderJson(new Result(-1001, "分数或每页数量为空"));
            return;
        }else{
            String name = this.getPara("name");
            this.renderJson(new Result(Result.SUCCESS_STATUS, UserService.service.page(pageNumber, pageSize, name)));
        }
	}
	
	/**
	 * 登录
	 */
	public void login()
	{
		String name = this.getPara("name");
		String password = this.getPara("password");
		Result result = UserService.service.login(name, password);
		if(result.getStatus() != Constant.SUCCESS){
			renderJson(result);
			return;
    	}
		User user = (User) result.getData();
		
		//将用户信息放在session里
		setSessionAttr(Constant.SESSION_USER, user);
		renderJson(result);
	}
	
	/**
	 * 校验二级密码
	 */
	@Before(AuthorityInterceptor.class)
	public void doCheckTWoPwd()
	{
		String password = this.getPara("password");
		User user=(User) this.getRequest().getSession().getAttribute(Constant.SESSION_USER);
		Result result = UserService.service.checkTWoPwd(user, password);
		
		if(result.getStatus() != Constant.SUCCESS){
			renderJson(result);
			return;
    	}
		setSessionAttr(Constant.CHECKPWD_FLAG, Constant.SUCCESS);
		renderJson(result);
	}
	
	/**
	 * 查询用户角色
	 */
	@Before(AuthorityInterceptor.class)
	public void searchUserRole()
	{
		String id = this.getPara("id");
		if(StringUtils.isBlank(id) || StringUtils.isEmpty(id))
		{
			renderJson(Result.flomErrorData(Constant.ID_IS_EMPTY));
			return;
		}
		renderJson(UserService.service.searchUserRole(id));
	}
	
	/**
	 * 查询用户权限
	 */
	@Before(AuthorityInterceptor.class)
	public void searchUserAuthority()
	{
		User user = getSessionAttr(Constant.SESSION_USER);
		if(user == null){
			renderJson(Result.flomErrorData(Constant.LOGIN_INVALID));
			return;
		}
		renderJson(UserService.service.searchUserAuthority(user.getId()));
	}
	
	/**
	 * 查询叶子菜单
	 */
	@Before(AuthorityInterceptor.class)
	public void searchLeafMenu(){
		renderJson(UserService.service.searchLeafMenu());
	}
	
	/**
	 * 修改密码
	 */
	@Before(AuthorityInterceptor.class)
	public void modifyPwd(){
		
		String oldPwd = this.getPara("oldPwd");
		String newPwd = this.getPara("newPwd");
		User user = getSessionAttr(Constant.SESSION_USER);
		if(user == null){
			renderJson(Result.flomErrorData(Constant.LOGIN_INVALID));
			return;
		}
		if(StrKit.isBlank(oldPwd) || StrKit.isBlank(newPwd))
		{
			renderJson(Result.flomErrorData(Constant.PWD_IS_EMPTY));
			return;
		}
		renderJson(UserService.service.modifyPwd(user, newPwd, oldPwd));
	}
	
	/**
	 * 修改二级密码
	 */
	@Before(AuthorityInterceptor.class)
	public void modifyTwoPwd(){
		
		String oldPwd = this.getPara("oldPwd");
		String newPwd = this.getPara("newPwd");
		User user = getSessionAttr(Constant.SESSION_USER);
		
		renderJson(UserService.service.modifyPwd(user, newPwd, oldPwd));
	}
}
