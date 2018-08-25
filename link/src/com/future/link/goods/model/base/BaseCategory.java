package com.future.link.goods.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseCategory<M extends BaseCategory<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Long id) {
		set("id", id);
	}

	public java.lang.Long getId() {
		return get("id");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setParentId(java.lang.Long parentId) {
		set("parentId", parentId);
	}

	public java.lang.Long getParentId() {
		return get("parentId");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return get("status");
	}

	public void setCreateTime(java.lang.Long createTime) {
		set("createTime", createTime);
	}

	public java.lang.Long getCreateTime() {
		return get("createTime");
	}

	public void setShopId(java.lang.String shopId) {
		set("shopId", shopId);
	}

	public java.lang.String getShopId() {
		return get("shopId");
	}

}
