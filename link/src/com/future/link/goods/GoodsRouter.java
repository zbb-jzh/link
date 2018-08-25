package com.future.link.goods;

import com.future.link.goods.controller.CategoryController;
import com.future.link.goods.controller.GoodsController;
import com.jfinal.config.Routes;

public class GoodsRouter extends Routes{

	@Override
	public void config() {
		this.add("category", CategoryController.class);
		this.add("goods", GoodsController.class);
	}

}
