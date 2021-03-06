package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseBankInfo<M extends BaseBankInfo<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 银行名称
	 */
	public void setBankName(java.lang.String bankName) {
		set("bankName", bankName);
	}
	
	/**
	 * 银行名称
	 */
	public java.lang.String getBankName() {
		return getStr("bankName");
	}

	/**
	 * 银行代码
	 */
	public void setBankValue(java.lang.String bankValue) {
		set("bankValue", bankValue);
	}
	
	/**
	 * 银行代码
	 */
	public java.lang.String getBankValue() {
		return getStr("bankValue");
	}

	public void setYdBankCode(java.lang.String ydBankCode) {
		set("ydBankCode", ydBankCode);
	}
	
	public java.lang.String getYdBankCode() {
		return getStr("ydBankCode");
	}

}
