package com.future.link.consumer.controller;

import com.future.link.base.interceptor.AuthorityInterceptor;
import com.future.link.common.Result;
import com.future.link.consumer.model.Consumer;
import com.future.link.consumer.service.ConsumerService;
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
		renderJson(ConsumerService.service.add(consumer));
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
	 * 获取，树形结构
	 */
	@Before(AuthorityInterceptor.class)
	public void doTree()
	{
		
		User user=(User) this.getRequest().getSession().getAttribute(Constant.SESSION_USER);
		if(user.getType() == 2) {
			renderJson(ConsumerService.service.treeByConsumer(user));
		}else {
			renderJson(ConsumerService.service.tree());
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
