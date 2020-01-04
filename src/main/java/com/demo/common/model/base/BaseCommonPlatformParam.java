package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseCommonPlatformParam<M extends BaseCommonPlatformParam<M>> extends Model<M> implements IBean {

	/**
	 * id主键
	 */
	public void setId(java.lang.Long id) {
		set("id", id);
	}
	
	/**
	 * id主键
	 */
	public java.lang.Long getId() {
		return getLong("id");
	}

	/**
	 * 类别编码
	 */
	public void setTypeCode(java.lang.String typeCode) {
		set("typeCode", typeCode);
	}
	
	/**
	 * 类别编码
	 */
	public java.lang.String getTypeCode() {
		return getStr("typeCode");
	}

	/**
	 * 参数类型代码
	 */
	public void setStaticCode(java.lang.String staticCode) {
		set("staticCode", staticCode);
	}
	
	/**
	 * 参数类型代码
	 */
	public java.lang.String getStaticCode() {
		return getStr("staticCode");
	}

	/**
	 * 参数类型名称
	 */
	public void setStaticName(java.lang.String staticName) {
		set("staticName", staticName);
	}
	
	/**
	 * 参数类型名称
	 */
	public java.lang.String getStaticName() {
		return getStr("staticName");
	}

	/**
	 * 参数名
	 */
	public void setParamName(java.lang.String paramName) {
		set("paramName", paramName);
	}
	
	/**
	 * 参数名
	 */
	public java.lang.String getParamName() {
		return getStr("paramName");
	}

	/**
	 * 参数值
	 */
	public void setParamValue(java.lang.String paramValue) {
		set("paramValue", paramValue);
	}
	
	/**
	 * 参数值
	 */
	public java.lang.String getParamValue() {
		return getStr("paramValue");
	}

	/**
	 * 参数排序号
	 */
	public void setSort(java.lang.Integer sort) {
		set("sort", sort);
	}
	
	/**
	 * 参数排序号
	 */
	public java.lang.Integer getSort() {
		return getInt("sort");
	}

	/**
	 * 备注
	 */
	public void setMark(java.lang.String mark) {
		set("mark", mark);
	}
	
	/**
	 * 备注
	 */
	public java.lang.String getMark() {
		return getStr("mark");
	}

	/**
	 * 必填参数标记
	 */
	public void setRequired(java.lang.Integer required) {
		set("required", required);
	}
	
	/**
	 * 必填参数标记
	 */
	public java.lang.Integer getRequired() {
		return getInt("required");
	}

	/**
	 * 默认值
	 */
	public void setDefaultValue(java.lang.String defaultValue) {
		set("defaultValue", defaultValue);
	}
	
	/**
	 * 默认值
	 */
	public java.lang.String getDefaultValue() {
		return getStr("defaultValue");
	}

}
