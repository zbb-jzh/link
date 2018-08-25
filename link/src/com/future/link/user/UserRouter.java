package com.future.link.user;

import com.jfinal.config.Routes;
import com.future.link.user.controller.UserController;

public class UserRouter extends Routes{

	@Override
	public void config() {
		// TODO Auto-generated method stub
		this.add("user", UserController.class);
	}

}
