package com.future.link.consumer.service;

import java.util.List;

import com.future.link.common.Result;
import com.future.link.consumer.model.Withdraw;
import com.future.link.utils.CommonUtil;
import com.future.link.utils.ToolDateTime;
import com.jfinal.aop.Enhancer;

public class WithdrawService {
	
	public static final WithdrawService service = Enhancer.enhance(WithdrawService.class);
	
	/**
	 * 新增
	 * @param salary
	 */
	public Result add(Withdraw withdraw) {
		
		withdraw.setId(CommonUtil.getUUID());
		withdraw.setWithdrawDate(ToolDateTime.getDate());
		withdraw.save();
		return new Result(Result.SUCCESS_STATUS, "添加成功");
	}
	
	
	/**
	 * 获取客户工资
	 * @param consumerId
	 * @return
	 */
	public Double searchWithdraw(String consumerId) {
		
		Withdraw withdraw = Withdraw.dao.findFirst("select sum(withdrawCount) as withdrawCount from consumer_withdraw WHERE consumerId = ?", consumerId);
		if(withdraw.getWithdrawCount() == null) {
			return 0.0;
		}
		return withdraw.getWithdrawCount();
	}
	
	/**
	 * 获取客户提现记录
	 * @param 
	 */
	public Result searchWithdraws(String consumerId) {
		
		List<Withdraw> list = Withdraw.dao.find("select * from consumer_withdraw WHERE consumerId = ?", consumerId);
		return new Result(Result.SUCCESS_STATUS, list);
	}

}
