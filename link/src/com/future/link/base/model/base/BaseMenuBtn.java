package com.future.link.base.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMenuBtn<M extends BaseMenuBtn<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setMenuId(java.lang.String menuId) {
		set("menuId", menuId);
	}

	public java.lang.String getMenuId() {
		return get("menuId");
	}

	public void setBtnName(java.lang.String btnName) {
		set("btnName", btnName);
	}

	public java.lang.String getBtnName() {
		return get("btnName");
	}

	public void setBtnType(java.lang.String btnType) {
		set("btnType", btnType);
	}

	public java.lang.String getBtnType() {
		return get("btnType");
	}

	public void setStatus(java.lang.Integer status) {
		set("status", status);
	}

	public java.lang.Integer getStatus() {
		return get("status");
	}

}
