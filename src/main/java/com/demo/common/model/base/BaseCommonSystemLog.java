package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseCommonSystemLog<M extends BaseCommonSystemLog<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 错误类型
	 */
	public void setType(java.lang.String type) {
		set("type", type);
	}
	
	/**
	 * 错误类型
	 */
	public java.lang.String getType() {
		return getStr("type");
	}

	/**
	 * 系统错误代码
	 */
	public void setErrorCode(java.lang.String errorCode) {
		set("error_code", errorCode);
	}
	
	/**
	 * 系统错误代码
	 */
	public java.lang.String getErrorCode() {
		return getStr("error_code");
	}

	/**
	 * 日志描述
	 */
	public void setDescription(java.lang.String description) {
		set("description", description);
	}
	
	/**
	 * 日志描述
	 */
	public java.lang.String getDescription() {
		return getStr("description");
	}

	/**
	 * 错误时间
	 */
	public void setTime(java.util.Date time) {
		set("time", time);
	}
	
	/**
	 * 错误时间
	 */
	public java.util.Date getTime() {
		return get("time");
	}

}
