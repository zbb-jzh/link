package com.future.link.consumer.service;

import com.future.link.consumer.model.Salary;
import com.future.link.utils.CommonUtil;
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
		
		salary.save();
		
	}

}
