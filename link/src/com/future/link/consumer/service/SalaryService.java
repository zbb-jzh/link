package com.future.link.consumer.service;

import java.util.Date;
import java.util.List;

import com.future.link.common.Result;
import com.future.link.consumer.model.Salary;
import com.future.link.utils.CommonUtil;
import com.future.link.utils.Constant;
import com.future.link.utils.ToolDateTime;
import com.jfinal.aop.Enhancer;

public class SalaryService {
	
	public static final SalaryService service = Enhancer.enhance(SalaryService.class);
	
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
	public Double searchSalary(String consumerId) {
		
		Salary salary = Salary.dao.findFirst("select sum(realWage) as realWage from consumer_salary WHERE consumerId = ?", consumerId);
		if(salary.getRealWage() == null) {
			return 0.0;
		}
		return salary.getRealWage();
	}
	
	/**
	 * 查询客户的工资明细
	 * @param consumerId
	 * @return
	 */
	public Result searchSalarys(String consumerId) {
		
		List<Salary> list = Salary.dao.find("select * from consumer_salary WHERE consumerId = ?", consumerId);
		return new Result(Constant.SUCCESS, list);
	}

}
