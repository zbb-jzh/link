package com.future.link.consumer.model;

import java.util.ArrayList;
import java.util.List;

import com.future.link.consumer.model.base.BaseConsumer;
import com.future.link.goods.model.Category;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Consumer extends BaseConsumer<Consumer> {
	public static final Consumer dao = new Consumer();
	
	/**
	 * 未删除
	 */
	public static final int UN_DELETE = 1;
	
	/**
	 * 删除
	 */
	public static final int DELETEED = 0;
	
	/**
	 * 线下
	 */
	public static final int ORIGIN_OFFLINE = 1;
	
	/**
	 * 电子商务
	 */
	public static final int ORIGIN_ECOMMERCE = 2;
	
	/**
	 * 电子交易
	 */
	public static final int ORIGIN_ETRADING = 3;
	
	private List<Consumer> nodes;

	public List<Consumer> getNodes() {
		if(this.nodes==null) {
			this.nodes = new ArrayList<Consumer>();
		}
		return nodes;
	}

	public void setNodes(List<Consumer> nodes) {
		this.put("nodes", nodes);
		this.nodes = nodes;
	}
	
	private double withdraws;
	
	

	public double getWithdraws() {
		return withdraws;
	}

	public void setWithdraws(double withdraws) {
		this.put("withdraws", withdraws);
		this.withdraws = withdraws;
	}

}
