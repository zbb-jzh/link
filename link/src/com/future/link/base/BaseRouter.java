package com.future.link.base;

import com.future.link.base.controller.FileUploadController;
import com.future.link.base.controller.MenuController;
import com.future.link.base.controller.RoleController;
import com.jfinal.config.Routes;

public class BaseRouter extends Routes{

	@Override
	public void config() {
		this.add("fileupload", FileUploadController.class);
		this.add("menu", MenuController.class);
		this.add("role", RoleController.class);
	}
}
