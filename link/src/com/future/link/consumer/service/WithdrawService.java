package com.future.link.consumer.service;

import java.util.Date;

import com.future.link.consumer.model.Salary;
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
	public void add(Salary salary) {
		
		salary.setId(CommonUtil.getUUID());
		salary.setCreateDate(ToolDateTime.getDate());
		
		Salary model = this.getByCondition(salary);
		//同一天进行累加
		if(model != null) {
			
			model.setAAdvertisingaward(model.getAAdvertisingaward() + salary.getAAdvertisingaward());
			model.setBAdvertisingaward(model.getBAdvertisingaward() + salary.getBAdvertisingaward());
			
			model.setLayerAward(model.getLayerAward() + salary.getLayerAward());
			model.setManagementFee(model.getManagementFee() + salary.getManagementFee());
			model.setWithdrawalFee(model.getWithdrawalFee() + salary.getWithdrawalFee());
			model.setRealWage(model.getRealWage() + salary.getRealWage());
			
			model.update();
			
		}else {
			salary.save();
		}
		
	}
	
	public Salary getByCondition(Salary model) {
		
		return Salary.dao.findFirst("select * from consumer_salary WHERE createDate = ? and consumerId = ?", ToolDateTime.format(new Date(), "yyyy-MM-dd"), model.getConsumerId());
		
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

}
