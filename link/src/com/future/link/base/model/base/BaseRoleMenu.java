package com.future.link.base.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseRoleMenu<M extends BaseRoleMenu<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setRoleId(java.lang.String roleId) {
		set("roleId", roleId);
	}

	public java.lang.String getRoleId() {
		return get("roleId");
	}

	public void setMenuId(java.lang.String menuId) {
		set("menuId", menuId);
	}

	public java.lang.String getMenuId() {
		return get("menuId");
	}

}
