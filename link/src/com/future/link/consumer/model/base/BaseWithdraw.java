package com.future.link.consumer.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseWithdraw<M extends BaseWithdraw<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setUserName(java.lang.String userName) {
		set("userName", userName);
	}

	public java.lang.String getUserName() {
		return get("userName");
	}

	public void setConsumerName(java.lang.String consumerName) {
		set("consumerName", consumerName);
	}

	public java.lang.String getConsumerName() {
		return get("consumerName");
	}

	public void setWithdrawCount(java.lang.Double withdrawCount) {
		set("withdrawCount", withdrawCount);
	}

	public java.lang.Double getWithdrawCount() {
		return get("withdrawCount");
	}

	public void setBankAccountName(java.lang.String bankAccountName) {
		set("bankAccountName", bankAccountName);
	}

	public java.lang.String getBankAccountName() {
		return get("bankAccountName");
	}

	public void setBankCard(java.lang.String bankCard) {
		set("bankCard", bankCard);
	}

	public java.lang.String getBankCard() {
		return get("bankCard");
	}

	public void setBankName(java.lang.String bankName) {
		set("bankName", bankName);
	}

	public java.lang.String getBankName() {
		return get("bankName");
	}

	public void setBankAddress(java.lang.String bankAddress) {
		set("bankAddress", bankAddress);
	}

	public java.lang.String getBankAddress() {
		return get("bankAddress");
	}

	public void setPrizeCoin(java.lang.Double prizeCoin) {
		set("prizeCoin", prizeCoin);
	}

	public java.lang.Double getPrizeCoin() {
		return get("prizeCoin");
	}

}
