package com.future.link.base.service;

import com.future.link.base.model.Setting;
import com.jfinal.aop.Enhancer;

public class SettingService {
	
	public static final SettingService service = Enhancer.enhance(SettingService.class);
	
	
	/**
	 * 根据id获取系统设置
	 * @return
	 */
	public Setting getById() {
		
		return Setting.dao.findById(1);
		
	}

}
