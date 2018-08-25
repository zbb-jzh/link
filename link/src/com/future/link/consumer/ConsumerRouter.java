package com.future.link.consumer;

import com.jfinal.config.Routes;
import com.future.link.consumer.controller.ConsumerController;

public class ConsumerRouter extends Routes{

	@Override
	public void config() {
		// TODO Auto-generated method stub
		this.add("consumer", ConsumerController.class);
	}

}
