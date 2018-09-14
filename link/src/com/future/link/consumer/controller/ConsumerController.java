package com.future.link.consumer.controller;

import com.future.link.base.interceptor.AuthorityInterceptor;
import com.future.link.base.interceptor.CheckTwoPwdInterceptor;
import com.future.link.common.Result;
import com.future.link.consumer.model.Consumer;
import com.future.link.consumer.model.Withdraw;
import com.future.link.consumer.service.ConsumerService;
import com.future.link.consumer.service.SalaryService;
import com.future.link.consumer.service.WithdrawService;
import com.future.link.user.model.User;
import com.future.link.utils.Constant;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

public class ConsumerController extends Controller{
	
	@Before(AuthorityInterceptor.class)
	public void doAdd()
	{
		Consumer consumer = this.getModel(Consumer.class);
		
		if(null == consumer)
		{
			renderJson(new Result(-1000, "数据为空"));
		}
		User user=(User) this.getRequest().getSession().getAttribute(Constant.SESSION_USER);
		renderJson(ConsumerService.service.add(consumer, user));
	}
	
	@Before(AuthorityInterceptor.class)
	public void doGetById()
	{
		String id = this.getPara("id");
		
		renderJson(ConsumerService.service.getById(id));
	}
	
	@Before(AuthorityInterceptor.class)
	public void doDelete()
	{
		String id = this.getPara("id");
		renderJson(ConsumerService.service.deleteById(id));
	}
	
	@Before(AuthorityInterceptor.class)
	public void doUpdate()
	{
		Consumer consumer = this.getModel(Consumer.class);
		if(null == consumer)
		{
			renderJson(new Result(-1000, "数据为空"));
		}
		renderJson(ConsumerService.service.update(consumer));
	}
	
	/**
	 * 客户修改个人资料
	 */
	@Before(AuthorityInterceptor.class)
	public void doUpdatePersonInfo()
	{
		Consumer consumer = this.getModel(Consumer.class);
		if(null == consumer)
		{
			renderJson(new Result(-1000, "数据为空"));
		}
		renderJson(ConsumerService.service.doUpdatePersonInfo(consumer));
	}
	
	/**
	 * 客户修改个人资料
	 */
	@Before(AuthorityInterceptor.class)
	public void doAddWithDraw()
	{
		Withdraw consumer = this.getModel(Withdraw.class);
		if(null == consumer)
		{
			renderJson(new Result(-1000, "数据为空"));
		}
		renderJson(WithdrawService.service.add(consumer));
	}
	
	/**
	 * 获取，树形结构 
	 */
	@Before({AuthorityInterceptor.class, CheckTwoPwdInterceptor.class})
	public void doTree()
	{
		
		User user=(User) this.getRequest().getSession().getAttribute(Constant.SESSION_USER);
		if(user.getType() == 2) {
			renderJson(ConsumerService.service.treeByConsumer(user));
		}else {
			renderJson(ConsumerService.service.tree());
		}
	}
	
	/**
	 * 获取，树形结构 
	 */
	@Before({AuthorityInterceptor.class, CheckTwoPwdInterceptor.class})
	public void searchSalarys()
	{
		
		User user=(User) this.getRequest().getSession().getAttribute(Constant.SESSION_USER);
		
		renderJson(SalaryService.service.searchSalarys(user.getConsumerId()));
		
	}
	
	/**
	 * 根据登录用户查询客户详细资料 
	 */
	@Before({AuthorityInterceptor.class, CheckTwoPwdInterceptor.class})
	public void doGetByUser()
	{
		User user=(User) this.getRequest().getSession().getAttribute(Constant.SESSION_USER);
		
		renderJson(ConsumerService.service.getById(user.getConsumerId()));
	}
	
	/**
	 * 根据登录用户查询客户详细资料 
	 */
	@Before(AuthorityInterceptor.class)
	public void doGetDetailByUser()
	{
		User user=(User) this.getRequest().getSession().getAttribute(Constant.SESSION_USER);
		
		renderJson(ConsumerService.service.getById(user.getConsumerId()));
	}
	
	/**
	 * 获取用户提现记录
	 */
	@Before({AuthorityInterceptor.class, CheckTwoPwdInterceptor.class})
	public void doSearchWithdraw()
	{
		User user=(User) this.getRequest().getSession().getAttribute(Constant.SESSION_USER);
		
		renderJson(WithdrawService.service.searchWithdraws(user.getConsumerId()));
	}
	
	/**
	 * 获取用户推荐的客户
	 */
	@Before({AuthorityInterceptor.class, CheckTwoPwdInterceptor.class})
	public void doSearchReferrers()
	{
		User user=(User) this.getRequest().getSession().getAttribute(Constant.SESSION_USER);
		
		renderJson(ConsumerService.service.searchReferrers(user.getConsumerId()));
	}
	
	@Before(AuthorityInterceptor.class)
	public void doPageWithdraw()
	{
		int pageNumber = this.getParaToInt("pageNumber");
        int pageSize = this.getParaToInt("pageSize");
        if(pageNumber == 0 || pageSize == 0){
            this.renderJson(new Result(-1001, "分数或每页数量为空"));
            return;
        }else{
            String name = this.getPara("name");
            String type = this.getPara("type");
            
            long startTime = 0;
            long endTime = 0;
            if(this.getParaToLong("startTime") != null){
                startTime = this.getParaToLong("startTime");
            }
            if(this.getParaToLong("endTime") != null){
                endTime = this.getParaToLong("endTime");
            }
            this.renderJson(new Result(Result.SUCCESS_STATUS, WithdrawService.service.page(pageNumber, pageSize, name, type, startTime, endTime)));
        }
	}
	
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
            String type = this.getPara("type");
            
            long startTime = 0;
            long endTime = 0;
            if(this.getParaToLong("startTime") != null){
                startTime = this.getParaToLong("startTime");
            }
            if(this.getParaToLong("endTime") != null){
                endTime = this.getParaToLong("endTime");
            }
            this.renderJson(new Result(Result.SUCCESS_STATUS, ConsumerService.service.page(pageNumber, pageSize, name, type, startTime, endTime)));
        }
	}
}
