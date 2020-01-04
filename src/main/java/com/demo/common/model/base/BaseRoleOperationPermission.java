package com.demo.common.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseRoleOperationPermission<M extends BaseRoleOperationPermission<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	/**
	 * 角色id
	 */
	public void setRoleId(java.lang.Integer roleId) {
		set("roleId", roleId);
	}
	
	/**
	 * 角色id
	 */
	public java.lang.Integer getRoleId() {
		return getInt("roleId");
	}

	/**
	 * 操作id
	 */
	public void setOperationId(java.lang.Integer operationId) {
		set("operationId", operationId);
	}
	
	/**
	 * 操作id
	 */
	public java.lang.Integer getOperationId() {
		return getInt("operationId");
	}

	/**
	 * 状态
	 */
	public void setValid(java.lang.Integer valid) {
		set("valid", valid);
	}
	
	/**
	 * 状态
	 */
	public java.lang.Integer getValid() {
		return getInt("valid");
	}

}
