package com.future.link.base.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSetting<M extends BaseSetting<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setWithdrawalRatio(java.lang.Double withdrawalRatio) {
		set("withdrawalRatio", withdrawalRatio);
	}

	public java.lang.Double getWithdrawalRatio() {
		return get("withdrawalRatio");
	}

	public void setManagementFeeRatio(java.lang.Double managementFeeRatio) {
		set("managementFeeRatio", managementFeeRatio);
	}

	public java.lang.Double getManagementFeeRatio() {
		return get("managementFeeRatio");
	}

	public void setAAdvertisingaward(java.lang.Double aAdvertisingaward) {
		set("a_advertisingAward", aAdvertisingaward);
	}

	public java.lang.Double getAAdvertisingaward() {
		return get("a_advertisingAward");
	}

	public void setBAdvertisingaward(java.lang.Double bAdvertisingaward) {
		set("b_advertisingAward", bAdvertisingaward);
	}

	public java.lang.Double getBAdvertisingaward() {
		return get("b_advertisingAward");
	}

	public void setLayerAward(java.lang.Double layerAward) {
		set("layerAward", layerAward);
	}

	public java.lang.Double getLayerAward() {
		return get("layerAward");
	}

	public void setVentureCapital(java.lang.Double ventureCapital) {
		set("ventureCapital", ventureCapital);
	}

	public java.lang.Double getVentureCapital() {
		return get("ventureCapital");
	}

}
